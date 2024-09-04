package com.asemicanalytics.cli.internal.dsgenerator.entity;

import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.MDauKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.PayersOnDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.PayersLifetimeColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.PaymentSegmentColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.RevenueOnDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.RevenueLast28DaysColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.RevenueLifetimeColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.PaymentTransactionsOnDay;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.WasPayerLifetimeColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.WasPayerOnDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis.ArpdauKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis.ArppuKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis.DailyPayersKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis.LtvCohortKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis.LtvCohortedDailyKpis;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.kpis.RevenueKpi;
import com.asemicanalytics.core.logicaltable.action.PaymentTransactionActionLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityKpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.PropertiesDto;

public class PaymentTransaction {
  public static EntityPropertiesDto buildProperties(
      PaymentTransactionActionLogicalTable logicalTable) {

    var properties = new PropertiesDto();
    properties.setAdditionalProperty(RevenueOnDayColumn.ID,
        new RevenueOnDayColumn(logicalTable.getId(), logicalTable.getTransactionAmountColumn().getId()));
    properties.setAdditionalProperty(RevenueLast28DaysColumn.ID, new RevenueLast28DaysColumn());
    properties.setAdditionalProperty(RevenueLifetimeColumn.ID,
        new RevenueLifetimeColumn());

    properties.setAdditionalProperty(PaymentSegmentColumn.ID,
        new PaymentSegmentColumn());

    properties.setAdditionalProperty(PayersOnDayColumn.ID,
        new PayersOnDayColumn(logicalTable.getId()));
    properties.setAdditionalProperty(WasPayerOnDayColumn.ID,
        new WasPayerOnDayColumn());

    properties.setAdditionalProperty(PayersLifetimeColumn.ID,
        new PayersLifetimeColumn());
    properties.setAdditionalProperty(WasPayerLifetimeColumn.ID,
        new WasPayerLifetimeColumn());

    properties.setAdditionalProperty(PaymentTransactionsOnDay.ID,
        new PaymentTransactionsOnDay(logicalTable.getId()));
    properties.setAdditionalProperty(PayersLifetimeColumn.ID,
        new PayersLifetimeColumn());

    return new EntityPropertiesDto(properties);

  }

  public static EntityKpisDto buildKpis(PaymentTransactionActionLogicalTable logicalTable) {
    var kpis = new KpisDto();
    kpis.setAdditionalProperty(MDauKpi.ID, new MDauKpi(logicalTable.getDateColumn().getId()));
    kpis.setAdditionalProperty(
        ArpdauKpi.ID, new ArpdauKpi(logicalTable.getDateColumn().getId()));
    kpis.setAdditionalProperty(
        ArppuKpi.ID, new ArppuKpi(logicalTable.getDateColumn().getId()));
    kpis.setAdditionalProperty(
        DailyPayersKpi.ID, new DailyPayersKpi(logicalTable.getDateColumn().getId()));
    kpis.setAdditionalProperty(
        LtvCohortKpi.ID, new LtvCohortKpi());
    kpis.setAdditionalProperty(LtvCohortedDailyKpis.ID,
        new LtvCohortedDailyKpis(logicalTable.getDateColumn().getId()));
    kpis.setAdditionalProperty(
        RevenueKpi.ID, new RevenueKpi(logicalTable.getDateColumn().getId()));
    return new EntityKpisDto(kpis);
  }
}
