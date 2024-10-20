package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ValueMappingsDto;
import java.util.List;

public class WasPayerLifetimeColumn extends EntityPropertyDto {
  public static final String ID = "was_payer_lifetime";
  public static final String KPI_REF = "{property." + ID + "}";

  public WasPayerLifetimeColumn() {
    super(null, DataType.STRING, null, true, true,
        null, null, null, null, null,
        new EntityPropertyComputedDto(
            "{" + PayersLifetimeColumn.ID + "}",
            List.of(
                new ValueMappingsDto("0", null, "Non Payer lifetime"),
                new ValueMappingsDto("1", null, "Payer lifetime")
            ), null
        ));
  }
}
