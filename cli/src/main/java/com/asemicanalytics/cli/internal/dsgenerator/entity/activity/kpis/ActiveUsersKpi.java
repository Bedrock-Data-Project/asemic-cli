package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis;

import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.DaysSinceLastActiveColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortSizeColumn;
import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpiDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XAxisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.XaxisOverrideDto;

public class ActiveUsersKpi extends KpiDto {
  public static final String ID = "active_users";
  public static final String KPI_REF = "{kpi." + ID + "}";
  public ActiveUsersKpi(String dateColumn, int activeDays) {
    super(
        null,
        null,
        "SUM(%s)".formatted(CohortSizeColumn.KPI_REF),
        "%s <= %d".formatted(DaysSinceLastActiveColumn.KPI_REF, activeDays),
        null,
        null,
        new XAxisDto() {{
          this.setAdditionalProperty(dateColumn, new XaxisOverrideDto());
          this.setAdditionalProperty(CohortDayColumn.ID, new XaxisOverrideDto());
        }},
        null);
  }
}
