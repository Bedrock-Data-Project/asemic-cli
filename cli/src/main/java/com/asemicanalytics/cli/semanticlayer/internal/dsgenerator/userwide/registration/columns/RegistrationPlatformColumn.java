package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.registration.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnRegistrationDto;

public class RegistrationPlatformColumn extends UserWideColumnRegistrationDto {
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
