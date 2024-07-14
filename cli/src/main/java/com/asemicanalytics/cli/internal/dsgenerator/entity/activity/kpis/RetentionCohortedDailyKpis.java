package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiCohortedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;

public class RetentionCohortedDailyKpis extends KpiCohortedDto {
  public RetentionCohortedDailyKpis() {
    super(
        "retention_d{}",
        null,
        null,
        null,
        null,
        "SAFE_DIVIDE({kpi.dau} * 100, SUM({property.cohort_size}))",
        "{property.cohort_day} = {}",
        new UnitDto("%", false),
        null,
        null,
        null);
  }
}
