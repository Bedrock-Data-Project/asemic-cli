package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnTotalDto;

public class LastLoginCountryColumn extends UserWideColumnTotalDto {
  public LastLoginCountryColumn() {
    super(new ColumnDto(
            "last_login_country",
            ColumnDto.DataType.STRING,
            true,
            true,
            null,
            null,
            null),
        "login_country",
        "COALESCE({__current}, {__total})");
  }
}
