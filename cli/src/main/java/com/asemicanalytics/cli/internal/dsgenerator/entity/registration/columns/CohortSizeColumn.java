package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns;

import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;

public class CohortSizeColumn extends EntityPropertyDto {
  public static final String ID = EntityLogicalTable.COHORT_SIZE_COLUMN;

  public CohortSizeColumn(String dateColumn) {
    super(null, ActionColumnDto.DataType.INTEGER, null, false, false,
        null, null, null, null, null,
        new EntityPropertyComputedDto(
            "1"
        ));
  }
}
