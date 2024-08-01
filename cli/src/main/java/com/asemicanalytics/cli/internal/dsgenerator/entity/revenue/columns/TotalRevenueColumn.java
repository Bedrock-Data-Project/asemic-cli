package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyLifetimeDto;

public class TotalRevenueColumn extends EntityPropertyDto {
  public static final String ID = "total_revenue";

  public TotalRevenueColumn() {
    super(null, ActionColumnDto.DataType.NUMBER, null, true, true,
        null, null,
        new EntityPropertyLifetimeDto(
            RevenueColumn.ID, null,
            null,
            EntityPropertyLifetimeDto.MergeFunction.SUM),
        null, null);
  }
}
