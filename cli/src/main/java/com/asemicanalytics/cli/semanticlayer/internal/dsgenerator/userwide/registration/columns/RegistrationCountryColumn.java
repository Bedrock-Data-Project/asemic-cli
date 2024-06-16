package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.registration.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnRegistrationDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnUserActionDto;

public class RegistrationCountryColumn extends UserWideColumnRegistrationDto {
  public RegistrationCountryColumn(String countryColumn) {
    super(new ColumnDto(
            "registration_country",
            ColumnDto.DataType.STRING,
            true,
            true,
            null,
            null,
            null),
        countryColumn);
  }
}
