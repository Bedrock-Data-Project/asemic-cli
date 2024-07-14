package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;
import java.util.List;

public class StickinessKpi extends KpiDto {
  public StickinessKpi(String dateColumn) {
    super(
        "stickiness",
        null,
        null,
        null,
        null,
        "SAFE_DIVIDE({kpi.dau} * 100, {kpi.mau})",
        null,
        new UnitDto("%", false),
        List.of(dateColumn),
        KpiDto.TotalFunction.AVG,
        null);
  }
}
