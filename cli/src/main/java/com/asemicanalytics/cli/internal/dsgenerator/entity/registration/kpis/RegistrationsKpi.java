package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.kpis;

import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class RegistrationsKpi extends KpiDto {
  public static final String ID = "registrations";

  public RegistrationsKpi(String dateColumn) {
    super(
        null,
        null,
        "SUM({property.cohort_size})",
        "{property.cohort_day} = 0",
        null,
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(dateColumn, new XaxisOverrideDto());
        }},
        null);
  }
}
