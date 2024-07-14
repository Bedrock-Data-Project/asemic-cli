package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyTotalDto;

public class IsPayerColumn extends EntityPropertyTotalDto {
  public IsPayerColumn() {
    super(new ColumnDto(
            "is_payer",
            ColumnDto.DataType.BOOLEAN,
            true,
            true,
            null,
            null,
            null),
        "is_daily_payer",
        Function.MAX
    );
  }
}
