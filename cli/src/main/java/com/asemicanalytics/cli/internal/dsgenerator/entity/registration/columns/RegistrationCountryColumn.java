package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyFirstAppearanceDto;

public class RegistrationCountryColumn extends EntityPropertyFirstAppearanceDto {
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
