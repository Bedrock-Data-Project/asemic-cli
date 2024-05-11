package com.asemicanalytics.cli.semanticlayer;

import com.asemicanalytics.cli.semanticlayer.internal.GlobalConfig;
import com.asemicanalytics.cli.semanticlayer.internal.QueryEngineClient;
import com.asemicanalytics.cli.semanticlayer.internal.YamlObjectMapperFactory;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.DsGeneratorHelper;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.MostSimilarColumn;
import com.asemicanalytics.semanticlayer.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.dto.v1.semantic_layer.UserActionDatasourceDto;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "generate-ds-user-action", mixinStandardHelpOptions = true)
public class GenerateDsUserActionCommand implements Runnable {

  @Option(names = "--table", description = "Full table name.")
  Optional<String> tableOption;

  @Option(names = "--datasource-name", description = "Name of generated datasource.")
  Optional<String> datasourceOption;

  @Option(names = "--date-column", description = "Name of date column.")
  Optional<String> dateColumnOption;

  @Option(names = "--timestamp-column", description = "Name of timestamp column.")
  Optional<String> timestampColumnOption;

  @Option(names = "--user-id-column", description = "Name of user id column.")
  Optional<String> userIdColumnOption;

  @Option(names = "--no-wizard", description = "Fails if any required input is missing."
      + " Usefull for scripting.")
  Optional<Boolean> noWizard;

  @Inject
  QueryEngineClient queryEngineClient;

  @Override
  public void run() {
    var dsGeneratorHelper = new DsGeneratorHelper(queryEngineClient, noWizard);

    final String table = dsGeneratorHelper.readInput(
        tableOption, "table",
        Optional.empty(), "Enter full table name", Optional.empty());

    try {
      var columns = dsGeneratorHelper.getTableSchema(table);

      final String datasourceName = dsGeneratorHelper.readInput(
          datasourceOption, "datasource-name",
          Optional.empty(), "Enter datasource name",
          Optional.of(dsGeneratorHelper.recommendedDatasourceName(table)));

      final String dateColumn = dsGeneratorHelper.readInput(
          dateColumnOption, "date-column",
          Optional.of("User action datasources need a date column."
              + "\nIdeally, this should be a partition column for performance reason."),
          "Enter date column name",
          MostSimilarColumn.find("", columns, Set.of("date")));


      final String timestampColumn = dsGeneratorHelper.readInput(
          timestampColumnOption, "timestamp-column",
          Optional.of(
              "A timestamp column is needed that represents the exact time user action happened"),
          "Enter timestamp column name",
          MostSimilarColumn.find("", columns, Set.of("datetime")));


      final String userIdColumn = dsGeneratorHelper.readInput(
          userIdColumnOption, "user-id-column",
          Optional.of(
              "Enter the name of the column that represent the id of the user that performed the action"),
          "Enter user id column name",
          MostSimilarColumn.find("user", columns, Set.of("string", "integer")));

      var datasourceDto = new UserActionDatasourceDto(
          table,
          null,
          null,
          dateColumn,
          timestampColumn,
          userIdColumn,
          columns.stream().map(c -> new ColumnDto(
              c.getId(),
              ColumnDto.DataType.valueOf(c.getDataType().toUpperCase()),
              null,
              null,
              null,
              null
          )).collect(Collectors.toList()),
          List.of(),
          List.of(),
          List.of());

      Path dsPath = GlobalConfig.getAppIdDir().resolve("ds.user_action." + datasourceName + ".yml");

      Files.writeString(
          dsPath,
          "# $schema: http://schema.asemicanalytics.com/v1/semantic_layer/user_action_datasource.json\n"
              + YamlObjectMapperFactory.build().writeValueAsString(datasourceDto),
          StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

      System.out.println("Datasource saved to " + dsPath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
