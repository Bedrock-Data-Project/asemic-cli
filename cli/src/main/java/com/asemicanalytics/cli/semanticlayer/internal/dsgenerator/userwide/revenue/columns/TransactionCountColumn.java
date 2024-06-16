package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnUserActionDto;

public class TransactionCountColumn extends UserWideColumnUserActionDto {
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
        null,
        null,
        null,
        null);
  }
}
