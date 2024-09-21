package com.asemicanalytics.cli.internal.datatests;

import com.asemicanalytics.cli.internal.CachingChartClient;
import com.asemicanalytics.cli.model.DateIntervalDto;
import com.asemicanalytics.cli.model.EntityChartRequestDto;
import com.asemicanalytics.cli.model.EntityChartRequestDtoTimeGrain;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class GroupByGroupTotalsAvg extends DataTest {
  private final Optional<String> kpi;
  private final Optional<String> groupBy;
  private final LocalDate date;

  public GroupByGroupTotalsAvg(String appId, String name,
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
                    .dateFrom(date.minusDays(1))
                    .dateTo(date))
                .xaxis("date")
                .timeGrain(EntityChartRequestDtoTimeGrain.DAY)
                .sortByKpiId(null)
                .groupByLimit(10)
                .addColumnGroupBysItem(groupBy.get()),
            Optional.empty());

    Map<String, Double> expected = new TreeMap<>();
    Map<String, Double> actual = new TreeMap<>();

    if (!data.getChartPoints().isEmpty()) {
      for (var point : data.getChartPoints()) {
        for (var linePoint : point.getValues()) {
          expected.put(
              linePoint.getGroupByKey().toString(),
              expected.getOrDefault(linePoint.getGroupByKey().toString(), 0.0) +
                  linePoint.getValue());
        }
      }

      for (var point : data.getChartTotal()) {
        actual.put(
            point.getGroupByKey().toString(),
            point.getValue());
      }
    }

    expected.replaceAll((k, v) -> Math.round(v * 100.0) / 100.0 / data.getChartPoints().size());
    actual.replaceAll((k, v) -> Math.round(v * 100.0) / 100.0);
    return new TestResult(
        expected.toString(),
        actual.toString());
  }
}
