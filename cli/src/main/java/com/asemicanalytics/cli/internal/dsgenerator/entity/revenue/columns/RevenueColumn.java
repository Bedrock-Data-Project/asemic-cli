package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;

public class RevenueColumn extends EntityPropertyDto {
  public static final String ID = "revenue";

  public RevenueColumn(String revenueDatasourceName, String revenueColumn) {
    super(null, ActionColumnDto.DataType.NUMBER, null, true, true,
        new EntityPropertyActionDto(
            revenueDatasourceName, "{" + revenueColumn + "}",
            EntityPropertyActionDto.AggregateFunction.SUM, null, "0"
        ),
        null, null, null, null);
  }
}
