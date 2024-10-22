package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.Optional;
import java.util.TreeMap;
import picocli.CommandLine;

@CommandLine.Command(name = "backfill-statistics", description = "Show statistics for backfilled properties", mixinStandardHelpOptions = true)
public class BackfillStatisticsCommand implements Runnable {

  QueryEngineClient queryEngineClient = new QueryEngineClient();

  @CommandLine.Option(names = "--app-id", description = "App id")
  String appId;

  @CommandLine.Option(names = "--date-from", description = "YYYY-MM-DD", required = true)
  LocalDate dateFrom;

  @CommandLine.Option(names = "--date-to", description = "YYYY-MM-DD", required = true)
  LocalDate dateTo;

  @CommandLine.Option(names = "--version", description = "Custom config version")
  Optional<String> version;

  @Override
  public void run() {
    String appId = this.appId != null ? this.appId : GlobalConfig.getAppId();

    var stats = new TreeMap<>(
        queryEngineClient.backfillUserWideStatistics(appId, dateFrom, dateTo, version));
    for (var statistics : stats.entrySet()) {

      double percentage = statistics.getValue().getMaterializedProperties() * 100.0 /
          (statistics.getValue().getMaterializedProperties() +
              statistics.getValue().getNonMaterializedProperties());
      String formattedPercentage = String.format("%.0f", percentage) + "%";
      String message = "%s: %s (%d materialized, %d non-materialized)".formatted(
          statistics.getKey(), formattedPercentage,
          statistics.getValue().getMaterializedProperties(),
          statistics.getValue().getNonMaterializedProperties());

      if (percentage == 0.0) {
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|fg(red) " + message + "|@"));
      } else if (percentage == 100.0) {
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|fg(green) " + message + "|@"));
      } else {
        System.out.println(CommandLine.Help.Ansi.AUTO.string("@|fg(yellow) " + message + "|@"));
      }
    }

  }
}
