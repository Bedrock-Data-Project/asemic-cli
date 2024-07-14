package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;

public class IsDailyPayerColumn extends ColumnComputedDto {
  public IsDailyPayerColumn() {
    super(new ColumnDto(
            "is_daily_payer",
            ColumnDto.DataType.BOOLEAN,
            true,
            true,
            null,
            null,
            null),
        "{daily_payers} > 0"
    );
  }
}
