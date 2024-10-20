package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;

public class DaysSinceLastActiveColumn extends EntityPropertyDto {
  public static final String ID = EntityLogicalTable.DAYS_SINCE_LAST_ACTIVE;
  public static final String KPI_REF = "{property." + ID + "}";

  public DaysSinceLastActiveColumn(String dateColumn) {
    super(null, DataType.INTEGER, null, true, true,
        null, null, null, null, null,
        new EntityPropertyComputedDto(
            "DATE_DIFF({" + dateColumn + "}, {last_activity_date}, DAY)",
            null, null
        ));
  }
}
