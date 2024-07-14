package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import java.util.List;

public class MauCohortKpi extends KpiDto {
  public MauCohortKpi() {
    super(
        "mau",
        "MAU",
        null,
        null,
        null,
        "SUM({property.mau})",
        null,
        null,
        List.of("cohort_day"),
        null,
        null);
  }
}
