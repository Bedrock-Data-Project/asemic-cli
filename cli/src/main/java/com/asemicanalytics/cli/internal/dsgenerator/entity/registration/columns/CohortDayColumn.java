package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns;

import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;

public class CohortDayColumn extends EntityPropertyDto {
  public static final String ID = EntityLogicalTable.COHORT_DAY_COLUMN;

  public CohortDayColumn(String dateColumn) {
    super(null, ActionColumnDto.DataType.INTEGER, null, true, true,
        null, null, null, null,
        new EntityPropertyComputedDto(
            "DATE_DIFF({"
                + dateColumn + "}, {"
                + EntityLogicalTable.FIRST_APPEARANCE_DATE_COLUMN + "}, DAY)"
        ));
  }
}
