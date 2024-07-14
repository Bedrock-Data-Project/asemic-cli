package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import java.util.List;

public class MDauKpi extends KpiDto {
  public MDauKpi(String dateColumn) {
    super(
        "mdau",
        "mDAU",
        null,
        null,
        null,
        "SUM({kpi.dau})",
        "{property.is_payer}",
        null,
        List.of(dateColumn),
        KpiDto.TotalFunction.AVG,
        null);
  }
}
