package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import java.util.List;

public class DauKpi extends KpiDto {
  public DauKpi(String dateColumn) {
    super(
        "dau",
        "DAU",
        null,
        null,
        null,
        "SUM({property.dau})",
        null,
        null,
        List.of(dateColumn),
        KpiDto.TotalFunction.AVG,
        null);
  }
}
