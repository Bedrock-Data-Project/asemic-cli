package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns;

import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;

public class CohortSizeColumn extends EntityPropertyDto {
  public static final String ID = EntityLogicalTable.COHORT_SIZE_COLUMN;
  public static final String KPI_REF = "{property." + ID + "}";

  public CohortSizeColumn() {
    super(null, DataType.INTEGER, null, false, false,
        null, null, null, null, null,
        new EntityPropertyComputedDto(
            "1",
            null, null
        ));
  }
}
