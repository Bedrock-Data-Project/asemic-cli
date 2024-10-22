package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.YamlSerDe;
import com.asemicanalytics.cli.internal.dsgenerator.DsGeneratorHelper;
import com.asemicanalytics.cli.internal.dsgenerator.MostSimilarColumn;
import com.asemicanalytics.config.parser.yaml.YamlConfigParser;
import com.asemicanalytics.core.logicaltable.EventLikeLogicalTable;
import com.asemicanalytics.core.logicaltable.TemporalLogicalTable;
import com.asemicanalytics.core.logicaltable.event.ActivityLogicalTable;
import com.asemicanalytics.core.logicaltable.event.EventLogicalTable;
import com.asemicanalytics.core.logicaltable.event.RegistrationsLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnsDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EventColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EventLogicalTableDto;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "events", description = "Generates event config for all tables from a schema", mixinStandardHelpOptions = true)
public class GenerateEventsCommand implements Runnable {

  @Option(names = "--schema", description = "Schema name.")
  Optional<String> schemaOption;

  @Option(names = "--date-column", description = "Name of date column.")
  Optional<String> dateColumnOption;

  @Option(names = "--timestamp-column", description = "Name of timestamp column.")
  Optional<String> timestampColumnOption;

  @Option(names = "--user-id-column", description = "Name of user id column.")
  Optional<String> userIdColumnOption;

  QueryEngineClient queryEngineClient = new QueryEngineClient();

  @Inject
  ObjectMapper objectMapper;

  @Override
  public void run() {
    try {
      var dsGeneratorHelper = new DsGeneratorHelper(queryEngineClient, Optional.empty());

      final String schema = dsGeneratorHelper.readInput(
          schemaOption, "schema",
          Optional.empty(), "Enter schema name", Optional.empty());
      System.out.println("Getting tables...");
      var tables = queryEngineClient.getTables(GlobalConfig.getAppId(), schema);
      if (tables.isEmpty()) {
        System.out.println("No tables found in schema " + schema);
        return;
      }
      System.out.println("Got " + tables.size() + " tables.");

      var columns = dsGeneratorHelper.getTableSchema(schema + "." + tables.getFirst());

      final String dateColumn = dsGeneratorHelper.readInput(
          dateColumnOption, "date-column",
          Optional.of("\nEvent datasources need a date column."
              + "\nIdeally, this should be a partition column for performance reasons."),
          "Enter date column name",
          MostSimilarColumn.find("", columns, Set.of("date")));

      final String timestampColumn = dsGeneratorHelper.readInput(
          timestampColumnOption, "timestamp-column",
          Optional.of(
              "\nA timestamp column is needed that represents the exact time event happened"),
          "Enter timestamp column name",
          MostSimilarColumn.find("", columns, Set.of("datetime")));

      final String userIdColumn = dsGeneratorHelper.readInput(
          userIdColumnOption, "user-id-column",
          Optional.of(
              "\nEnter the name of the column that represent the id of the user that performed the event"),
          "Enter user id column name",
          MostSimilarColumn.find("user", columns, Set.of("string", "integer")));

      Map<String, List<String>> columnTags = new HashMap<>();
      columnTags.put(dateColumn, List.of(TemporalLogicalTable.DATE_COLUMN_TAG));
      columnTags.put(timestampColumn, List.of(EventLikeLogicalTable.TIMESTAMP_COLUMN_TAG));
      columnTags.put(userIdColumn, List.of(EventLogicalTable.ENTITY_ID_COLUMN_TAG));

      for (String table : tables) {
        columns = dsGeneratorHelper.getTableSchema(schema + "." + table);

        if (columns.stream().noneMatch(c -> c.getId().equals(dateColumn))) {
          System.out.println("Skipping table " + table + " because it has no date column");
          continue;
        }
        if (columns.stream().noneMatch(c -> c.getId().equals(timestampColumn))) {
          System.out.println("Skipping table " + table + " because it has no timestamp column");
          continue;
        }
        if (columns.stream().noneMatch(c -> c.getId().equals(userIdColumn))) {
          System.out.println("Skipping table " + table + " because it has no user id column");
          continue;
        }

        var columnsDto = new ColumnsDto();
        columns.forEach(c -> {
          var column = new EventColumnDto();
          column.setDataType(DataType.valueOf(c.getDataType().toUpperCase()));
          column.setTags(columnTags.get(c.getId()));
          columnsDto.setAdditionalProperty(c.getId(), column);
        });

        var datasourceDto = new EventLogicalTableDto(
            schema + "." + table,
            List.of(RegistrationsLogicalTable.TAG, ActivityLogicalTable.TAG),
            null,
            null,
            columnsDto,
            null,
            List.of());

        YamlConfigParser parser = new YamlConfigParser(null, GlobalConfig.getAppIdDir().getParent());
        Path dsPath = parser.actionsDir(GlobalConfig.getAppId()).resolve(table + ".yml");
        dsPath.toFile().getParentFile().mkdirs();
        new YamlSerDe().save("event_logical_table", datasourceDto, dsPath);
        System.out.println("Datasource saved to " + dsPath);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
