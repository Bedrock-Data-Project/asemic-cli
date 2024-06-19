package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;

public class LoginBuildVersionColumn extends EntityPropertyActionDto {
  public LoginBuildVersionColumn(String activityDatasourceName, String buildVersionColumn) {
    super(new ColumnDto(
            "login_build_version",
            ColumnDto.DataType.STRING,
            true,
            true,
            null,
            null,
            null),
        activityDatasourceName,
        "MAX({%s})".formatted(buildVersionColumn),
        null,
        null,
        null,
        null,
        null,
        null);
  }
}
