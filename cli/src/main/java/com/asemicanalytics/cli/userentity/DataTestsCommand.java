package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.CachingChartClient;
import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.datatests.TimeTravelPropertySimple;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import picocli.CommandLine;

@CommandLine.Command(name = "data-tests", description = "Runs a series of consistency tests on your entity data model", mixinStandardHelpOptions = true)
public class DataTestsCommand implements Runnable {

  @Inject
  QueryEngineClient queryEngineClient;

  @CommandLine.Option(names = "--app-id", description = "App id")
  String appId;

  @CommandLine.Option(names = "--date", description = "YYYY-MM-DD", required = true)
  LocalDate dateFrom;

  @CommandLine.Option(names = "--version", description = "Custom config version")
  Optional<String> version;

  @CommandLine.Option(names = "--daily-kpi-avg",
      description = "A kpi that can be calculated from daily table. It should be additive (sum of group bys should result in original kpi) but have AVG total function")
  Optional<String> dailyKpiAvg;

  @CommandLine.Option(names = "--active-kpi-avg",
      description = "A kpi that can be calculated from active table. It should be additive (sum of group bys should result in original kpi) but have AVG total function")
  Optional<String> activeKpiAvg;

  @CommandLine.Option(names = "--daily-kpi-sum",
      description = "A kpi that can be calculated from daily table. It should be additive (sum of group bys should result in original kpi) but have SUM total function")
  Optional<String> dailyKpiSum;

  @CommandLine.Option(names = "--active-kpi-sum",
      description = "A kpi that can be calculated from active table. It should be additive (sum of group bys should result in original kpi) but have SUM total function")
  Optional<String> activeKpiSum;

  @CommandLine.Option(names = "--numeric-total-property",
      description = "A property that is a sum of another property")
  Optional<String> numericTotalProperty;

  @CommandLine.Option(names = "--numeric-property",
      description = "A property that is source for numeric-total-property")
  Optional<String> numericProperty;

  @CommandLine.Option(names = "--boolean-total-dimensional-property",
      description = "A total property that is a dimension and has up to two values")
  Optional<String> booleanTotalDimensionalProperty;

  @CommandLine.Option(names = "--numeric-event-column",
      description = "<event_name>.<column_name> for a numeric column of an event")
  Optional<String> numericActionColumn;

  @CommandLine.Option(names = "--numeric-event-property",
      description = "An event property that is sum of a column and has default 0")
  Optional<String> numericActionProperty;

  @Override
  public void run() {
    CachingChartClient cachingChartClient = new CachingChartClient(queryEngineClient);

    String appId = this.appId != null ? this.appId : GlobalConfig.getAppId();
    List.of(
//        // total overall
//        new GroupByTotalOverall(appId, "group by total overall daily kpi AVG", dailyKpiAvg,
//            booleanTotalDimensionalProperty, dateFrom),
//        new GroupByTotalOverall(appId, "group by total overall active kpi AVG", activeKpiAvg,
//            booleanTotalDimensionalProperty, dateFrom),
//        new GroupByTotalOverall(appId, "group by total overall daily kpi SUM", dailyKpiSum,
//            booleanTotalDimensionalProperty, dateFrom),
//        new GroupByTotalOverall(appId, "group by total overall active kpi SUM", activeKpiSum,
//            booleanTotalDimensionalProperty, dateFrom),
//
//        // group totals
//        new GroupByGroupTotalsAvg(appId, "group by total daily kpi AVG", dailyKpiAvg,
//            booleanTotalDimensionalProperty, dateFrom),
//        new GroupByGroupTotalsAvg(appId, "group by total active kpi AVG", activeKpiAvg,
//            booleanTotalDimensionalProperty, dateFrom),
//        new GroupByGroupTotalsSum(appId, "group by total daily kpi SUM", dailyKpiSum,
//            booleanTotalDimensionalProperty, dateFrom),
//        new GroupByGroupTotalsSum(appId, "group by total active kpi SUM", activeKpiSum,
//            booleanTotalDimensionalProperty, dateFrom),
//
//        // filter
//        new Filter(appId, "filter daily kpi", dailyKpiAvg, dateFrom),
//        new Filter(appId, "filter active kpi", activeKpiAvg, dateFrom),
//
//        // compare
//        new Compare(appId, "compare daily kpi", dailyKpiAvg, dateFrom),
//        new Compare(appId, "compare active kpi", activeKpiAvg, dateFrom),
//
//        // Performed event
//        new PerformedActionSimple(appId, "Performed event simple daily", dailyKpiSum, numericActionColumn, dateFrom),
//        new PerformedActionSimple(appId, "Performed event simple active", activeKpiSum, numericActionColumn, dateFrom),
//        new PerformedActionSimple(appId, "Performed event simple daily avg", dailyKpiAvg, numericActionColumn, dateFrom),
//        new PerformedActionSimple(appId, "Performed event simple active avg", activeKpiAvg, numericActionColumn, dateFrom),
//        new PerformedActionSimpleWithFilter(appId, "Performed event with filter daily", dailyKpiSum, numericActionColumn, dateFrom),
//        new PerformedActionSimpleWithFilter(appId, "Performed event with filter active", activeKpiSum, numericActionColumn, dateFrom),
//        new PerformedActionDynamic(appId, "Performed event dynamic daily", dailyKpiSum, numericActionColumn, dateFrom),
//        new PerformedActionDynamic(appId, "Performed event dynamic active", activeKpiSum, numericActionColumn, dateFrom),
//        new PerformedActionStatic(appId, "Performed event static daily", dailyKpiSum, numericActionColumn, dateFrom),
//        new PerformedActionStatic(appId, "Performed event static active", activeKpiSum, numericActionColumn, dateFrom),

        new TimeTravelPropertySimple(appId, "Time travel property simple", dailyKpiAvg,
            numericProperty, dateFrom),
        new TimeTravelPropertySimple(appId, "Time travel property simple", activeKpiAvg,
            numericProperty, dateFrom)


//        Dau(where Had event X) + DAU(where Had no event X) == DAU
//        DAU(where Had event X over -7, -1) + DAU(where Had no event X over -7, -1) == DAU
        // window
        // total
        // performed event
        // time travel
    ).forEach(test -> test.test(cachingChartClient));
  }
}
