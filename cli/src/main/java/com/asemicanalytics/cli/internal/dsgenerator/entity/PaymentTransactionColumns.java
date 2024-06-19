package com.asemicanalytics.cli.internal.dsgenerator.entity;

import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.DailyPayersColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.IsPayerColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.RevenueColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.RevenueLast28DaysColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.TotalRevenueColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.TransactionCountColumn;
import com.asemicanalytics.core.logicaltable.action.PaymentTransactionActionLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyTotalDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import java.util.ArrayList;
import java.util.List;

public class PaymentTransactionColumns {
  public static EntityPropertiesDto build(PaymentTransactionActionLogicalTable logicalTable) {

    List<EntityPropertyActionDto> userActionColumns = new ArrayList<>();
    List<EntityPropertyTotalDto> totalColumns = new ArrayList<>();

    userActionColumns.add(
        new RevenueColumn(logicalTable.getId(), logicalTable.getTransactionAmountColumn().getId()));
    userActionColumns.add(new RevenueLast28DaysColumn(logicalTable.getId(),
        logicalTable.getTransactionAmountColumn().getId()));
    userActionColumns.add(new DailyPayersColumn(logicalTable.getId()));
    userActionColumns.add(new TransactionCountColumn(logicalTable.getId()));

    totalColumns.add(new TotalRevenueColumn());
    totalColumns.add(new IsPayerColumn());

    return new EntityPropertiesDto(
        List.of(),
        userActionColumns,
        totalColumns,
        List.of()
    );
  }
}
