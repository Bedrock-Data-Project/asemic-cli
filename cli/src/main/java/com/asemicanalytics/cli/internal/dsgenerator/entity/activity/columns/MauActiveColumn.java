package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertySlidingWindowDto;

public class MauActiveColumn extends EntityPropertyDto {
  public static final String ID = "mau_active";
  public static final String KPI_REF = "{property." + ID + "}";

  public MauActiveColumn() {
    super(null, DataType.INTEGER, null, false, false,
        null,
        new EntityPropertySlidingWindowDto(
            DauActiveColumn.ID, null, null,
            EntityPropertySlidingWindowDto.EntityPropertyWindowFunction.SUM, -27, 0
        ),
        null, null, null, null);
  }
}
