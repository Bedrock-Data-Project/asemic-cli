package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.model.ChartDataDto;
import com.asemicanalytics.cli.model.DateIntervalDto;
import com.asemicanalytics.cli.model.EntityChartRequestDto;
import com.asemicanalytics.cli.model.EntityChartRequestDtoTimeGrain;

import jakarta.inject.Inject;
import java.time.ZoneId;
import net.bytebuddy.asm.Advice;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import java.time.LocalDate;
import java.util.Optional;


@CommandLine.Command(name = "kpi-query", description = "Queries a predefined KPI without sending the existing configuration", mixinStandardHelpOptions = true)
public class KpiQueryCommand implements Runnable {

  QueryEngineClient queryEngineClient = new QueryEngineClient();

  @CommandLine.Option(names = "--app-id", description = "App id")
  String appId;

  @CommandLine.Option(names = "--date-from", description = "Start date (YYYY-MM-DD)", required = true)
  LocalDate dateFrom;

  @CommandLine.Option(names = "--date-to", description = "End date (YYYY-MM-DD)", required = true)
  LocalDate dateTo;

  @CommandLine.Option(names = "--kpi", description = "The KPI to query", required = true)
  String kpi;


  @Override
  public void run() {
    String appId = this.appId != null ? this.appId : GlobalConfig.getAppId();
    try {
      var request = new EntityChartRequestDto()
          .pageId("")
          .requestId("")
          .kpiId(kpi)
          .dateInterval(new DateIntervalDto()
              .dateFrom(dateFrom.atStartOfDay(ZoneId.of("UTC")))
              .dateTo(dateTo.atStartOfDay(ZoneId.of("UTC"))))
          .compareDateInterval(new DateIntervalDto()
              .dateFrom(dateFrom.minusDays(2).atStartOfDay(ZoneId.of("UTC")))
              .dateTo(dateFrom.minusDays(1).atStartOfDay(ZoneId.of("UTC"))))
          .xaxis("date")
          .timeGrain(EntityChartRequestDtoTimeGrain.DAY);

      var data = queryEngineClient.submitChart(appId, request, Optional.empty());
      processResults(data);


    } catch (Exception e) {
      System.out.println("KPI Query Error: " + e.getMessage());
    }
  }

  private void processResults(Object data) {
    if (data == null) {
      System.out.println("No results found for the specified KPI.");
    } else {
      if(data instanceof ChartDataDto chartDataDto) {
        var chartPoints = chartDataDto.getGranular().getRows();
        if(!chartPoints.isEmpty()) {

          System.out.println("Query Results:");
          LocalDate currentDate = dateFrom;
          for(var point : chartPoints) {
            for(var value : point.getMetrics()) {
              System.out.printf("Date: %s, Value: %.2f%n", currentDate, value);
              currentDate = currentDate.plusDays(1);
            }
          }
        } else {
          System.out.println("No chart points found.");
        }
      } else {
        System.out.println("Unexpected data format.");
      }
    }
  }
}
