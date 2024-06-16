package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide;

import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.revenue.columns.DailyPayersColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.revenue.columns.IsPayerColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.revenue.columns.RevenueColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.revenue.columns.RevenueLast28DaysColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.revenue.columns.TotalRevenueColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.revenue.columns.TransactionCountColumn;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnTotalDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnUserActionDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnsDto;
import java.util.ArrayList;
import java.util.List;

public class RevenueColumns {
  public static UserWideColumnsDto build(
      String revenueDatasourceName,
      String revenueColumn) {

    List<UserWideColumnUserActionDto> userActionColumns = new ArrayList<>();
    List<UserWideColumnTotalDto> totalColumns = new ArrayList<>();

    userActionColumns.add(new RevenueColumn(revenueDatasourceName, revenueColumn));
    userActionColumns.add(new RevenueLast28DaysColumn(revenueDatasourceName, revenueColumn));
    userActionColumns.add(new DailyPayersColumn(revenueDatasourceName));
    userActionColumns.add(new TransactionCountColumn(revenueDatasourceName));

    totalColumns.add(new TotalRevenueColumn());
    totalColumns.add(new IsPayerColumn());

    return new UserWideColumnsDto(
        List.of(),
        userActionColumns,
        totalColumns,
        List.of()
    );
  }
}
