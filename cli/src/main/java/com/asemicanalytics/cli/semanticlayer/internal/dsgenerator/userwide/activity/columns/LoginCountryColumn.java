package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnUserActionDto;

public class LoginCountryColumn extends UserWideColumnUserActionDto {
  public LoginCountryColumn(String activityDatasourceName, String countryColumn) {
    super(new ColumnDto(
            "login_country",
            ColumnDto.DataType.STRING,
            true,
            true,
            null,
            null,
            null),
        activityDatasourceName,
        "MAX({%s})".formatted(countryColumn),
        null,
        null,
        null,
        null,
        null,
        null);
  }
}
