package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.cli.SpinnerCli;
import jakarta.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
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
      System.out.println("Backfilling date: " + dateFrom + " / " + dateTo);
      new SpinnerCli().spin(() -> {
        var start = Instant.now();
        var stats = queryEngineClient.backfillUserWide(appId, dateFrom, version);
        var stop = Instant.now();

        var daysLeft = dateTo.toEpochDay() - dateFrom.toEpochDay();
        for (var tableStatistic : stats) {

          System.out.printf("Inserted %d rows to table %s in %d seconds. processed: %S, billed: %s \n",
              tableStatistic.getRowsInserted(), tableStatistic.getTable(),
              tableStatistic.getInsertDurationSeconds(),
              convertBytes(tableStatistic.getBytesProcessed()),
              convertBytes(tableStatistic.getBytesBilled()));
        }
        System.out.println("Estimated backfill duration: " +
            prettyPrintDuration(Duration.between(start, stop).multipliedBy(daysLeft)));

        return true;
      });
      dateFrom = dateFrom.plusDays(1);
    }
  }

  private String convertBytes(long bytes) {
    int unit = 1024;
    if (bytes < unit) return bytes + " B";
    int exp = (int) (Math.log(bytes) / Math.log(unit));
    String pre = ("KMGTPE").charAt(exp-1) + "i";
    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
  }
}
