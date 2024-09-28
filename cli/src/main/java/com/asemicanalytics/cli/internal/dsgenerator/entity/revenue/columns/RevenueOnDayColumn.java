package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyEventDto;

public class RevenueOnDayColumn extends EntityPropertyDto {
  public static final String ID = "revenue_on_day";
  public static final String KPI_REF = "{property." + ID + "}";

  public RevenueOnDayColumn(String revenueDatasourceName, String revenueColumn) {
    super(null, DataType.NUMBER, null, true, false,
        new EntityPropertyEventDto(
            revenueDatasourceName, "{" + revenueColumn + "}",
            EntityPropertyEventDto.AggregateFunction.SUM, null, "0"
        ),
        null, null, null, null, null);
  }
}
