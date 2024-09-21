package com.asemicanalytics.cli.internal.datatests;

import com.asemicanalytics.cli.internal.CachingChartClient;
import com.asemicanalytics.cli.model.AggregationFilterDto;
import com.asemicanalytics.cli.model.AggregationFilterDtoAggregationFunction;
import com.asemicanalytics.cli.model.DateIntervalDto;
import com.asemicanalytics.cli.model.EntityChartRequestDto;
import com.asemicanalytics.cli.model.EntityChartRequestDtoTimeGrain;
import com.asemicanalytics.cli.model.PropertyFilterDto;
import com.asemicanalytics.cli.model.RelativeWindowDto;
import com.asemicanalytics.cli.model.TimeTravelActionDynamicCohortFilterDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PerformedActionSimpleWithFilter extends DataTest {
  private final Optional<String> kpi;
  private final Optional<String> actionColumn;
  private final LocalDate date;

  public PerformedActionSimpleWithFilter(String appId, String name,
                                         Optional<String> kpi,
                                         Optional<String> actionColumn,
                                         LocalDate date) {
    super(name, appId);
    this.kpi = kpi;
    this.date = date;
    this.actionColumn = actionColumn;
  }

  public boolean supported() {
    return kpi.isPresent() && actionColumn.isPresent();
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

    var filter1 = new TimeTravelActionDynamicCohortFilterDto()
        .action(actionColumn.get().split("\\.")[0])
        .window(new RelativeWindowDto().from(0).to(0))
        .addFiltersItem(new PropertyFilterDto()
            .columnId(actionColumn.get().split("\\.")[1])
            .operation(">")
            .valueList(List.of("1")))
        .addAggregationFiltersItem(new AggregationFilterDto()
            .aggregationFunction(AggregationFilterDtoAggregationFunction.COUNT)
            .operation("=")
            .valueList(List.of("0")));
    request.setPerformedActionDynamicCohortFilters(
        List.of(filter1));
    data =
        queryEngineClient.submitChart(appId, request,
            Optional.empty());
    if (!data.getChartPoints().isEmpty()) {
      actual += data.getChartPoints().get(0).getValues().getFirst().getValue();
    }

    var filter2 = new TimeTravelActionDynamicCohortFilterDto()
        .action(actionColumn.get().split("\\.")[0])
        .window(new RelativeWindowDto().from(0).to(0))
        .addFiltersItem(new PropertyFilterDto()
            .columnId(actionColumn.get().split("\\.")[1])
            .operation(">")
            .valueList(List.of("1")))
        .addAggregationFiltersItem(new AggregationFilterDto()
            .aggregationFunction(AggregationFilterDtoAggregationFunction.COUNT)
            .operation(">")
            .valueList(List.of("0")));
    request.setPerformedActionDynamicCohortFilters(
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
