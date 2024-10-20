package com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns;

import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.RangeDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ValueMappingsDto;
import java.util.List;

public class PaymentSegmentColumn extends EntityPropertyDto {
  public static final String ID = "payment_segment";
  public static final String KPI_REF = "{property." + ID + "}";

  public PaymentSegmentColumn() {
    super(null, DataType.STRING, null, true, true,
        null, null, null, null, null,
        new EntityPropertyComputedDto(
            "{" + RevenueLifetimeColumn.ID + "}",
            List.of(
                new ValueMappingsDto(null, new RangeDto(null, "0"), "Non Payer"),
                new ValueMappingsDto(null, new RangeDto("0", "20"), "Minnow"),
                new ValueMappingsDto(null, new RangeDto("20", "100"), "Dolphin"),
                new ValueMappingsDto(null, new RangeDto("100", null), "Whale")
            ), null
        ));
  }
}
