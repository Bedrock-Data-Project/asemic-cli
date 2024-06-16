package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnUserActionDto;
import java.util.List;

public class DauYesterdayColumn extends UserWideColumnUserActionDto {
  public DauYesterdayColumn(String activityDatasourceName) {
    super(new ColumnDto(
            "dau_yesterday",
            ColumnDto.DataType.INTEGER,
            true,
            false,
            "DAU Yesterday",
            null,
            null),
        activityDatasourceName,
        "1",
        null,
        "0",
        null,
        List.of(-1, -1),
        null,
        null);
  }
}
