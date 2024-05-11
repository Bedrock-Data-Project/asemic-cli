package com.asemicanalytics.cli.semanticlayer;

import com.asemicanalytics.cli.semanticlayer.internal.GlobalConfig;
import com.asemicanalytics.cli.semanticlayer.internal.QueryEngineClient;
import com.asemicanalytics.cli.semanticlayer.internal.YamlObjectMapperFactory;
import com.asemicanalytics.cli.semanticlayer.internal.cli.InputCli;
import com.asemicanalytics.cli.semanticlayer.internal.cli.SpinnerCli;
import com.asemicanalytics.semanticlayer.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.dto.v1.semantic_layer.StaticDatasourceDto;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "generate-ds-static", mixinStandardHelpOptions = true)
public class GenerateDsStaticCommand implements Runnable {

  @Option(names = "--table", description = "Full table name.")
  Optional<String> table;

  @Option(names = "--datasource-name", description = "Name of generated datasource.")
  Optional<String> datasourceName;

  @Option(names = "--no-wizard", description = "Fails if any required input is missing."
      + " Usefull for scripting.")
  Optional<Boolean> noWizard;

  @Inject
  QueryEngineClient queryEngineClient;

  private List<com.asemicanalytics.cli.model.ColumnDto> getTableSchema(String table) {
    var columns = new SpinnerCli().spin(() ->
        queryEngineClient.getColumns(GlobalConfig.getAppId(), table));

    System.out.println("Columns:");
    for (var column : columns) {
      System.out.println(
          "    " + column.getId() + " [" + column.getDataType().toUpperCase() + "]");
    }
    return columns;
  }

  @Override
  public void run() {
    String table = new InputCli("Enter full table name").read();

    try {
      var columns = getTableSchema(table);

      String datasourceName = new InputCli(
          "Enter datasource name",
          Arrays.stream(table.split("\\.")).toList().getLast()).read();

      var datasourceDto = new StaticDatasourceDto(
          table,
          null,
          null,
          columns.stream().map(c -> new ColumnDto(
              c.getId(),
              ColumnDto.DataType.valueOf(c.getDataType().toUpperCase()),
              null,
              null,
              null,
              null
          )).collect(Collectors.toList()),
          List.of());

      Path dsPath = GlobalConfig.getAppIdDir().resolve("ds.static." + datasourceName + ".yml");

      Files.writeString(
          dsPath,
          "# $schema: http://schema.asemicanalytics.com/v1/semantic_layer/static_datasource.json\n"
              + YamlObjectMapperFactory.build().writeValueAsString(datasourceDto),
          StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

      System.out.println("Datasource saved to " + dsPath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
