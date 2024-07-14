package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyTotalDto;

public class LastLoginDateColumn extends EntityPropertyTotalDto {
  public LastLoginDateColumn() {
    super(new ColumnDto(
            "last_login_date",
            ColumnDto.DataType.DATE,
            true,
            true,
            null,
            null,
            null),
        EntityLogicalTable.DAU_DATE,
        Function.LAST_VALUE);
  }
}
