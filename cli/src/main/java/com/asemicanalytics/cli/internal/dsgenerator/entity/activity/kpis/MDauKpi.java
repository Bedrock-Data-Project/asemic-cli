package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.PayersLifetimeColumn;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class MDauKpi extends KpiDto {
  public static final String ID = "mdau";
  public static final String KPI_REF = "{kpi." + ID + "}";

  public MDauKpi(String dateColumn) {
    super(
        "mDAU",
        null,
        DauKpi.KPI_REF,
        "%s > 0".formatted(PayersLifetimeColumn.KPI_REF),
        null,
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(dateColumn, new XaxisOverrideDto(TotalFunction.AVG));
          this.setAdditionalProperty(CohortDayColumn.ID, new XaxisOverrideDto());
        }},
        null);
  }
}
