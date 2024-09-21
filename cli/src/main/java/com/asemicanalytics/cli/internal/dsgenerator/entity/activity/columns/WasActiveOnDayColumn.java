package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ValueMappingsDto;
import java.util.List;

public class WasActiveOnDayColumn extends EntityPropertyDto {
  public static final String ID = "was_active_on_day";
  public static final String KPI_REF = "{property." + ID + "}";

  public WasActiveOnDayColumn() {
    super(null, ActionColumnDto.DataType.STRING, null, true, true,
        null, null, null, null, null,
        new EntityPropertyComputedDto(
            "{" + DauActiveColumn.ID + "}",
            List.of(
                new ValueMappingsDto("0", null, "Inactive on day"),
                new ValueMappingsDto("1", null, "Active on day")
            )
        ));
  }
}
