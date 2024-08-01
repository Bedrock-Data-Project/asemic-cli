package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;

public class IsDailyPayerColumn extends EntityPropertyDto {
  public static final String ID = "is_daily_payer";

  public IsDailyPayerColumn() {
    super(null, ActionColumnDto.DataType.INTEGER, null, true, true,
        null, null, null, null,
        new EntityPropertyComputedDto(
            "{daily_payers} = 1"
        ));
  }
}
