package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyEventDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyLifetimeDto;

public class LastLoginDimensionColumn extends EntityPropertyDto {
  public LastLoginDimensionColumn(String dimension, DataType dataType,
                                  String activityDatasourceName) {
    super(null, dataType, null, true, true,
        null, null, null,
        new EntityPropertyLifetimeDto(
            null, null,
            new EntityPropertyEventDto(
                activityDatasourceName,
                "{" + dimension + "}",
                EntityPropertyEventDto.AggregateFunction.LAST_VALUE,
                null,
                null,
                null
            ),
            EntityPropertyLifetimeDto.MergeFunction.LAST_VALUE),
        null, null);
  }
}
