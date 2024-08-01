package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;

public class ActiveTodayColumn extends EntityPropertyDto {
  public static final String ID = "active_today";

  public ActiveTodayColumn(String activityDatasourceName) {
    super(null, ActionColumnDto.DataType.INTEGER, null, true, true,
        new EntityPropertyActionDto(
            activityDatasourceName, "1", EntityPropertyActionDto.AggregateFunction.NONE, null, "0"
        ),
        null, null, null, null);
  }
}
