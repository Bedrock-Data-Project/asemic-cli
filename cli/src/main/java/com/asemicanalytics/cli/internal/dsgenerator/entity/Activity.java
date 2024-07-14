package com.asemicanalytics.cli.internal.dsgenerator.entity;

import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.ActiveLast28DaysColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.ActiveLast7DaysColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.DauColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.DauDateColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.DauYesterdayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LastLoginBuildVersionColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LastLoginCountryColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LastLoginDateColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LastLoginPlatformColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LoginBuildVersionColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LoginCountryColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LoginPlatformColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.MauColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.DauCohortKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.DauKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.MDauCohortKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.MDauKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.MauCohortKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.MauKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.MauLostKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.RetentionCohortKpi;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.kpis.RetentionCohortedDailyKpis;
import com.asemicanalytics.core.logicaltable.action.ActivityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityKpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertySlidingWindowDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyTotalDto;
import java.util.ArrayList;
import java.util.List;

public class Activity {
  public static EntityPropertiesDto buildProperties(ActivityLogicalTable logicalTable) {

    List<EntityPropertyActionDto> userActionColumns = new ArrayList<>();
    List<EntityPropertySlidingWindowDto> slidingWindowColumns = new ArrayList<>();
    List<EntityPropertyTotalDto> totalColumns = new ArrayList<>();

    userActionColumns.add(new DauColumn(logicalTable.getId()));
    userActionColumns.add(
        new DauDateColumn(logicalTable.getId(), logicalTable.getDateColumn().getId()));

    slidingWindowColumns.add(new DauYesterdayColumn());
    slidingWindowColumns.add(new ActiveLast7DaysColumn());
    slidingWindowColumns.add(new ActiveLast28DaysColumn());
    slidingWindowColumns.add(new MauColumn(logicalTable.getId()));

    totalColumns.add(new LastLoginDateColumn());


    logicalTable.getCountryColumn().ifPresent(countryColumn -> {
      userActionColumns.add(new LoginCountryColumn(logicalTable.getId(), countryColumn.getId()));
      totalColumns.add(new LastLoginCountryColumn());
    });
    logicalTable.getPlatformColumn().ifPresent(platformColumn -> {
      userActionColumns.add(new LoginPlatformColumn(logicalTable.getId(), platformColumn.getId()));
      totalColumns.add(new LastLoginPlatformColumn());
    });
    logicalTable.getBuildVersionColumn().ifPresent(buildVersionColumn -> {
      userActionColumns.add(
          new LoginBuildVersionColumn(logicalTable.getId(), buildVersionColumn.getId()));
      totalColumns.add(new LastLoginBuildVersionColumn());
    });

    return new EntityPropertiesDto(
        List.of(),
        userActionColumns,
        slidingWindowColumns,
        totalColumns,
        List.of()
    );
  }

  public static EntityKpisDto buildKpis(ActivityLogicalTable logicalTable) {
    return new EntityKpisDto("Engagement",
        List.of(
            new DauKpi(logicalTable.getDateColumn().getId()),
            new DauCohortKpi(),
            new MauKpi(logicalTable.getDateColumn().getId()),
            new MauCohortKpi(),
            new MauLostKpi(logicalTable.getDateColumn().getId()),
            new MDauKpi(logicalTable.getDateColumn().getId()),
            new MDauCohortKpi(),
            new RetentionCohortKpi()
        ),
        List.of(1, 2, 3, 4, 5, 6, 7, 14, 28, 30, 60, 90, 180, 365),
        List.of(
            new RetentionCohortedDailyKpis()
        )
    );
  }

}
