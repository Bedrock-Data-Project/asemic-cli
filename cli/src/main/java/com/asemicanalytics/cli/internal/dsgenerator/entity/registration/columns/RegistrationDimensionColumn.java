package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyRegistrationDto;

public class RegistrationDimensionColumn extends EntityPropertyDto {
  public RegistrationDimensionColumn(String dimension,
                                     DataType dataType) {
    super(null, dataType, null, true, true,
        null, null, null,
        null,
        new EntityPropertyRegistrationDto(dimension),
        null);
  }
}
