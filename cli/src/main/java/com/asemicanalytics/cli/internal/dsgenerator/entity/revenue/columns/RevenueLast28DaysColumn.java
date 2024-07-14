package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertySlidingWindowDto;

public class RevenueLast28DaysColumn extends EntityPropertySlidingWindowDto {
  public RevenueLast28DaysColumn() {
    super(new ColumnDto(
            "revenue_28_days",
            ColumnDto.DataType.NUMBER,
            true,
            false,
            null,
            null,
            null),
        "revenue",
        Function.SUM,
        -27,
        0);
  }
}
