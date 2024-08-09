package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;

public class ActiveOnDayColumn extends EntityPropertyDto {
  public static final String ID = "active_on_day";
  public static final String KPI_REF = "{property." + ID + "}";

  public ActiveOnDayColumn(String activityDatasourceName) {
    super(null, ActionColumnDto.DataType.INTEGER, null, false, false,
        new EntityPropertyActionDto(
            activityDatasourceName, "1", EntityPropertyActionDto.AggregateFunction.NONE, null, "0"
        ),
        null, null, null, null, null);
  }
}
