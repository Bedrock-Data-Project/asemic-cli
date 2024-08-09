package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertySlidingWindowDto;

public class RevenueLast28DaysColumn extends EntityPropertyDto {
  public static final String ID = "revenue_last_28_days";
  public static final String KPI_REF = "{property." + ID + "}";

  public RevenueLast28DaysColumn() {
    super(null, ActionColumnDto.DataType.NUMBER, null, true, false,
        null,
        new EntityPropertySlidingWindowDto(
            RevenueOnDayColumn.ID, EntityPropertySlidingWindowDto.EntityPropertyWindowFunction.SUM, -27, 0
        ),
        null, null, null, null);
  }
}
