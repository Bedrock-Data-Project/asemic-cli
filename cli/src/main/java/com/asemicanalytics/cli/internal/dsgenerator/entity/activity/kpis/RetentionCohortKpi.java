package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;
import java.util.List;

public class RetentionCohortKpi extends KpiDto {
  public RetentionCohortKpi() {
    super(
        "retention",
        null,
        null,
        null,
        true,
        "SAFE_DIVIDE({kpi.dau} * 100, SUM({property.cohort_size}))",
        null,
        new UnitDto("%", false),
        List.of("cohort_day"),
        null,
        null);
  }
}
