package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.model.DateIntervalDto;
import com.asemicanalytics.cli.model.EntityChartRequestDto;
import com.asemicanalytics.cli.model.EntityChartRequestDtoTimeGrain;
import jakarta.inject.Inject;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import java.time.LocalDate;
import java.util.Optional;


    @CommandLine.Command(name = "kpi-query", description = "Queries a predefined KPI without sending the existing configuration", mixinStandardHelpOptions = true)
    public class KpiQueryCommand implements Runnable {

        @Inject
        QueryEngineClient queryEngineClient;

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
            try {
                var request = new EntityChartRequestDto()
                        .pageId("")
                        .requestId("")
                        .kpiId(kpi)
                        .dateInterval(new DateIntervalDto()
                                .dateFrom(dateFrom)
                                .dateTo(dateTo))
                        .compareDateInterval(new DateIntervalDto()
                                .dateFrom(dateFrom.minusDays(2))
                                .dateTo(dateFrom.minusDays(1)))
                        .xaxis("date")
                        .timeGrain(EntityChartRequestDtoTimeGrain.DAY);


                var data = queryEngineClient.submitChart(appId, request, Optional.empty());


                processResults(data);
            } catch (Exception e) {
                System.err.println("Error querying KPI: " + e.getMessage());
            }
        }

        private void processResults(Object data) {
            if (data == null) {
                System.out.println("No results found for the specified KPI.");
            } else {
                System.out.println("Query Results:");
            }
        }
    }

