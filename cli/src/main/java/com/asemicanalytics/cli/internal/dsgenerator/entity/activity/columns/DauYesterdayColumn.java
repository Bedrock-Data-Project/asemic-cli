package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertySlidingWindowDto;

public class DauYesterdayColumn extends EntityPropertySlidingWindowDto {
  public DauYesterdayColumn() {
    super(new ColumnDto(
            "dau_yesterday",
            ColumnDto.DataType.INTEGER,
            true,
            false,
            "DAU Yesterday",
            null,
            null),
        "dau",
        EntityPropertySlidingWindowDto.Function.SUM,
        -1,
        -1);
  }
}
