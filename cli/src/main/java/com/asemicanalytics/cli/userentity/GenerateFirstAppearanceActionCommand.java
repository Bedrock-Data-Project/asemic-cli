package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.dsgenerator.DsGeneratorHelper;
import com.asemicanalytics.cli.model.ColumnDto;
import com.asemicanalytics.core.logicaltable.action.FirstAppearanceActionLogicalTable;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "first-appearance-action", description = "Generate config for a first appearance action.", mixinStandardHelpOptions = true)
public class GenerateFirstAppearanceActionCommand extends GenerateActionCommand {

  @Option(names = "--country-column", description = "Name of country column.")
  Optional<String> countryColumnOption;

  @Option(names = "--platform-column", description = "Name of platform column.")
  Optional<String> platformColumnOption;

  @Option(names = "--build-version-column", description = "Name of build version column.")
  Optional<String> buildVersionColumnOption;

  @Inject
  QueryEngineClient queryEngineClient;

  @Inject
  ObjectMapper objectMapper;

  protected List<String> logicalTableTags() {
    return List.of(FirstAppearanceActionLogicalTable.TAG);
  }

  protected Map<String, List<String>> additionalColumnTags(List<ColumnDto> columns) {
    var dsGeneratorHelper = new DsGeneratorHelper(queryEngineClient, noWizard);

    Map<String, List<String>> columnTags = new HashMap<>();

    final String countryColumn = dsGeneratorHelper.readInput(
        countryColumnOption, "country-column",
        Optional.of(
            "\nIs there a column that represents the registration country of the user?"
                + "\nIf so, enter the column name. Otherwise, leave empty"),
        "Enter country column name", Optional.empty());
    if (!countryColumn.isEmpty()) {
      columnTags.put(countryColumn, List.of(FirstAppearanceActionLogicalTable.COUNTRY_COLUMN_TAG));
    }

    final String platformColumn = dsGeneratorHelper.readInput(
        platformColumnOption, "platform-column",
        Optional.of(
            "\nIs there a column that represents the registration platform of the user?"
                + "\nIf so, enter the column name. Otherwise, leave empty"),
        "Enter platform column name", Optional.empty());
    if (!platformColumn.isEmpty()) {
      columnTags.put(platformColumn,
          List.of(FirstAppearanceActionLogicalTable.PLATFORM_COLUMN_TAG));
    }

    final String buildVersionColumn = dsGeneratorHelper.readInput(
        buildVersionColumnOption, "build-version-column",
        Optional.of(
            "\nIs there a column that represents the registration build version of the user?"
                + "\nIf so, enter the column name. Otherwise, leave empty"),
        "Enter build version column name", Optional.empty());
    if (!buildVersionColumn.isEmpty()) {
      columnTags.put(buildVersionColumn,
          List.of(FirstAppearanceActionLogicalTable.BUILD_VERSION_COLUMN_TAG));
    }

    return columnTags;
  }

}
