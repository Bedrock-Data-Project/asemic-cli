package com.asemicanalytics.cli.config;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.cli.SpinnerCli;
import com.asemicanalytics.cli.model.LegacyEntityChartRequestDto;
import com.asemicanalytics.cli.model.ColumnDto;
import com.asemicanalytics.cli.model.ColumnFilterDto;
import com.asemicanalytics.cli.model.DateIntervalDto;
import com.asemicanalytics.cli.model.KpiDto;
import com.asemicanalytics.cli.model.LegacyEntityChartRequestDtoTimeGrain;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.swing.text.html.parser.Entity;
import picocli.CommandLine;

@CommandLine.Command(name = "validate", description = "Validate config without submitting it", mixinStandardHelpOptions = true)
public class ValidateCommand implements Runnable {

  @Inject
  QueryEngineClient queryEngineClient;

  private void testChart(String version, String logPrefix, KpiDto kpi,
                         boolean isDailyKpi, ColumnDto column) {

    try {
      new SpinnerCli().spin(() -> {
        var yesterday = LocalDate.now().minusDays(1);
        queryEngineClient.submitChart(GlobalConfig.getAppId(), new LegacyEntityChartRequestDto()
            .pageId("")
            .requestId("")
            .kpiId(kpi.getId())
            .dateInterval(new DateIntervalDto()
                .dateFrom(yesterday)
                .dateTo(yesterday))
            .xaxis(isDailyKpi
                ? "date"
                : "cohort_date")
            .columnFilters(column != null
                ? List.of(new ColumnFilterDto()
                .columnId(column.getId())
                .operation("is_not_null")
                .valueList(List.of()))
                : List.of())
            .columnGroupBys(List.of())
            .timeGrain(LegacyEntityChartRequestDtoTimeGrain.DAY)
            .sortByKpiId(null)
            .groupByLimit(10)
            .dryRun(true), Optional.of(version));

        return null;
      });
      System.out.println(
          CommandLine.Help.Ansi.AUTO.string(logPrefix + dots(logPrefix)
              + "@|fg(green) SUCCESS|@"));
    } catch (Exception e) {
      System.out.println(
          CommandLine.Help.Ansi.AUTO.string(logPrefix + dots(logPrefix)
              + "@|fg(red) FAILED|@"));
      System.out.println("    " + e);
    }
  }

  private String dots(String prefix) {
    return ".".repeat(60 - prefix.length());
  }

  @Override
  public void run() {
    final String devVersion = "dev/" + UUID.randomUUID();

    var logicalTables = new SpinnerCli().spin(() -> {
      try {
        PushCommand.push(queryEngineClient, Optional.of(devVersion));
        return queryEngineClient.getDailyDatasources(GlobalConfig.getAppId(),
            Optional.of(devVersion));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    for (var logicalTable : logicalTables.entrySet()) {
      if (logicalTable.getValue().getKpis().isEmpty()) {
        continue;
      }
      for (var column : logicalTable.getValue().getColumns()) {
        testChart(devVersion, "column " + column.getLabel(),
            logicalTable.getValue().getKpis().get(0),
            logicalTable.getValue().getKpis().get(0).getIsDailyKpi(), column);
      }
      for (var kpi : logicalTable.getValue().getKpis()) {
        if (kpi.getIsDailyKpi()) {
          testChart(devVersion, "kpi (daily) " + kpi.getLabel(), kpi, true, null);
        }
        if (kpi.getIsCohortKpi()) {
          testChart(devVersion, "kpi (cohort) " + kpi.getLabel(), kpi, false, null);
        }
      }
    }
  }
}
