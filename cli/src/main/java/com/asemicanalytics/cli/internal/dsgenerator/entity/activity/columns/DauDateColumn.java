package com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns;

import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;

public class DauDateColumn extends EntityPropertyActionDto {
  public DauDateColumn(String activityDatasourceName, String dateColumn) {
    super(new ColumnDto(
            EntityLogicalTable.DAU_DATE,
            ColumnDto.DataType.DATE,
            false,
            false,
            "DAU Date",
            null,
            null),
        activityDatasourceName,
        "MAX({" + dateColumn + "})",
        null,
        null,
        false);
  }
}
