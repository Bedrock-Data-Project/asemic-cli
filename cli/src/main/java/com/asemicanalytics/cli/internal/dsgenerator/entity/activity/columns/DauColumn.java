package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;

public class DauColumn extends EntityPropertyActionDto {
  public DauColumn(String activityDatasourceName) {
    super(new ColumnDto(
            "dau",
            ColumnDto.DataType.INTEGER,
            true,
            true,
            "DAU",
            null,
            null),
        activityDatasourceName, "1", null, "0", null);
  }
}
