package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertySlidingWindowDto;

public class ActiveLast28DaysColumn extends EntityPropertyDto {
  public static final String ID = "active_last_28_days";

  public ActiveLast28DaysColumn() {
    super(null, ActionColumnDto.DataType.INTEGER, null, true, true,
        null,
        new EntityPropertySlidingWindowDto(
            ActiveTodayColumn.ID, EntityPropertySlidingWindowDto.EntityPropertyWindowFunction.SUM, -27, 0
        ),
        null, null, null, null);
  }
}
