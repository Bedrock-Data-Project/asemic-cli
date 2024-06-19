package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyTotalDto;

public class LastLoginPlatformColumn extends EntityPropertyTotalDto {
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
