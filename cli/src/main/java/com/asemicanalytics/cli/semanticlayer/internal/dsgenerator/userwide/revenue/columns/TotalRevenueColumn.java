package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnTotalDto;

public class TotalRevenueColumn extends UserWideColumnTotalDto {
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
        "{__current} + COALESCE({__total}, 0)"
    );
  }
}
