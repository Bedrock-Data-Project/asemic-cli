package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis;

import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortSizeColumn;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class LtvCohortedDailyKpis extends KpiDto {
  public static final String ID = "ltv_d{}";

  public LtvCohortedDailyKpis(String dateColumn) {
    super(
        "LTV D{}",
        null,
        "SAFE_DIVIDE(%s * 100, SUM(%s))".formatted(
            RevenueKpi.KPI_REF,
            CohortSizeColumn.KPI_REF),
        "%s = {}".formatted(CohortDayColumn.KPI_REF),
        new UnitDto("$", true),
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(dateColumn, new XaxisOverrideDto());
        }},
        "cohort_day");
  }
}
