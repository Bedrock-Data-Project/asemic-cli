package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UnitDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class RetentionCohortKpi extends KpiDto {
  public static final String ID = "retention";

  public RetentionCohortKpi() {
    super(
        null,
        null,
        "SAFE_DIVIDE({kpi.dau} * 100, SUM({property.cohort_size}))",
        null,
        new UnitDto("%", false),
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(CohortDayColumn.ID, new XaxisOverrideDto());
        }},
        null);
  }
}
