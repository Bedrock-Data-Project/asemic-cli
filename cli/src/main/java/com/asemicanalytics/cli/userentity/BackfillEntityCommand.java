package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.cli.SpinnerCli;
import jakarta.inject.Inject;
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



  @Override
  public void run() {
    String appId = this.appId != null ? this.appId : GlobalConfig.getAppId();
    while (dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateTo)) {
      System.out.println("Backfilling date: " + dateFrom + " / " + dateTo);
      new SpinnerCli().spin(() -> {
        queryEngineClient.backfillUserWide(appId, dateFrom, version);
        return true;
      });
      dateFrom = dateFrom.plusDays(1);
    }
  }
}
