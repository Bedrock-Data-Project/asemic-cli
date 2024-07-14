package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiCohortedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;

public class LtvCohortedDailyKpis extends KpiCohortedDto {
  public LtvCohortedDailyKpis(String dateColumn) {
    super(
        "ltv_d{}",
        null,
        null,
        null,
        null,
        "SAFE_DIVIDE(SUM({property.total_revenue}) * 100, SUM({property.cohort_size}))",
        "{property.cohort_day} = {}",
        new UnitDto("$", true),
        null,
        null,
        null);
  }
}
