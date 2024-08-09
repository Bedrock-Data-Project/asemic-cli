package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis;

import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.DauKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class ArpdauKpi extends KpiDto {
  public static final String ID = "arpdau";
  public static final String KPI_REF = "{kpi." + ID + "}";

  public ArpdauKpi(String dateColumn) {
    super(
        "ARPDAU",
        null,
        "SAFE_DIVIDE(%s, %s)".formatted(RevenueKpi.KPI_REF, DauKpi.KPI_REF),
        null,
        new UnitDto("$", true),
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(dateColumn, new XaxisOverrideDto());
          this.setAdditionalProperty(CohortDayColumn.ID, new XaxisOverrideDto());
        }},
        null);
  }
}
