package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class LtvCohortedDailyKpis extends KpiDto {
  public static final String ID = "ltv_d{}";

  public LtvCohortedDailyKpis(String dateColumn) {
    super(
        "LTV D{}",
        null,
        "SAFE_DIVIDE(SUM({property.total_revenue}) * 100, SUM({property.cohort_size}))",
        "{property.cohort_day} = {}",
        new UnitDto("$", true),
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(dateColumn, new XaxisOverrideDto(KpiDto.TotalFunction.AVG));
        }},
        "cohort_day");
  }
}
