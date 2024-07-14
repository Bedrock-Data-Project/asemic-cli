package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import java.util.List;

public class MauKpi extends KpiDto {
  public MauKpi(String dateColumn) {
    super(
        "mau",
        "MAU",
        null,
        null,
        null,
        "SUM({property.mau})",
        null,
        null,
        List.of(dateColumn),
        KpiDto.TotalFunction.AVG,
        null);
  }
}
