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
import java.util.stream.Collectors;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "event", description = "Generates config for a new event.", mixinStandardHelpOptions = true)
public class GenerateEventCommand implements Runnable {

  @Option(names = "--table", description = "Full table name.")
  Optional<String> tableOption;

  @Option(names = "--event-name", description = "Name of generated event.")
  Optional<String> logicalTableOption;

  @Option(names = "--date-column", description = "Name of date column.")
  Optional<String> dateColumnOption;

  @Option(names = "--timestamp-column", description = "Name of timestamp column.")
  Optional<String> timestampColumnOption;

  @Option(names = "--user-id-column", description = "Name of user id column.")
  Optional<String> userIdColumnOption;

  @Option(names = "--no-wizard", description = "Fails if any required input is missing."
      + " Useful for scripting.")
  Optional<Boolean> noWizardOption;

  @Option(names = "--subschema", description = "Event table parameters are part of a subschema.")
  Optional<Boolean> isSubschemaOption;

  @Option(names = "--subschema-type-column", description = "Column that indicates which subschema is used")
  Optional<String> subschemaTypeColumnOption;

  @Option(names = "--subschema-type-value", description = "Value for subschema type column.")
  Optional<String> subschemaTypeValueOption;

  @Option(names = "--subschema-params-column", description = "Column that contains parameters for this subschema")
  Optional<String> subschemaParamsNameOption;

  @Option(names = "--skip-registrations-tag", description = "If set, will not add registrations tag to table.")
  Optional<Boolean> skipRegistrationsTag;

  @Option(names = "--skip-activity-tag", description = "If set, will not add activity tag to table.")
  Optional<Boolean> skipActivityTag;

  @Inject
  QueryEngineClient queryEngineClient;

  @Inject
  ObjectMapper objectMapper;

  @Override
  public void run() {
    try {
      var dsGeneratorHelper = new DsGeneratorHelper(queryEngineClient, noWizardOption);

      final String table = dsGeneratorHelper.readInput(
          tableOption, "table",
          Optional.empty(), "Enter full table name", Optional.empty());
      String actionRecommendedName = table;
      var columns = dsGeneratorHelper.getTableSchema(table);

      Optional<String> where = Optional.empty();
      if (isSubschemaOption.orElse(false)) {
        final String subschemaTypeColumn = dsGeneratorHelper.readInput(
            subschemaTypeColumnOption, "subschema-type-column",
            Optional.empty(),
            "\nEnter the name of the column that indicates which subschema column is used.",
            MostSimilarColumn.find("event", columns, Set.of("string")));

        final String subschemaTypeValue = dsGeneratorHelper.readInput(
            subschemaTypeValueOption, "subschema-type-value",
            Optional.empty(),
            "Enter the value of %s column for this action".formatted(subschemaTypeColumn),
            Optional.empty());
        actionRecommendedName = subschemaTypeValue;

        final String subschemaParamsName = dsGeneratorHelper.readInput(
            subschemaParamsNameOption, "subschema-params-column",
            Optional.empty(),
            "Enter column name that contains parameters for this subschema",
            Optional.empty());

        columns = columns.stream()
            .filter(
                c -> !c.getId().contains(".") || c.getId().startsWith(subschemaParamsName + "."))
            .collect(Collectors.toList());

        where = Optional.of("{%s} = '%s'".formatted(subschemaTypeColumn, subschemaTypeValue));
      }

      final String logicalTableName = dsGeneratorHelper.readInput(
          logicalTableOption, "event-name",
          Optional.empty(), "Enter event name",
          Optional.of(dsGeneratorHelper.recommendedlogicalTableName(actionRecommendedName)));

      final String dateColumn = dsGeneratorHelper.readInput(
          dateColumnOption, "date-column",
          Optional.of("\nEvent datasources need a date column."
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
              "\nEnter the name of the column that represent the id of the user that performed the event"),
          "Enter user id column name",
          MostSimilarColumn.find("user", columns, Set.of("string", "integer")));

      Map<String, List<String>> columnTags = new HashMap<>();
      columnTags.put(dateColumn, List.of(TemporalLogicalTable.DATE_COLUMN_TAG));
      columnTags.put(timestampColumn, List.of(EventLikeLogicalTable.TIMESTAMP_COLUMN_TAG));
      columnTags.put(userIdColumn, List.of(EventLogicalTable.ENTITY_ID_COLUMN_TAG));
      columnTags.putAll(additionalColumnTags(columns));

      var columnsDto = new ColumnsDto();
      columns.forEach(c -> {
        var column = new EventColumnDto();
        column.setDataType(DataType.valueOf(c.getDataType().toUpperCase()));
        column.setTags(columnTags.get(c.getId()));
        columnsDto.setAdditionalProperty(c.getId(), column);
      });

      var datasourceDto = new EventLogicalTableDto(
          table,
          logicalTableTags(),
          null,
          null,
          columnsDto,
          where.orElse(null),
          List.of());


      YamlConfigParser parser = new YamlConfigParser(null, GlobalConfig.getAppIdDir().getParent());
      Path dsPath = parser.actionsDir(GlobalConfig.getAppId()).resolve(logicalTableName + ".yml");
      dsPath.toFile().getParentFile().mkdirs();

      new YamlSerDe().save("event_logical_table", datasourceDto, dsPath);

      System.out.println("Datasource saved to " + dsPath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected List<String> logicalTableTags() {
    List<String> tags = new ArrayList<>();
    if (!skipRegistrationsTag.orElse(false)) {
      tags.add(RegistrationsLogicalTable.TAG);
    }
    if (!skipActivityTag.orElse(false)) {
      tags.add(ActivityLogicalTable.TAG);
    }
    return tags;
  }

  protected Map<String, List<String>> additionalColumnTags(
      List<com.asemicanalytics.cli.model.ColumnDto> columns) {
    return Map.of();
  }

}
