package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;

public class TransactionCountColumn extends EntityPropertyDto {
  public static final String ID = "transaction_count";

  public TransactionCountColumn(String revenueDatasourceName) {
    super(null, ActionColumnDto.DataType.NUMBER, null, true, true,
        new EntityPropertyActionDto(
            revenueDatasourceName, "COUNT(*)", EntityPropertyActionDto.AggregateFunction.NONE, null,
            "0"
        ),
        null, null, null, null, null);
  }
}
