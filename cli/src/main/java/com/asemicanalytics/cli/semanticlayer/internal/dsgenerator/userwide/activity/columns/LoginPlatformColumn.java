package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnUserActionDto;

public class LoginPlatformColumn extends UserWideColumnUserActionDto {
  public LoginPlatformColumn(String activityDatasourceName, String platformColumn) {
    super(new ColumnDto(
            "login_platform",
            ColumnDto.DataType.STRING,
            true,
            true,
            null,
            null,
            null),
        activityDatasourceName,
        "MAX({%s})".formatted(platformColumn),
        null,
        null,
        null,
        null,
        null,
        null);
  }
}
