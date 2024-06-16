package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide;

import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns.ActiveLast7DaysColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns.ActiveLast28DaysColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns.DauColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns.DauYesterdayColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns.LastLoginCountryColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns.LastLoginPlatformColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns.LoginCountryColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns.LoginPlatformColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.activity.columns.MauColumn;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnTotalDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnUserActionDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnsDto;
import java.util.ArrayList;
import java.util.List;

public class ActivityColumns {
  public static UserWideColumnsDto build(
      String activityDatasourceName,
      String countryColumn,
      String platformColumn) {

    List<UserWideColumnUserActionDto> userActionColumns = new ArrayList<>();
    List<UserWideColumnTotalDto> totalColumns = new ArrayList<>();

    userActionColumns.add(new DauColumn(activityDatasourceName));
    userActionColumns.add(new DauYesterdayColumn(activityDatasourceName));
    userActionColumns.add(new ActiveLast7DaysColumn(activityDatasourceName));
    userActionColumns.add(new ActiveLast28DaysColumn(activityDatasourceName));
    userActionColumns.add(new MauColumn(activityDatasourceName));

    if (!countryColumn.isEmpty()) {
      userActionColumns.add(new LoginCountryColumn(activityDatasourceName, countryColumn));
      totalColumns.add(new LastLoginCountryColumn());

    }

    if (!platformColumn.isEmpty()) {
      userActionColumns.add(new LoginPlatformColumn(activityDatasourceName, platformColumn));
      totalColumns.add(new LastLoginPlatformColumn());
    }

    return new UserWideColumnsDto(
        List.of(),
        userActionColumns,
        totalColumns,
        List.of()
    );
  }
}
