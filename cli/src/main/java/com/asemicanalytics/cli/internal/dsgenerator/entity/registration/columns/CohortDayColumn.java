package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns;

import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;

public class CohortDayColumn extends ColumnComputedDto {
  public CohortDayColumn(String dateColumn) {
    super(new ColumnDto(
            EntityLogicalTable.COHORT_DAY_COLUMN,
            ColumnDto.DataType.INTEGER,
            true,
            true,
            null,
            null,
            null),
        "{EPOCH_DAYS(" + dateColumn + ")} - {EPOCH_DAYS("
            + EntityLogicalTable.FIRST_APPEARANCE_DATE_COLUMN + ")}");
  }
}
