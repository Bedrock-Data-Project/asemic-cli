package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;
import java.util.List;

public class LtvCohortKpi extends KpiDto {
  public LtvCohortKpi() {
    super(
        "ltv",
        "LTV",
        null,
        null,
        null,
        "SAFE_DIVIDE(SUM({property.total_revenue}), SUM({property.cohort_size}))",
        null,
        new UnitDto("$", true),
        List.of("cohort_day"),
        null,
        null);
  }
}
