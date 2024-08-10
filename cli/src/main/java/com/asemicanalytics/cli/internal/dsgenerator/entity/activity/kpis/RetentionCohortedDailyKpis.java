package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortSizeColumn;
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
        "SAFE_DIVIDE(%s * 100, SUM(%s))"
            .formatted(DauKpi.KPI_REF, CohortSizeColumn.KPI_REF),
        "%s = {}".formatted(CohortDayColumn.KPI_REF),
        new UnitDto("%", false),
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(dateColumn, new XaxisOverrideDto());
        }},
        "cohort_day");
  }
}
