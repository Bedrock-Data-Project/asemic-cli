package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;

public class PaymentTransactionsOnDay extends EntityPropertyDto {
  public static final String ID = "payment_transactions_count";
  public static final String KPI_REF = "{property." + ID + "}";

  public PaymentTransactionsOnDay(String revenueDatasourceName) {
    super(null, ActionColumnDto.DataType.NUMBER, null, true, false,
        new EntityPropertyActionDto(
            revenueDatasourceName, "COUNT(*)", EntityPropertyActionDto.AggregateFunction.NONE, null,
            "0"
        ),
        null, null, null, null, null);
  }
}
