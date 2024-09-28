package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyEventDto;

public class PayersOnDayColumn extends EntityPropertyDto {
  public static final String ID = "payers_on_day";
  public static final String KPI_REF = "{property." + ID + "}";

  public PayersOnDayColumn(String revenueDatasourceName) {
    super(null, DataType.INTEGER, null, false, false,
        new EntityPropertyEventDto(
            revenueDatasourceName, "1", EntityPropertyEventDto.AggregateFunction.NONE, null, "0"
        ),
        null, null, null, null, null);
  }
}
