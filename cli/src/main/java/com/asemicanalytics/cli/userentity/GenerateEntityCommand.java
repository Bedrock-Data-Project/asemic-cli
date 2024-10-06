package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.YamlSerDe;
import com.asemicanalytics.cli.internal.dsgenerator.DsGeneratorHelper;
import com.asemicanalytics.cli.internal.dsgenerator.entity.Activity;
import com.asemicanalytics.cli.internal.dsgenerator.entity.PaymentTransaction;
import com.asemicanalytics.cli.internal.dsgenerator.entity.Registrations;
import com.asemicanalytics.config.mapper.ConfigLoader;
import com.asemicanalytics.config.parser.yaml.YamlConfigParser;
import com.asemicanalytics.core.logicaltable.event.ActivityLogicalTable;
import com.asemicanalytics.core.logicaltable.event.RegistrationsLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityConfigDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityKpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "entity", description = "Generate entity config", mixinStandardHelpOptions = true)
public class GenerateEntityCommand implements Runnable {

  @Option(names = "--schema",
      description = "Schema where asemic will generate entity tables")
  Optional<String> schemaOption;

  @Option(names = "--no-wizard", description = "Fails if any required input is missing."
      + " Useful for scripting.")
  Optional<Boolean> noWizard;

  @Inject
  QueryEngineClient queryEngineClient;

  private static final int ACTIVE_DAYS = 90;

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
    final String schema = dsGeneratorHelper.readInput(
        schemaOption, "base-table-prefix",
        Optional.of("Enter the name of schema where asemic will generate entity model tables."),
        "Enter schema name", Optional.empty());

    try {
      var events = new ConfigLoader(parser)
          .parseEvents(GlobalConfig.getAppId(), new ArrayList<>());

      var paymentTransaction = events.getEventLogicalTables().values().stream()
          .filter(a -> a.hasTag("payment_transaction_event"))
          .findFirst();
      var registration = new RegistrationsLogicalTable(null,
          events.getEventLogicalTables().values().stream()
              .filter(a -> a.hasTag(RegistrationsLogicalTable.TAG))
              .toList());
      var activity = new ActivityLogicalTable(null,
          events.getEventLogicalTables().values().stream()
              .filter(a -> a.hasTag(ActivityLogicalTable.TAG))
              .toList());

      final EntityPropertiesDto registrationColumns;
      final EntityKpisDto registrationKpis;
      registrationColumns = Registrations.buildProperties(registration);
      registrationKpis = Registrations.buildKpis(registration);


      final EntityPropertiesDto activityColumns;
      final EntityKpisDto activityKpis;
      activityColumns = Activity.buildProperties(activity);
      activityKpis = Activity.buildKpis(activity, ACTIVE_DAYS);

      final Optional<EntityPropertiesDto> revenueColumns;
      final Optional<EntityKpisDto> revenueKpis;
      if (paymentTransaction.isPresent()) {
        revenueColumns = Optional.of(PaymentTransaction.buildProperties(
            paymentTransaction.get()));
        revenueKpis = Optional.of(PaymentTransaction.buildKpis(
            paymentTransaction.get()));
      } else {
        revenueColumns = Optional.empty();
        revenueKpis = Optional.empty();
      }

      columnsPath.toFile().mkdirs();
      kpisPath.toFile().mkdirs();

      new YamlSerDe().save(
          "entity_config",
          new EntityConfigDto(
              schema,
              "",
              List.of(0, 1, 2, 3, 4, 5, 6, 7, 14, 21, 28, 30, 40, 50, 60, 90, 120, 180, 270, 360),
              ACTIVE_DAYS),
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
