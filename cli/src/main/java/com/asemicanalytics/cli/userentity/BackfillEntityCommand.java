package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import jakarta.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import picocli.CommandLine;

@CommandLine.Command(name = "backfill", description = "Runs an interactive backfill for entity table", mixinStandardHelpOptions = true)
public class BackfillEntityCommand implements Runnable {

  @Inject
  QueryEngineClient queryEngineClient;

  @CommandLine.Option(names = "--app-id", description = "App id")
  String appId;

  @CommandLine.Option(names = "--date-from", description = "YYYY-MM-DD", required = true)
  LocalDate dateFrom;

  @CommandLine.Option(names = "--date-to", description = "YYYY-MM-DD", required = true)
  LocalDate dateTo;

  @CommandLine.Option(names = "--days-per-query", description = "How many days in a single query", defaultValue = "10")
  Integer daysPerQuery;

  @CommandLine.Option(names = "--version", description = "Custom config version")
  Optional<String> version;


  String prettyPrintDuration(Duration duration) {
    long days = duration.toDaysPart();
    long hours = duration.toHoursPart();
    long minutes = duration.toMinutesPart();
    long seconds = duration.toSecondsPart();

    return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes,
            seconds);
  }

  @Override
  public void run() {
    String appId = this.appId != null ? this.appId : GlobalConfig.getAppId();
    while (dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateTo)) {

      LocalDate intervalEnd = dateFrom.plusDays(daysPerQuery - 1);
      if (intervalEnd.isAfter(dateTo)) {
        intervalEnd = dateTo;
      }

      System.out.println("Backfilling (%s - %s) / %s".formatted(dateFrom, intervalEnd, dateTo));
      var start = Instant.now();
      var stats = queryEngineClient.backfillUserWide(appId, dateFrom, intervalEnd, version);
      var stop = Instant.now();

      var daysLeft = dateTo.toEpochDay() - intervalEnd.toEpochDay();
      for (var tableStatistic : stats) {

        System.out.println("Processed table: %s in %d seconds.".formatted(
                tableStatistic.getTable(),
                tableStatistic.getInsertDurationSeconds()));
        if (tableStatistic.getBytesProcessed() > 0) {
          System.out.println("Bytes processed: %s".formatted(
                  convertBytes(tableStatistic.getBytesProcessed())));
        }
        if (tableStatistic.getCountBytesProcessed() > 0) {
          System.out.println("Count query bytes processed: %s".formatted(
                  convertBytes(tableStatistic.getCountBytesProcessed())));
        }
        for (var partition : tableStatistic.getRowsInserted().entrySet()) {
          System.out.printf("Partition: %s, Rows: %d\n", partition.getKey(), partition.getValue());
        }
      }
      System.out.println("It took: " + prettyPrintDuration(Duration.between(start, stop)));
      System.out.println("Estimated backfill duration: " + prettyPrintDuration(
              Duration.between(start, stop)
                      .dividedBy(ChronoUnit.DAYS.between(dateFrom, intervalEnd))
                      .multipliedBy(daysLeft)));

      dateFrom = dateFrom.plusDays(daysPerQuery);
    }
  }

  private String convertBytes(long bytes) {
    int unit = 1024;
    if (bytes < unit) {
      return bytes + " B";
    }
    int exp = (int) (Math.log(bytes) / Math.log(unit));
    String pre = ("KMGTPE").charAt(exp - 1) + "i";
    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
  }
}