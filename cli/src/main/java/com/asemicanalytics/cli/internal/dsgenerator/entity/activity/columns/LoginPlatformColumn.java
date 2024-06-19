package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;

public class LoginPlatformColumn extends EntityPropertyActionDto {
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
