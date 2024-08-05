package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;

public class DailyPayersColumn extends EntityPropertyDto {
  public static final String ID = "daily_payers";

  public DailyPayersColumn(String revenueDatasourceName) {
    super(null, ActionColumnDto.DataType.INTEGER, null, true, false,
        new EntityPropertyActionDto(
            revenueDatasourceName, "1", EntityPropertyActionDto.AggregateFunction.NONE, null, "0"
        ),
        null, null, null, null, null);
  }
}
