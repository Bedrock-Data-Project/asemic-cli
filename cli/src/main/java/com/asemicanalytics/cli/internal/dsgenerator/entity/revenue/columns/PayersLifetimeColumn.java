package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyLifetimeDto;

public class PayersLifetimeColumn extends EntityPropertyDto {
  public static final String ID = "payers_lifetime";
  public static final String KPI_REF = "{property." + ID + "}";

  public PayersLifetimeColumn() {
    super(null, ActionColumnDto.DataType.INTEGER, null, true, true,
        null, null, null,
        new EntityPropertyLifetimeDto(
            PayersOnDayColumn.ID, null,
            null,
            EntityPropertyLifetimeDto.MergeFunction.MAX),
        null, null);
  }
}
