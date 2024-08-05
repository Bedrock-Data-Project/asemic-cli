package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertySlidingWindowDto;

public class ActiveLast28Days extends EntityPropertyDto {
  public static final String ID = "active_last_28_days";

  public ActiveLast28Days(String activityDatasourceName) {
    super(null, ActionColumnDto.DataType.INTEGER, null, true, true,
        null,
        new EntityPropertySlidingWindowDto(
            ActiveTodayColumn.ID, EntityPropertySlidingWindowDto.EntityPropertyWindowFunction.MAX, -27, 0
        ),
        null, null, null, null);
  }
}
