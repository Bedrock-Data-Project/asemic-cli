package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import java.util.List;

public class RegistrationsKpi extends KpiDto {
  public RegistrationsKpi(String dateColumn) {
    super(
        "registrations",
        null,
        null,
        null,
        null,
        "SUM({property.cohort_size})",
        "{property.cohort_day} = 0",
        null,
        List.of(dateColumn, "cohort_day"),
        KpiDto.TotalFunction.AVG,
        null);
  }
}
