package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import java.util.List;

public class MDauCohortKpi extends KpiDto {
  public MDauCohortKpi() {
    super(
        "mdau",
        "mDAU",
        null,
        null,
        null,
        "{kpi.dau}",
        "{property.is_payer}",
        null,
        List.of("cohort_day"),
        null,
        null);
  }
}
