package com.asemicanalytics.cli.internal.dsgenerator.entity;

import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.Active1DayAgoColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.ActiveLast28Days;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.ActiveLast28DaysColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.ActiveTodayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.DaysActiveLast7DaysColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LastLoginDimensionColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.DauKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.MDauKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.MauKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.MauLostKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.RetentionCohortKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.RetentionCohortedDailyKpis;
import com.asemicanalytics.core.logicaltable.action.ActivityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityKpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.PropertiesDto;

public class Activity {
  public static EntityPropertiesDto buildProperties(ActivityLogicalTable logicalTable) {
    var properties = new PropertiesDto();
    properties.setAdditionalProperty(ActiveTodayColumn.ID,
        new ActiveTodayColumn(logicalTable.getId()));

    properties.setAdditionalProperty(Active1DayAgoColumn.ID, new Active1DayAgoColumn());
    properties.setAdditionalProperty(DaysActiveLast7DaysColumn.ID, new DaysActiveLast7DaysColumn());
    properties.setAdditionalProperty(ActiveLast28DaysColumn.ID, new ActiveLast28DaysColumn());
    properties.setAdditionalProperty(ActiveLast28Days.ID,
        new ActiveLast28Days(logicalTable.getId()));

    properties.setAdditionalProperty("last_login_date",
        new LastLoginDimensionColumn(logicalTable.getDateColumn().getId(),
            ActionColumnDto.DataType.DATE, logicalTable.getId()));

    for (var column : logicalTable.getColumns()
        .getColumnsByTag(ActivityLogicalTable.LAST_LOGIN_PROPERTY_TAG)) {
      String id = "last_login_" + column.getId();
      properties.setAdditionalProperty(id, new LastLoginDimensionColumn(
          column.getId(),
          ActionColumnDto.DataType.valueOf(column.getDataType().name()),
          logicalTable.getId()));
    }

    return new EntityPropertiesDto(properties);
  }

  public static EntityKpisDto buildKpis(ActivityLogicalTable logicalTable) {
    var kpis = new KpisDto();
    kpis.setAdditionalProperty(DauKpi.ID, new DauKpi(logicalTable.getDateColumn().getId()));
    kpis.setAdditionalProperty(MauKpi.ID, new MauKpi(logicalTable.getDateColumn().getId()));
    kpis.setAdditionalProperty(MauLostKpi.ID, new MauLostKpi(logicalTable.getDateColumn().getId()));
    kpis.setAdditionalProperty(MDauKpi.ID, new MDauKpi(logicalTable.getDateColumn().getId()));
    kpis.setAdditionalProperty(RetentionCohortKpi.ID, new RetentionCohortKpi());
    kpis.setAdditionalProperty(RetentionCohortedDailyKpis.ID,
        new RetentionCohortedDailyKpis(logicalTable.getDateColumn().getId()));
    return new EntityKpisDto(kpis);
  }

}
