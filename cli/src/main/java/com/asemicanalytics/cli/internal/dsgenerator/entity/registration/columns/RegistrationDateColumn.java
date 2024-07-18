package com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns;

import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyFirstAppearanceDto;

public class RegistrationDateColumn extends EntityPropertyFirstAppearanceDto {
  public RegistrationDateColumn(String dateColumn) {
    super(new ColumnDto(
            EntityLogicalTable.FIRST_APPEARANCE_DATE_COLUMN,
            ColumnDto.DataType.DATE,
            true,
            true,
            null,
            null,
            null),
        dateColumn);
  }
}
