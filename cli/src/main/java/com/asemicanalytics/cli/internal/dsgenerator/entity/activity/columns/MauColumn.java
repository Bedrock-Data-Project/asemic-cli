package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertySlidingWindowDto;

public class MauColumn extends EntityPropertySlidingWindowDto {
  public MauColumn(String activityDatasourceName) {
    super(new ColumnDto(
            "mau",
            ColumnDto.DataType.INTEGER,
            true,
            true,
            "MAU",
            null,
            null),
        "dau",
        Function.MAX,
        -29,
        0);
  }
}
