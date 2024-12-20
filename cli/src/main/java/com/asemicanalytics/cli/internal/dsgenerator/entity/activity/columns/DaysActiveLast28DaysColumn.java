package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertySlidingWindowDto;

public class DaysActiveLast28DaysColumn extends EntityPropertyDto {
  public static final String ID = "days_active_last_28_days";
  public static final String KPI_REF = "{property." + ID + "}";

  public DaysActiveLast28DaysColumn() {
    super(null, DataType.INTEGER, null, true, true,
        null,
        new EntityPropertySlidingWindowDto(
            DauActiveColumn.ID, null, null,
            EntityPropertySlidingWindowDto.EntityPropertyWindowFunction.SUM, -27, 0
        ),
        null, null, null, null);
  }
}
