package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyLifetimeDto;

public class RevenueLifetimeColumn extends EntityPropertyDto {
  public static final String ID = "revenue_lifetime";
  public static final String KPI_REF = "{property." + ID + "}";

  public RevenueLifetimeColumn() {
    super(null, DataType.NUMBER, null, true, false,
        null, null, null,
        new EntityPropertyLifetimeDto(
            RevenueOnDayColumn.ID, null,
            null,
            EntityPropertyLifetimeDto.MergeFunction.SUM),
        null, null);
  }
}
