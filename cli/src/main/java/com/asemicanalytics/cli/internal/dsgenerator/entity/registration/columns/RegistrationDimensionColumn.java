package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyFirstAppearanceDto;

public class RegistrationDimensionColumn extends EntityPropertyDto {
  public RegistrationDimensionColumn(String dimension,
                                     ActionColumnDto.DataType dataType) {
    super(null, dataType, null, true, true,
        null, null, null,
        null,
        new EntityPropertyFirstAppearanceDto(dimension),
        null);
  }
}
