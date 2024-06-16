package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnUserActionDto;

public class DailyPayersColumn extends UserWideColumnUserActionDto {
  public DailyPayersColumn(String revenueDatasourceName) {
    super(new ColumnDto(
            "daily_payers",
            ColumnDto.DataType.NUMBER,
            true,
            false,
            null,
            null,
            null),
        revenueDatasourceName,
        "1",
        null,
        "0",
        null,
        null,
        null,
        null);
  }
}
