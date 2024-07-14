package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import java.util.List;

public class DailyPayersKpi extends KpiDto {
  public DailyPayersKpi(String dateColumn) {
    super(
        "daily_payers",
        null,
        null,
        null,
        null,
        "SUM({property.daily_payers})",
        null,
        null,
        List.of(dateColumn, "cohort_day"),
        KpiDto.TotalFunction.AVG,
        null);
  }
}
