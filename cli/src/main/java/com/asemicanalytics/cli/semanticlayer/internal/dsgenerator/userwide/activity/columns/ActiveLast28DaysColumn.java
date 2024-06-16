package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnUserActionDto;
import java.util.List;

public class ActiveLast28DaysColumn extends UserWideColumnUserActionDto {
  public ActiveLast28DaysColumn(String activityDatasourceName) {
    super(new ColumnDto(
            "active_last_28_days",
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
        List.of(-27, 0),
        "count",
        null);
  }
}
