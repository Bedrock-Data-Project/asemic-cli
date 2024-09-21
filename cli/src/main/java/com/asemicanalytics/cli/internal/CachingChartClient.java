package com.asemicanalytics.cli.internal;

import com.asemicanalytics.cli.model.ChartDataDto;
import com.asemicanalytics.cli.model.EntityChartRequestDto;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CachingChartClient {
  private final QueryEngineClient client;
  private final Map<EntityChartRequestDto, ChartDataDto> cache = new HashMap<>();

  public CachingChartClient(QueryEngineClient client) {
    this.client = client;
  }

  public ChartDataDto submitChart(String appId, EntityChartRequestDto chartRequestDto,
                                  Optional<String> version) {
    if (cache.containsKey(chartRequestDto)) {
      return cache.get(chartRequestDto);
    } else {
      var chartData = client.submitChart(appId, chartRequestDto, version);
      cache.put(chartRequestDto, chartData);
      return chartData;
    }
  }

}
