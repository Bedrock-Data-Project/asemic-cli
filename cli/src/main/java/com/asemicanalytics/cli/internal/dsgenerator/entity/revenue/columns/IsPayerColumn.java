package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyLifetimeDto;

public class IsPayerColumn extends EntityPropertyDto {
    public static final String ID = "is_payer";

  public IsPayerColumn() {
    super(null, ActionColumnDto.DataType.BOOLEAN, null, true, true,
        null, null,
        new EntityPropertyLifetimeDto(
            IsDailyPayerColumn.ID, null,
            null,
            EntityPropertyLifetimeDto.MergeFunction.MAX),
        null, null);
  }
}
