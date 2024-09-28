package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyEventDto;

public class PaymentTransactionsOnDay extends EntityPropertyDto {
  public static final String ID = "payment_transactions_count";
  public static final String KPI_REF = "{property." + ID + "}";

  public PaymentTransactionsOnDay(String revenueDatasourceName) {
    super(null, DataType.NUMBER, null, true, false,
        new EntityPropertyEventDto(
            revenueDatasourceName, "COUNT(*)", EntityPropertyEventDto.AggregateFunction.NONE, null,
            "0"
        ),
        null, null, null, null, null);
  }
}
