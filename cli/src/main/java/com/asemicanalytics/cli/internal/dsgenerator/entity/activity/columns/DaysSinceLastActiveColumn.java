package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ValueMappingsDto;
import java.util.List;

public class DaysSinceLastActiveColumn extends EntityPropertyDto {
  public static final String ID = EntityLogicalTable.DAYS_SINCE_LAST_ACTIVE;
  public static final String KPI_REF = "{property." + ID + "}";

  public DaysSinceLastActiveColumn(String dateColumn) {
    super(null, ActionColumnDto.DataType.INTEGER, null, true, true,
        null, null, null, null, null,
        new EntityPropertyComputedDto(
            "DATE_DIFF({" + dateColumn + "}, {last_login_date}, DAY)",
            null
        ));
  }
}
