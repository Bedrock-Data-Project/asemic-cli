package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnUserActionDto;

public class DauColumn extends UserWideColumnUserActionDto {
  public DauColumn(String activityDatasourceName) {
    super(new ColumnDto(
            "dau",
            ColumnDto.DataType.INTEGER,
            true,
            false,
            "DAU",
            null,
            null),
        activityDatasourceName,
        "1",
        null,
        "0",
        null,
        null,
        null,
        null);
  }
}
