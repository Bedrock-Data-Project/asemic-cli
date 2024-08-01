package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyLifetimeDto;

public class LastLoginDimensionColumn extends EntityPropertyDto {
  public LastLoginDimensionColumn(String dimension, ActionColumnDto.DataType dataType,
                                  String activityDatasourceName) {
    super(null, dataType, null, true, true,
        null, null,
        new EntityPropertyLifetimeDto(
            null, null,
            new EntityPropertyActionDto(
                activityDatasourceName,
                "{" + dimension + "}",
                EntityPropertyActionDto.AggregateFunction.LAST_VALUE,
                null,
                null
            ),
            EntityPropertyLifetimeDto.MergeFunction.LAST_VALUE),
        null, null);
  }
}
