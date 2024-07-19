package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import java.util.List;

public class MauLostKpi extends KpiDto {
  public MauLostKpi(String dateColumn) {
    super(
        "mau_lost",
        "MAU Lost",
        null,
        null,
        null,
        "SUM(if({property.%s} - {property.last_login_date} = interval 29 day, 1, 0))".formatted(
            dateColumn),
        null,
        null,
        List.of(dateColumn, "cohort_day"),
        null,
        null);
  }
}
