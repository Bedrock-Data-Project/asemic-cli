package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnTotalDto;

public class LastLoginPlatformColumn extends UserWideColumnTotalDto {
  public LastLoginPlatformColumn() {
    super(new ColumnDto(
            "last_login_platform",
            ColumnDto.DataType.STRING,
            true,
            true,
            null,
            null,
            null),
        "login_platform",
        "COALESCE({__current}, {__total})");
  }
}
