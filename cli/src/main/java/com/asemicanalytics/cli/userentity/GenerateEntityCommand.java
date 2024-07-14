package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.YamlSerDe;
import com.asemicanalytics.cli.internal.dsgenerator.DsGeneratorHelper;
import com.asemicanalytics.cli.internal.dsgenerator.entity.Activity;
import com.asemicanalytics.cli.internal.dsgenerator.entity.FirstAppearance;
import com.asemicanalytics.cli.internal.dsgenerator.entity.PaymentTransaction;
import com.asemicanalytics.config.EntityModelConfig;
import com.asemicanalytics.config.mapper.ConfigLoader;
import com.asemicanalytics.config.parser.yaml.YamlConfigParser;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityConfigDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityKpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import jakarta.inject.Inject;
import java.io.IOException;
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
    var parser = new YamlConfigParser(
        new YamlSerDe(), GlobalConfig.getAppIdDir().getParent());

    var columnsPath = parser.propertiesDir(GlobalConfig.getAppId());
    var kpisPath = parser.kpisDir(GlobalConfig.getAppId());

    if (columnsPath.toFile().exists() || kpisPath.toFile().exists()) {
      throw new IllegalArgumentException("properties/kpis directories already exist");
    }

    var dsGeneratorHelper = new DsGeneratorHelper(queryEngineClient, noWizard);
    final String tablePrefix = dsGeneratorHelper.readInput(
        tablePrefixOption, "base-table-prefix",
        Optional.of("Enter a prefix for all user wide tables." +
            "\nAll user wide tables will be created under this prefix (i.e. mydataset.user_wide)"),
        "Enter table prefix", Optional.empty());

    try {
      EntityModelConfig entityModelConfig = new ConfigLoader(parser)
          .parse(GlobalConfig.getAppId());

      final EntityPropertiesDto registrationColumns;
      final EntityKpisDto registrationKpis;
      if (entityModelConfig.getFirstAppearanceActionLogicalTable().isPresent()) {
        registrationColumns = FirstAppearance.buildProperties(
            entityModelConfig.getFirstAppearanceActionLogicalTable().get());
        registrationKpis = FirstAppearance.buildKpis(
            entityModelConfig.getFirstAppearanceActionLogicalTable().get());
      } else {
        throw new IllegalArgumentException("First appearance action not found. "
            + "Generate it using "
            + "first-appearance-action command first.");
      }

      final EntityPropertiesDto activityColumns;
      final EntityKpisDto activityKpis;
      if (entityModelConfig.getActivityActionLogicalTable().isPresent()) {
        activityColumns = Activity.buildProperties(
            entityModelConfig.getActivityActionLogicalTable().get());
        activityKpis = Activity.buildKpis(
            entityModelConfig.getActivityActionLogicalTable().get());
      } else {
        throw new IllegalArgumentException("Activity action not found."
            + "Generate it using "
            + "activity-action command first.");
      }

      final Optional<EntityPropertiesDto> revenueColumns;
      final Optional<EntityKpisDto> revenueKpis;
      if (entityModelConfig.getPaymentTransactionActionLogicalTable().isPresent()) {
        revenueColumns = Optional.of(PaymentTransaction.buildProperties(
            entityModelConfig.getPaymentTransactionActionLogicalTable().get()));
        revenueKpis = Optional.of(PaymentTransaction.buildKpis(
            entityModelConfig.getPaymentTransactionActionLogicalTable().get()));
      } else {
        revenueColumns = Optional.empty();
        revenueKpis = Optional.empty();
      }

      columnsPath.toFile().mkdirs();
      kpisPath.toFile().mkdirs();

      new YamlSerDe().save(
          "entity_config",
          new EntityConfigDto(tablePrefix),
          columnsPath.getParent().resolve("config.yml"));

      new YamlSerDe().save(
          "entity_properties",
          registrationColumns,
          columnsPath.resolve("registration.yml"));
      new YamlSerDe().save(
          "entity_kpis",
          registrationKpis,
          kpisPath.resolve("registration.yml"));

      new YamlSerDe().save(
          "entity_properties",
          activityColumns,
          columnsPath.resolve("activity.yml"));
      new YamlSerDe().save(
          "entity_kpis",
          activityKpis,
          kpisPath.resolve("activity.yml"));

      if (revenueColumns.isPresent()) {
        new YamlSerDe().save(
            "entity_properties",
            revenueColumns.get(),
            columnsPath.resolve("revenue.yml"));
        new YamlSerDe().save(
            "entity_kpis",
            revenueKpis.get(),
            kpisPath.resolve("revenue.yml"));
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
