package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.MauActiveColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class MauKpi extends KpiDto {
  public static final String ID = "mau";
  public static final String KPI_REF = "{kpi." + ID + "}";

  public MauKpi(String dateColumn) {
    super(
        "MAU",
        null,
        "SUM(%s)".formatted(MauActiveColumn.KPI_REF),
        null,
        null,
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(dateColumn, new XaxisOverrideDto(TotalFunction.AVG));
          this.setAdditionalProperty(CohortDayColumn.ID, new XaxisOverrideDto());
        }},
        null);
  }
}
