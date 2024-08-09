package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis;

import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortSizeColumn;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class LtvCohortKpi extends KpiDto {
  public static final String ID = "ltv";
  public static final String KPI_REF = "{kpi." + ID + "}";

  public LtvCohortKpi() {
    super(
        "LTV",
        null,
        "SAFE_DIVIDE(%s * 100, SUM(%s))".formatted(
            RevenueKpi.KPI_REF,
            CohortSizeColumn.KPI_REF),
        null,
        new UnitDto("%", false),
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(CohortDayColumn.ID, new XaxisOverrideDto());
        }},
        null);
  }
}
