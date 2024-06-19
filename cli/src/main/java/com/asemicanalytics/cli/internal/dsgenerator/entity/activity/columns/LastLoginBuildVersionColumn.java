package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyTotalDto;

public class LastLoginBuildVersionColumn extends EntityPropertyTotalDto {
  public LastLoginBuildVersionColumn() {
    super(new ColumnDto(
            "last_login_build_version",
            ColumnDto.DataType.STRING,
            true,
            true,
            null,
            null,
            null),
        "login_build_version",
        "COALESCE({__current}, {__total})");
  }
}
