package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnUserActionDto;
import java.util.List;

public class MauColumn extends UserWideColumnUserActionDto {
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
