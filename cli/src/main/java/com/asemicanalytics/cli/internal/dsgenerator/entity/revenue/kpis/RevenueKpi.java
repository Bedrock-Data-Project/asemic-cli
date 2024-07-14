package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;
import java.util.List;

public class RevenueKpi extends KpiDto {
  public RevenueKpi(String dateColumn) {
    super(
        "revenue",
        null,
        null,
        null,
        null,
        "SUM({property.revenue})",
        null,
        new UnitDto("$", true),
        List.of(dateColumn, "cohort_day"),
        KpiDto.TotalFunction.AVG,
        null);
  }
}
