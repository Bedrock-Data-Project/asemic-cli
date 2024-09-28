package com.asemicanalytics.cli.internal.datatests;

import com.asemicanalytics.cli.internal.CachingChartClient;
import com.asemicanalytics.cli.model.DateIntervalDto;
import com.asemicanalytics.cli.model.EntityChartRequestDto;
import com.asemicanalytics.cli.model.EntityChartRequestDtoTimeGrain;
import java.time.LocalDate;
import java.util.Optional;

public class GroupByTotalOverall extends DataTest {
  private final Optional<String> kpi;
  private final Optional<String> groupBy;
  private final LocalDate date;

  public GroupByTotalOverall(String appId, String name,
                             Optional<String> kpi,
                             Optional<String> groupBy,
                             LocalDate date) {
    super(name, appId);
    this.kpi = kpi;
    this.groupBy = groupBy;
    this.date = date;
  }

  public boolean supported() {
    return kpi.isPresent() && groupBy.isPresent();
  }

  public TestResult run(CachingChartClient queryEngineClient) {
    var data =
        queryEngineClient.submitChart(appId, new EntityChartRequestDto()
                .pageId("")
                .requestId("")
                .kpiId(kpi.get())
                .dateInterval(new DateIntervalDto()
                    .dateFrom(date)
                    .dateTo(date))
                .xaxis("date")
                .timeGrain(EntityChartRequestDtoTimeGrain.DAY)
                .sortByKpiId(null)
                .groupByLimit(10)
                .addColumnGroupBysItem(groupBy.get()),
            Optional.empty());

    Double expected = 0.0;
    Double actual = 0.0;
    if (!data.getChartPoints().isEmpty()) {
      var linePoints = data.getChartPoints().getFirst().getValues();
      for (var point : linePoints) {
        expected += point.getValue();
      }
      actual = data.getChartTotalOverall();
    }
    return new TestResult(
        String.format("%.2f", expected),
        String.format("%.2f", actual));
  }
}
