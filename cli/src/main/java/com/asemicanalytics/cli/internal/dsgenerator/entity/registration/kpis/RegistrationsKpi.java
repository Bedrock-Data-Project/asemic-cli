package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.kpis;

import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortSizeColumn;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class RegistrationsKpi extends KpiDto {
  public static final String ID = "registrations";
  public static final String KPI_REF = "{kpi." + ID + "}";

  public RegistrationsKpi(String dateColumn) {
    super(
        null,
        null,
        "SUM(%s)".formatted(CohortSizeColumn.KPI_REF),
        "%s = 0".formatted(CohortDayColumn.KPI_REF),
        null,
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(dateColumn, new XaxisOverrideDto());
        }},
        null);
  }
}
