package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.YamlSerDe;
import com.asemicanalytics.cli.internal.dsgenerator.DsGeneratorHelper;
import com.asemicanalytics.cli.internal.dsgenerator.MostSimilarColumn;
import com.asemicanalytics.core.logicaltable.EventLikeLogicalTable;
import com.asemicanalytics.core.logicaltable.TemporalLogicalTable;
import com.asemicanalytics.core.logicaltable.action.ActionLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionLogicalTableDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "action", description = "Generates config for a new action.", mixinStandardHelpOptions = true)
public class GenerateActionCommand implements Runnable {

  @Option(names = "--table", description = "Full table name.")
  Optional<String> tableOption;

  @Option(names = "--action-name", description = "Name of generated action.")
  Optional<String> logicalTableOption;

  @Option(names = "--date-column", description = "Name of date column.")
  Optional<String> dateColumnOption;

  @Option(names = "--timestamp-column", description = "Name of timestamp column.")
  Optional<String> timestampColumnOption;

  @Option(names = "--user-id-column", description = "Name of user id column.")
  Optional<String> userIdColumnOption;

  @Option(names = "--no-wizard", description = "Fails if any required input is missing."
      + " Useful for scripting.")
  Optional<Boolean> noWizard;

  @Inject
  QueryEngineClient queryEngineClient;

  @Inject
  ObjectMapper objectMapper;

  @Override
  public void run() {
    try {
      var dsGeneratorHelper = new DsGeneratorHelper(queryEngineClient, noWizard);

      final String table = dsGeneratorHelper.readInput(
          tableOption, "table",
          Optional.empty(), "Enter full table name", Optional.empty());

      var columns = dsGeneratorHelper.getTableSchema(table);

      final String logicalTableName = dsGeneratorHelper.readInput(
          logicalTableOption, "action-name",
          Optional.empty(), "Enter action name",
          Optional.of(dsGeneratorHelper.recommendedlogicalTableName(table)));

      final String dateColumn = dsGeneratorHelper.readInput(
          dateColumnOption, "date-column",
          Optional.of("\nAction datasources need a date column."
              + "\nIdeally, this should be a partition column for performance reasons."),
          "Enter date column name",
          MostSimilarColumn.find("", columns, Set.of("date")));


      final String timestampColumn = dsGeneratorHelper.readInput(
          timestampColumnOption, "timestamp-column",
          Optional.of(
              "\nA timestamp column is needed that represents the exact time action happened"),
          "Enter timestamp column name",
          MostSimilarColumn.find("", columns, Set.of("datetime")));


      final String userIdColumn = dsGeneratorHelper.readInput(
          userIdColumnOption, "user-id-column",
          Optional.of(
              "\nEnter the name of the column that represent the id of the user that performed the action"),
          "Enter user id column name",
          MostSimilarColumn.find("user", columns, Set.of("string", "integer")));

      Map<String, List<String>> columnTags = new HashMap<>();
      columnTags.put(dateColumn, List.of(TemporalLogicalTable.DATE_COLUMN_TAG));
      columnTags.put(timestampColumn, List.of(EventLikeLogicalTable.TIMESTAMP_COLUMN_TAG));
      columnTags.put(userIdColumn, List.of(ActionLogicalTable.ENTITY_ID_COLUMN_TAG));
      columnTags.putAll(additionalColumnTags(columns));


      var datasourceDto = new ActionLogicalTableDto(
          table,
          datasourceTags(),
          null,
          null,
          columns.stream().map(c -> new ColumnDto(
              c.getId(),
              ColumnDto.DataType.valueOf(c.getDataType().toUpperCase()),
              null,
              null,
              null,
              null,
              columnTags.get(c.getId())
          )).collect(Collectors.toList()),
          List.of(),
          List.of());


      Path dsPath = GlobalConfig.getUserEntityDirt().resolve(logicalTableName + ".yml");
      dsPath.toFile().getParentFile().mkdirs();

      new YamlSerDe().save("action_logical_table", datasourceDto, dsPath);

      System.out.println("Datasource saved to " + dsPath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected List<String> datasourceTags() {
    return null;
  }

  protected Map<String, List<String>> additionalColumnTags(
      List<com.asemicanalytics.cli.model.ColumnDto> columns) {
    return Map.of();
  }

}
