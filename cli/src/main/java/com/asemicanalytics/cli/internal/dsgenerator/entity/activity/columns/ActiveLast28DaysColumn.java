package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertySlidingWindowDto;

public class ActiveLast28DaysColumn extends EntityPropertySlidingWindowDto {
  public ActiveLast28DaysColumn() {
    super(new ColumnDto(
            "active_last_28_days",
            ColumnDto.DataType.INTEGER,
            true,
            true,
            null,
            null,
            null),
        "dau",
        EntityPropertySlidingWindowDto.Function.SUM,
        -27,
        0);
  }
}
