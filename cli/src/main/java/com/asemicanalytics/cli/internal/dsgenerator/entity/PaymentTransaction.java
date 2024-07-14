package com.asemicanalytics.cli.internal.dsgenerator.entity;

import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.DailyPayersColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.IsPayerColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.RevenueColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.RevenueLast28DaysColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.TotalRevenueColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.TransactionCountColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis.ArpdauKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis.ArppuKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis.DailyPayersKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis.LtvCohortKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis.LtvCohortedDailyKpis;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis.RevenueKpi;
import com.asemicanalytics.core.logicaltable.action.PaymentTransactionActionLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityKpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertySlidingWindowDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyTotalDto;
import java.util.ArrayList;
import java.util.List;

public class PaymentTransaction {
  public static EntityPropertiesDto buildProperties(
      PaymentTransactionActionLogicalTable logicalTable) {

    List<EntityPropertyActionDto> userActionColumns = new ArrayList<>();
    List<EntityPropertySlidingWindowDto> slidingWindowColumns = new ArrayList<>();
    List<EntityPropertyTotalDto> totalColumns = new ArrayList<>();

    userActionColumns.add(
        new RevenueColumn(logicalTable.getId(), logicalTable.getTransactionAmountColumn().getId()));
    slidingWindowColumns.add(new RevenueLast28DaysColumn());
    userActionColumns.add(new DailyPayersColumn(logicalTable.getId()));
    userActionColumns.add(new TransactionCountColumn(logicalTable.getId()));

    totalColumns.add(new TotalRevenueColumn());
    totalColumns.add(new IsPayerColumn());

    return new EntityPropertiesDto(
        List.of(),
        userActionColumns,
        slidingWindowColumns,
        totalColumns,
        List.of()
    );
  }

  public static EntityKpisDto buildKpis(PaymentTransactionActionLogicalTable logicalTable) {
    return new EntityKpisDto("Engagement",
        List.of(
            new ArpdauKpi(logicalTable.getDateColumn().getId()),
            new ArppuKpi(logicalTable.getDateColumn().getId()),
            new DailyPayersKpi(logicalTable.getDateColumn().getId()),
            new LtvCohortKpi(),
            new RevenueKpi(logicalTable.getDateColumn().getId())
        ),
        List.of(1, 2, 3, 4, 5, 6, 7, 14, 28, 30, 60, 90, 180, 365),
        List.of(
            new LtvCohortedDailyKpis(logicalTable.getDateColumn().getId())
        )
    );
  }
}
