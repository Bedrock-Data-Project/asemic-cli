package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnTotalDto;

public class IsPayerColumn extends UserWideColumnTotalDto {
  public IsPayerColumn() {
    super(new ColumnDto(
            "is_payer",
            ColumnDto.DataType.BOOLEAN,
            true,
            true,
            null,
            null,
            null),
        "transaction_count",
        "COALESCE({__total}, FALSE) OR ({__current} > 0)"
    );
  }
}
