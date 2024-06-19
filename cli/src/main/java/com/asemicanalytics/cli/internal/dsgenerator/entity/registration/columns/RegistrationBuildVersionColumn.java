package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyFirstAppearanceDto;

public class RegistrationBuildVersionColumn extends EntityPropertyFirstAppearanceDto {
  public RegistrationBuildVersionColumn(String buildVersionColumn) {
    super(new ColumnDto(
            "registration_build_version",
            ColumnDto.DataType.STRING,
            true,
            true,
            null,
            null,
            null),
        buildVersionColumn);
  }
}
