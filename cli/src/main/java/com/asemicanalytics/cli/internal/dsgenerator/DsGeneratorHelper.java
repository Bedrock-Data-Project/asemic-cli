package com.asemicanalytics.cli.internal.dsgenerator;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.cli.InputCli;
import com.asemicanalytics.cli.internal.cli.SpinnerCli;
import com.asemicanalytics.cli.model.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EventColumnDto;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.SequencedMap;

public class DsGeneratorHelper {
  private final QueryEngineClient queryEngineClient;
  private final Optional<Boolean> noWizard;

  public DsGeneratorHelper(QueryEngineClient queryEngineClient, Optional<Boolean> noWizard) {
    this.queryEngineClient = queryEngineClient;
    this.noWizard = noWizard;
  }

  public String recommendedlogicalTableName(String table) {
    return Arrays.stream(table.split("\\.")).toList().getLast();
  }

  public String readInput(Optional<String> option, String optionName,
                          Optional<String> header, String prompt,
                          Optional<String> defaultValue) {
    return option.orElseGet(() -> {
      if (noWizard.isPresent() && noWizard.get()) {
        throw new RuntimeException("Missing required option --"
            + optionName + " (or omit --no-wizard flag)");
      }
      header.ifPresent(System.out::println);
      return new InputCli(prompt, defaultValue, s -> true).read();
    });
  }

  private void printColumn(String id, String name) {
    System.out.println("    " + id + " [" + name + "]");
  }

  public List<ColumnDto> getTableSchema(String table) {
    System.out.println("Getting table schema...");
    var columns = new SpinnerCli().spin(() ->
        queryEngineClient.getColumns(GlobalConfig.getAppId(), table));

    System.out.println("Columns:");
    for (var column : columns) {
      printColumn(column.getId(), column.getDataType());
    }
    return columns;
  }

  public void printColumns(
      SequencedMap<String, EventColumnDto> columns) {
    System.out.println("Columns:");
    for (var entry : columns.entrySet()) {
      printColumn(entry.getKey(), entry.getValue().getDataType().value());
    }
  }
}
