package com.asemicanalytics.cli.internal.dsgenerator.entity;

import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.ActiveLast28DaysColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.ActiveLast7DaysColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.DauColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.DauYesterdayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LastLoginBuildVersionColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LastLoginCountryColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LastLoginPlatformColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LoginBuildVersionColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LoginCountryColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.LoginPlatformColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.activity.columns.MauColumn;
import com.asemicanalytics.core.logicaltable.action.ActivityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyActionDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyTotalDto;
import java.util.ArrayList;
import java.util.List;

public class ActivityColumns {
  public static EntityPropertiesDto build(ActivityLogicalTable logicalTable) {

    List<EntityPropertyActionDto> userActionColumns = new ArrayList<>();
    List<EntityPropertyTotalDto> totalColumns = new ArrayList<>();

    userActionColumns.add(new DauColumn(logicalTable.getId()));
    userActionColumns.add(new DauYesterdayColumn(logicalTable.getId()));
    userActionColumns.add(new ActiveLast7DaysColumn(logicalTable.getId()));
    userActionColumns.add(new ActiveLast28DaysColumn(logicalTable.getId()));
    userActionColumns.add(new MauColumn(logicalTable.getId()));

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
        totalColumns,
        List.of()
    );
  }

}
