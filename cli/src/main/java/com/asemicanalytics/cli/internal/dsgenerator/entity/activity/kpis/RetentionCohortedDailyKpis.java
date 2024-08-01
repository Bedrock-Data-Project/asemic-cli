package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class RetentionCohortedDailyKpis extends KpiDto {
  public static final String ID = "retention_d{}";

  public RetentionCohortedDailyKpis(String dateColumn) {
    super(
        null,
        null,
        "SAFE_DIVIDE({kpi.dau} * 100, SUM({property.cohort_size}))",
        "{property.cohort_day} = {}",
        new UnitDto("%", false),
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(dateColumn, new XaxisOverrideDto(KpiDto.TotalFunction.AVG));
        }},
        "cohort_day");
  }
}
