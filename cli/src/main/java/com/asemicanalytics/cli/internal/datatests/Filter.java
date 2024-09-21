package com.asemicanalytics.cli.internal.datatests;

import com.asemicanalytics.cli.internal.CachingChartClient;
import com.asemicanalytics.cli.model.DateIntervalDto;
import com.asemicanalytics.cli.model.EntityChartRequestDto;
import com.asemicanalytics.cli.model.EntityChartRequestDtoTimeGrain;
import com.asemicanalytics.cli.model.PropertyFilterDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class Filter extends DataTest {
  private final Optional<String> kpi;
  private final LocalDate date;

  public Filter(String appId, String name,
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
            .dateFrom(date)
            .dateTo(date))
        .xaxis("date")
        .timeGrain(EntityChartRequestDtoTimeGrain.DAY);

    var data =
        queryEngineClient.submitChart(appId, request,
            Optional.empty());

    Double expected = 0.0;
    Double actual = 0.0;

    if (!data.getChartPoints().isEmpty()) {
      expected = data.getChartPoints().getFirst().getValues().getFirst().getValue();
    }

    var filter1 = new PropertyFilterDto();
    filter1.setColumnId("cohort_day");
    filter1.setOperation("=");
    filter1.setValueList(List.of("0"));
    request.setColumnFilters(List.of(filter1));
    data = queryEngineClient.submitChart(appId, request,
        Optional.empty());
    if (!data.getChartPoints().isEmpty()) {
      actual += data.getChartPoints().getFirst().getValues().getFirst().getValue();
    }

    var filter2 = new PropertyFilterDto();
    filter2.setColumnId("cohort_day");
    filter2.setOperation("!=");
    filter2.setValueList(List.of("0"));
    request.setColumnFilters(List.of(filter2));
    data = queryEngineClient.submitChart(appId, request,
        Optional.empty());
    if (!data.getChartPoints().isEmpty()) {
      actual += data.getChartPoints().getFirst().getValues().getFirst().getValue();
    }

    return new TestResult(
        String.format("%.2f", expected),
        String.format("%.2f", actual));
  }
}
