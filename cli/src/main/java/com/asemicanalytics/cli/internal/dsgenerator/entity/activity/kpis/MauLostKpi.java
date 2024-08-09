package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.DaysSinceLastActiveColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortSizeColumn;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class MauLostKpi extends KpiDto {
  public static final String ID = "mau_lost";
  public static final String KPI_REF = "{kpi." + ID + "}";

  public MauLostKpi(String dateColumn) {
    super(
        "MAU Lost",
        null,
        "SUM(%s)".formatted(CohortSizeColumn.KPI_REF),
        "%s = 27".formatted(DaysSinceLastActiveColumn.KPI_REF),
        null,
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(dateColumn, new XaxisOverrideDto());
          this.setAdditionalProperty(CohortDayColumn.ID, new XaxisOverrideDto());
        }},
        null);
  }
}
