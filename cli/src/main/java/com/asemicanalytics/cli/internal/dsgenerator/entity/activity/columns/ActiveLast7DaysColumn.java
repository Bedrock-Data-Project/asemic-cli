package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import java.util.List;

public class ActiveLast7DaysColumn extends EntityPropertyActionDto {
  public ActiveLast7DaysColumn(String activityDatasourceName) {
    super(new ColumnDto(
            "active_last_7_days",
            ColumnDto.DataType.INTEGER,
            true,
            true,
            null,
            null,
            null),
        activityDatasourceName,
        "1",
        null,
        "0",
        null,
        List.of(-6, 0),
        "count",
        null);
  }
}
