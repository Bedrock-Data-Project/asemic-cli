package com.asemicanalytics.cli.internal.datatests;

import com.asemicanalytics.cli.internal.CachingChartClient;
import com.asemicanalytics.cli.model.AggregationFilterDto;
import com.asemicanalytics.cli.model.AggregationFilterDtoAggregationFunction;
import com.asemicanalytics.cli.model.DateIntervalDto;
import com.asemicanalytics.cli.model.EntityChartRequestDto;
import com.asemicanalytics.cli.model.EntityChartRequestDtoTimeGrain;
import com.asemicanalytics.cli.model.RelativeWindowDto;
import com.asemicanalytics.cli.model.TimeTravelPropertyDynamicCohortFilterDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TimeTravelPropertySimple extends DataTest {
  private final Optional<String> kpi;
  private final Optional<String> sourceProperty;
  private final LocalDate date;

  public TimeTravelPropertySimple(String appId, String name,
                                  Optional<String> kpi,
                                  Optional<String> sourceProperty,
                                  LocalDate date) {
    super(name, appId);
    this.kpi = kpi;
    this.date = date;
    this.sourceProperty = sourceProperty;
  }

  public boolean supported() {
    return kpi.isPresent() && sourceProperty.isPresent();
  }

  public TestResult run(CachingChartClient queryEngineClient) {
    var request = new EntityChartRequestDto()
        .pageId("")
        .requestId("")
        .kpiId(kpi.get())
        .dateInterval(new DateIntervalDto()
            .dateFrom(date)
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

    if (!data.getChartPoints().isEmpty()) {
      expected = data.getChartPoints().get(0).getValues().getFirst().getValue();
    }

    var filter1 = new TimeTravelPropertyDynamicCohortFilterDto()
        .window(new RelativeWindowDto().from(0).to(0))
        .addAggregationFiltersItem(new AggregationFilterDto()
            .columnId(sourceProperty.get())
            .aggregationFunction(AggregationFilterDtoAggregationFunction.COUNT)
            .operation("=")
            .valueList(List.of("0")));
    request.setPropertyDynamicCohortFilters(
        List.of(filter1));
    data =
        queryEngineClient.submitChart(appId, request,
            Optional.empty());
    if (!data.getChartPoints().isEmpty()) {
      actual += data.getChartPoints().get(0).getValues().getFirst().getValue();
    }

    var filter2 = new TimeTravelPropertyDynamicCohortFilterDto()
        .window(new RelativeWindowDto().from(0).to(0))
        .addAggregationFiltersItem(new AggregationFilterDto()
            .columnId(sourceProperty.get())
            .aggregationFunction(AggregationFilterDtoAggregationFunction.COUNT)
            .operation(">")
            .valueList(List.of("0")));
    request.setPropertyDynamicCohortFilters(
        List.of(filter2));
    data =
        queryEngineClient.submitChart(appId, request,
            Optional.empty());
    if (!data.getChartPoints().isEmpty()) {
      actual += data.getChartPoints().get(0).getValues().getFirst().getValue();
    }

    return new TestResult(
        String.format("%.2f", expected),
        String.format("%.2f", actual));
  }
}
