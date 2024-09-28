package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns;

import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;

public class CohortDayColumn extends EntityPropertyDto {
  public static final String ID = EntityLogicalTable.COHORT_DAY_COLUMN;
  public static final String KPI_REF = "{property." + ID + "}";

  public CohortDayColumn(String dateColumn) {
    super(null, DataType.INTEGER, null, true, true,
        null, null, null, null, null,
        new EntityPropertyComputedDto(
            "DATE_DIFF({"
                + dateColumn + "}, {"
                + EntityLogicalTable.REGISTRATION_DATE_COLUMN + "}, DAY)",
            null
        ));
  }
}
