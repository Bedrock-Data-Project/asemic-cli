package com.asemicanalytics.cli.semanticlayer;

import com.asemicanalytics.cli.semanticlayer.internal.GlobalConfig;
import com.asemicanalytics.cli.semanticlayer.internal.QueryEngineClient;
import com.asemicanalytics.cli.semanticlayer.internal.YamlObjectMapperFactory;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.DsGeneratorHelper;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.ActivityColumns;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.RegistrationColumns;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.RevenueColumns;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserActionDatasourceDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideConfigDto;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "generate-ds-user-wide", mixinStandardHelpOptions = true)
public class GenerateDsUserWideCommand implements Runnable {

  @Option(names = "--base-table-prefix",
      description = "Prefix of base table. Generated tables will use this prefix")
  Optional<String> tablePrefixOption;

  @Option(names = "--registration-datasource", description = "Name of registration datasource.")
  Optional<String> registrationDatasourceOption;

  @Option(names = "--registration-datasource-country-column", description = "Name of country column in registration datasource.")
  Optional<String> registrationDatasourceCountryColumnOption;

  @Option(names = "--registration-datasource-platform-column", description = "Name of platform column in registration datasource.")
  Optional<String> registrationDatasourcePlatformColumnOption;

  @Option(names = "--activity-datasource", description = "Name of activity datasource.")
  Optional<String> activityDatasourceOption;

  @Option(names = "--√-datasource-country-column", description = "Name of country column in activity datasource.")
  Optional<String> activityDatasourceCountryColumnOption;

  @Option(names = "--activity -datasource-platform-column", description = "Name of platform column in activity datasource.")
  Optional<String> activityDatasourcePlatformColumnOption;

  @Option(names = "--revenue-datasource", description = "Name of revenue datasource.")
  Optional<String> revenueDatasourceOption;

  @Option(names = "--revenue-datasource-platform-column", description = "Name of revenue column in revenue datasource.")
  Optional<String> revenueDatasourceRevenueColumnOption;

  @Option(names = "--no-wizard", description = "Fails if any required input is missing."
      + " Usefull for scripting.")
  Optional<Boolean> noWizard;

  @Inject
  QueryEngineClient queryEngineClient;

  @Override
  public void run() {
    var userWidePath = GlobalConfig.getAppIdDir().resolve("user_wide");
    if (Files.exists(userWidePath)) {
      throw new IllegalArgumentException("User wide datasource already exists");
    }

    var dsGeneratorHelper = new DsGeneratorHelper(queryEngineClient, noWizard);

    final String tablePrefix = dsGeneratorHelper.readInput(
        tablePrefixOption, "base-table-prefix",
        Optional.of("Enter a prefix for all user wide tables." +
            "\nAll user wide tables will be created under this prefix (i.e. mydataset.user_wide)"),
        "Enter table prefix", Optional.empty());

    Map<String, UserActionDatasourceDto> userActionDatasources = loadUserActionDatasources();
    if (userActionDatasources.isEmpty()) {
      throw new IllegalArgumentException("No user action datasources found");
    }

    final String registrationDatasource = dsGeneratorHelper.readInput(
        registrationDatasourceOption, "registration-datasource",
        Optional.empty(), "Enter registration datasource name",
        Optional.empty());
    if (!userActionDatasources.containsKey(registrationDatasource)) {
      throw new IllegalArgumentException("Registration datasource not found");
    }
    dsGeneratorHelper.printColumns(userActionDatasources.get(registrationDatasource).getColumns());
    final String registrationCountryColumn = dsGeneratorHelper.readInput(
        registrationDatasourceCountryColumnOption, "registration-datasource-country-column",
        Optional.of(
            "\nIs there a column in registration datasource that represents the registration country of the user?"
                + "\nIf so, enter the column name. Otherwise, leave empty"),
        "Enter country column name", Optional.empty());
    if (!registrationCountryColumn.isEmpty()
        && userActionDatasources.get(registrationDatasource).getColumns()
        .stream().filter(c -> c.getId().equals(registrationCountryColumn)).findAny().isEmpty()) {
      throw new IllegalArgumentException("Country column not found in registration datasource");
    }

    final String registrationPlatformColumn = dsGeneratorHelper.readInput(
        registrationDatasourcePlatformColumnOption, "registration-datasource-platform-column",
        Optional.of(
            "\nIs there a column in registration datasource that represents the platform country of the user?"
                + "\nIf so, enter the column name. Otherwise, leave empty"),
        "Enter platform column name", Optional.empty());
    if (!registrationPlatformColumn.isEmpty()
        && userActionDatasources.get(registrationDatasource).getColumns()
        .stream().filter(c -> c.getId().equals(registrationPlatformColumn)).findAny().isEmpty()) {
      throw new IllegalArgumentException("Platform column not found in registration datasource");
    }



    final String activityDatasource = dsGeneratorHelper.readInput(
        activityDatasourceOption, "activity-datasource",
        Optional.empty(), "Enter activity datasource name",
        Optional.empty());
    final String loginCountryColumn = dsGeneratorHelper.readInput(
        activityDatasourceCountryColumnOption, "activity-datasource-country-column",
        Optional.of(
            "\nIs there a column in activity datasource that represents the login country of the user?"
                + "\nIf so, enter the column name. Otherwise, leave empty"),
        "Enter country column name", Optional.empty());
    if (!loginCountryColumn.isEmpty()
        && userActionDatasources.get(activityDatasource).getColumns()
        .stream().filter(c -> c.getId().equals(loginCountryColumn)).findAny().isEmpty()) {
      throw new IllegalArgumentException("Country column not found in activity datasource");
    }

    final String loginPlatformColumn = dsGeneratorHelper.readInput(
        activityDatasourcePlatformColumnOption, "activity-datasource-platform-column",
        Optional.of(
            "\nIs there a column in registration datasource that represents the platform country of the user?"
                + "\nIf so, enter the column name. Otherwise, leave empty"),
        "Enter platform column name", Optional.empty());
    if (!loginPlatformColumn.isEmpty()
        && userActionDatasources.get(activityDatasource).getColumns()
        .stream().filter(c -> c.getId().equals(loginPlatformColumn)).findAny().isEmpty()) {
      throw new IllegalArgumentException("Platform column not found in activity datasource");
    }

    final String revenueDatasource = dsGeneratorHelper.readInput(
        revenueDatasourceOption, "revenue-datasource",
        Optional.empty(), "Is there a datasource that contains revenue data?"
            + "\nIf so, enter the datasource name. Otherwise, leave empty",
        Optional.empty());
    if (!revenueDatasource.isEmpty() && !userActionDatasources.containsKey(revenueDatasource)) {
      throw new IllegalArgumentException("Revenue datasource not found");
    }

    final String revenueColumn;
    if (!revenueDatasource.isEmpty()) {
      revenueColumn = dsGeneratorHelper.readInput(
          revenueDatasourceRevenueColumnOption, "revenue-datasource-revenue-column",
          Optional.of(
              "\nEnter the name of the column that represents the revenue generated by the user"),
          "Enter platform column name", Optional.empty());
      if (userActionDatasources.get(revenueDatasource).getColumns()
          .stream().filter(c -> c.getId().equals(revenueColumn)).findAny().isEmpty()) {
        throw new IllegalArgumentException("Revenue column not found in revenue datasource");
      }
    } else {
      revenueColumn = "";
    }

    var registrationColumns = RegistrationColumns.build(
        registrationCountryColumn,
        registrationPlatformColumn);

    var activityColumns = ActivityColumns.build(
        activityDatasource,
        loginCountryColumn,
        loginPlatformColumn);

    var revenueColumns = RevenueColumns.build(
        revenueDatasource,
        revenueColumn);

    var userWideConfigDto = new UserWideConfigDto(tablePrefix);
  }

  private Map<String, UserActionDatasourceDto> loadUserActionDatasources() {
    Map<String, UserActionDatasourceDto> datasources = new HashMap<>();
    try (var files = Files.list(GlobalConfig.getAppIdDir())) {
      files.filter(p ->
              p.getFileName().toString().startsWith("ds.user_action.")
                  && p.getFileName().toString().endsWith(".yaml"))
          .forEach(p -> {
            try {
              UserActionDatasourceDto datasource = YamlObjectMapperFactory.build()
                  .readValue(Files.readString(p), UserActionDatasourceDto.class);
              datasources.put(p.getFileName().toString(), datasource);
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return datasources;
  }
}
