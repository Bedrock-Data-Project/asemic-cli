package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyTotalDto;

public class TotalRevenueColumn extends EntityPropertyTotalDto {
  public TotalRevenueColumn() {
    super(new ColumnDto(
            "total_revenue",
            ColumnDto.DataType.NUMBER,
            true,
            false,
            null,
            null,
            null),
        "revenue",
        Function.SUM
    );
  }
}
