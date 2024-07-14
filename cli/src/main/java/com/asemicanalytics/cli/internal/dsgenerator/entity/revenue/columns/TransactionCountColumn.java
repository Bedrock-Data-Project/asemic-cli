package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;

public class TransactionCountColumn extends EntityPropertyActionDto {
  public TransactionCountColumn(String revenueDatasourceName) {
    super(new ColumnDto(
            "transaction_count",
            ColumnDto.DataType.INTEGER,
            true,
            false,
            null,
            null,
            null),
        revenueDatasourceName,
        "COUNT(*)",
        null,
        "0",
        null);
  }
}
