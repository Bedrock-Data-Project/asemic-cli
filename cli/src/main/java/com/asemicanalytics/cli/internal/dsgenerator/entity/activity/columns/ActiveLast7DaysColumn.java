package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertySlidingWindowDto;

public class ActiveLast7DaysColumn extends EntityPropertySlidingWindowDto {
  public ActiveLast7DaysColumn() {
    super(new ColumnDto(
            "active_last_7_days",
            ColumnDto.DataType.INTEGER,
            true,
            true,
            null,
            null,
            null),
        "dau",
        Function.SUM,
        -6,
        0);
  }
}
