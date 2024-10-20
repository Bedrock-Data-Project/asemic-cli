package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyEventDto;

public class DauActiveColumn extends EntityPropertyDto {
  public static final String ID = "dau_active";
  public static final String KPI_REF = "{property." + ID + "}";

  public DauActiveColumn(String activityDatasourceName) {
    super(null, DataType.INTEGER, null, false, false,
        new EntityPropertyEventDto(
            activityDatasourceName, "1", EntityPropertyEventDto.AggregateFunction.NONE, null, "0", null
        ),
        null, null, null, null, null);
  }
}
