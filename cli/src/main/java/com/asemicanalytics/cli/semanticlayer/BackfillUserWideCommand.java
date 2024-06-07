package com.asemicanalytics.cli.semanticlayer;

import com.asemicanalytics.cli.semanticlayer.internal.GlobalConfig;
import com.asemicanalytics.cli.semanticlayer.internal.QueryEngineClient;
import com.asemicanalytics.cli.semanticlayer.internal.ZipUtils;
import com.asemicanalytics.cli.semanticlayer.internal.cli.SpinnerCli;
import jakarta.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;
import picocli.CommandLine;

@CommandLine.Command(name = "backfill-user-wide", mixinStandardHelpOptions = true)
public class BackfillUserWideCommand implements Runnable {

  @Inject
  QueryEngineClient queryEngineClient;

  @CommandLine.Option(names = "--date-from", description = "YYYY-MM-DD", required = true)
  LocalDate dateFrom;

  @CommandLine.Option(names = "--date-to", description = "YYYY-MM-DD", required = true)
  LocalDate dateTo;

  @CommandLine.Option(names = "--version", description = "Custom config version")
  Optional<String> version;

  @Override
  public void run() {
    while (dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateTo)) {
      System.out.println("Backfilling date: " + dateFrom + " / " + dateTo);
      new SpinnerCli().spin(() -> {
          queryEngineClient.backfillUserWide(GlobalConfig.getAppId(), dateFrom, version);
          return true;
      });
      dateFrom = dateFrom.plusDays(1);
    }
  }
}
