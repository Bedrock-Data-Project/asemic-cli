package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class StickinessKpi extends KpiDto {
  public static final String ID = "stickiness";

  public StickinessKpi(String dateColumn) {
    super(
        null,
        null,
        "SAFE_DIVIDE({kpi.dau} * 100, {kpi.mau})",
        null,
        new UnitDto("%", false),
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(dateColumn, new XaxisOverrideDto());
        }},
        null);
  }
}
