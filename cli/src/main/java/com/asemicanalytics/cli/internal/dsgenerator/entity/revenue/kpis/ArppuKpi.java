package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;
import java.util.List;

public class ArppuKpi extends KpiDto {
  public ArppuKpi(String dateColumn) {
    super(
        "arppu",
        "ARPPU",
        null,
        null,
        null,
        "SAFE_DIVIDE({kpi.revenue}, {kpi.daily_payers})",
        null,
        new UnitDto("$", true),
        List.of(dateColumn, "cohort_day"),
        null,
        null);
  }
}
