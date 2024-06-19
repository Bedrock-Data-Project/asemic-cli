package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyFirstAppearanceDto;

public class RegistrationPlatformColumn extends EntityPropertyFirstAppearanceDto {
  public RegistrationPlatformColumn(String platformColumn) {
    super(new ColumnDto(
            "registration_platform",
            ColumnDto.DataType.STRING,
            true,
            true,
            null,
            null,
            null),
        platformColumn);
  }
}
