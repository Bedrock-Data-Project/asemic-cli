package com.asemicanalytics.cli.internal.dsgenerator.entity;

import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.ActiveTodayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.DailyPayersColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.revenue.columns.IsDailyPayerColumn;
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
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.PropertiesDto;

public class PaymentTransaction {
  public static EntityPropertiesDto buildProperties(
      PaymentTransactionActionLogicalTable logicalTable) {

    var properties = new PropertiesDto();
    properties.setAdditionalProperty(RevenueColumn.ID,
        new RevenueColumn(logicalTable.getId(), logicalTable.getTransactionAmountColumn().getId()));
    properties.setAdditionalProperty(RevenueLast28DaysColumn.ID, new RevenueLast28DaysColumn());
    properties.setAdditionalProperty(DailyPayersColumn.ID,
        new DailyPayersColumn(logicalTable.getId()));
    properties.setAdditionalProperty(TransactionCountColumn.ID,
        new TransactionCountColumn(logicalTable.getId()));
    properties.setAdditionalProperty(TotalRevenueColumn.ID,
        new TotalRevenueColumn());
    properties.setAdditionalProperty(IsPayerColumn.ID,
        new IsPayerColumn());
    properties.setAdditionalProperty(IsDailyPayerColumn.ID,
        new IsDailyPayerColumn());

    return new EntityPropertiesDto(properties);

  }

  public static EntityKpisDto buildKpis(PaymentTransactionActionLogicalTable logicalTable) {
    var kpis = new KpisDto();
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
