package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import java.util.List;

public class RevenueLast28DaysColumn extends EntityPropertyActionDto {
  public RevenueLast28DaysColumn(String revenueDatasourceName, String revenueColumn) {
    super(new ColumnDto(
            "revenue_28_days",
            ColumnDto.DataType.NUMBER,
            true,
            false,
            null,
            null,
            null),
        revenueDatasourceName,
        "SUM({%s})".formatted(revenueColumn),
        null,
        "0",
        null,
        List.of(-27, 0),
        "sum",
        null);
  }
}
