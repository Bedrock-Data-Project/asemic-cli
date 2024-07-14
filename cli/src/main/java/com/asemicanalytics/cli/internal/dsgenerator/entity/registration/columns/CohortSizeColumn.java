package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns;

import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;

public class CohortSizeColumn extends ColumnComputedDto {
  public CohortSizeColumn(String dateColumn) {
    super(new ColumnDto(
            EntityLogicalTable.COHORT_SIZE_COLUMN,
            ColumnDto.DataType.INTEGER,
            true,
            true,
            null,
            null,
            null),
        "1");
  }
}
