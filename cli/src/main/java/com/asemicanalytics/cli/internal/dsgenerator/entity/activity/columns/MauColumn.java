package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import java.util.List;

public class MauColumn extends EntityPropertyActionDto {
  public MauColumn(String activityDatasourceName) {
    super(new ColumnDto(
            "mau",
            ColumnDto.DataType.INTEGER,
            true,
            false,
            "MAU",
            null,
            null),
        activityDatasourceName,
        "1",
        null,
        "0",
        null,
        List.of(-29, 0),
        "max",
        null);
  }
}
