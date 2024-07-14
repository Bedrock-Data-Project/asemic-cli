package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import java.util.List;

public class DauCohortKpi extends KpiDto {
  public DauCohortKpi() {
    super(
        "dau",
        "DAU",
        null,
        null,
        null,
        "SUM({property.dau})",
        null,
        null,
        List.of("cohort_day"),
        null,
        null);
  }

}
