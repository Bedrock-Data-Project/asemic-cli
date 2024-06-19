package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;

public class RevenueColumn extends EntityPropertyActionDto {
  public RevenueColumn(String revenueDatasourceName, String revenueColumn) {
    super(new ColumnDto(
            "revenue",
            ColumnDto.DataType.NUMBER,
            true,
            false,
            null,
            null,
            null),
        revenueDatasourceName,
        "SUM({%s})".formatted(revenueColumn),
        null,
        "0",
        null,
        null,
        null,
        null);
  }
}
