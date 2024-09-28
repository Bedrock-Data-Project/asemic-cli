package com.asemicanalytics.cli.internal.datatests;

import com.asemicanalytics.cli.internal.CachingChartClient;
import com.asemicanalytics.cli.model.DateIntervalDto;
import com.asemicanalytics.cli.model.EntityChartRequestDto;
import com.asemicanalytics.cli.model.EntityChartRequestDtoTimeGrain;
import java.time.LocalDate;
import java.util.Optional;

public class Compare extends DataTest {
  private final Optional<String> kpi;
  private final LocalDate date;

  public Compare(String appId, String name,
                 Optional<String> kpi,
                 LocalDate date) {
    super(name, appId);
    this.kpi = kpi;
    this.date = date;
  }

  public boolean supported() {
    return kpi.isPresent();
  }

  public TestResult run(CachingChartClient queryEngineClient) {
    var request = new EntityChartRequestDto()
        .pageId("")
        .requestId("")
        .kpiId(kpi.get())
        .dateInterval(new DateIntervalDto()
            .dateFrom(date.minusDays(1))
            .dateTo(date))
        .compareDateInterval(new DateIntervalDto()
            .dateFrom(date.minusDays(2))
            .dateTo(date.minusDays(1)))
        .xaxis("date")
        .timeGrain(EntityChartRequestDtoTimeGrain.DAY);

    var data =
        queryEngineClient.submitChart(appId, request,
            Optional.empty());

    Double expected = 0.0;
    Double actual = 0.0;

    if (data.getChartPoints().size() == 2) {
      expected = data.getChartPoints().get(0).getValues().getFirst().getValue();
    }

    if (!data.getComparePeriodChartPoints().isEmpty()) {
      actual = data.getComparePeriodChartPoints().get(1).getValues().getFirst().getValue();
    }

    return new TestResult(
        String.format("%.2f", expected),
        String.format("%.2f", actual));
  }
}
