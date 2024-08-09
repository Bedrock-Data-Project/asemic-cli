package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.RangeDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ValueMappingsDto;
import java.util.List;

public class WasPayerOnDayColumn extends EntityPropertyDto {
  public static final String ID = "was_payer_on_day";
  public static final String KPI_REF = "{property." + ID + "}";

  public WasPayerOnDayColumn() {
    super(null, ActionColumnDto.DataType.STRING, null, true, true,
        null, null, null, null, null,
        new EntityPropertyComputedDto(
            "{" + PayersOnDayColumn.ID + "}",
            List.of(
                new ValueMappingsDto("0", null, "Non Payer on day"),
                new ValueMappingsDto("1", null, "Payer on day")
            )
        ));
  }
}
