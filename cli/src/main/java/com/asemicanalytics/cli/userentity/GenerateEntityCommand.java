package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.YamlSerDe;
import com.asemicanalytics.cli.internal.dsgenerator.DsGeneratorHelper;
import com.asemicanalytics.cli.internal.dsgenerator.entity.ActivityColumns;
import com.asemicanalytics.cli.internal.dsgenerator.entity.PaymentTransactionColumns;
import com.asemicanalytics.cli.internal.dsgenerator.entity.FirstAppearanceColumns;
import com.asemicanalytics.config.EntityModelConfig;
import com.asemicanalytics.config.configloader.ConfigLoader;
import com.asemicanalytics.config.configparser.yaml.YamlConfigParser;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityConfigDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "entity", description = "Generate entity config", mixinStandardHelpOptions = true)
public class GenerateEntityCommand implements Runnable {

  @Option(names = "--base-table-prefix",
      description = "Prefix of base table. Generated tables will use this prefix")
  Optional<String> tablePrefixOption;

  @Option(names = "--no-wizard", description = "Fails if any required input is missing."
      + " Useful for scripting.")
  Optional<Boolean> noWizard;

  @Inject
  QueryEngineClient queryEngineClient;

  @Override
  public void run() {
    var columnsPath = GlobalConfig.getUserEntityDirt().resolve("properties");
    var kpisPath = GlobalConfig.getUserEntityDirt().resolve("kpis");

    if (Files.exists(columnsPath) || Files.exists(kpisPath)) {
      throw new IllegalArgumentException("properties/kpis directories already exist");
    }

    var dsGeneratorHelper = new DsGeneratorHelper(queryEngineClient, noWizard);
    final String tablePrefix = dsGeneratorHelper.readInput(
        tablePrefixOption, "base-table-prefix",
        Optional.of("Enter a prefix for all user wide tables." +
            "\nAll user wide tables will be created under this prefix (i.e. mydataset.user_wide)"),
        "Enter table prefix", Optional.empty());

    try {
      EntityModelConfig entityModelConfig = new ConfigLoader(new YamlConfigParser(
          new YamlSerDe(), GlobalConfig.getAppIdDir().getParent()))
          .parse(GlobalConfig.getAppId());

      final EntityPropertiesDto registrationColumns;
      if (entityModelConfig.getFirstAppearanceActionLogicalTable().isPresent()) {
        registrationColumns = FirstAppearanceColumns.build(
            entityModelConfig.getFirstAppearanceActionLogicalTable().get());
      } else {
        throw new IllegalArgumentException("First appearance action not found."
            + "Generate it using "
            + "first-appearance-action command first.");
      }

      final EntityPropertiesDto activityColumns;
      if (entityModelConfig.getActivityActionLogicalTable().isPresent()) {
        activityColumns = ActivityColumns.build(
            entityModelConfig.getActivityActionLogicalTable().get());
      } else {
        throw new IllegalArgumentException("Activity action not found."
            + "Generate it using "
            + "activity-action command first.");
      }

      final Optional<EntityPropertiesDto> revenueColumns;
      if (entityModelConfig.getPaymentTransactionActionLogicalTable().isPresent()) {
        revenueColumns = Optional.of(PaymentTransactionColumns.build(
            entityModelConfig.getPaymentTransactionActionLogicalTable().get()));
      } else {
        revenueColumns = Optional.empty();
      }

      columnsPath.toFile().mkdirs();
      kpisPath.toFile().mkdirs();

      new YamlSerDe().save(
          "entity_config",
          new EntityConfigDto(tablePrefix),
          columnsPath.resolve("config.yml"));

      new YamlSerDe().save(
          "entity_properties",
          registrationColumns,
          columnsPath.resolve("registration.yml"));

      new YamlSerDe().save(
          "entity_properties",
          registrationColumns,
          columnsPath.resolve("activity.yml"));

      if (revenueColumns.isPresent()) {
        new YamlSerDe().save(
            "entity_properties",
            revenueColumns.get(),
            columnsPath.resolve("revenue.yml"));
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
