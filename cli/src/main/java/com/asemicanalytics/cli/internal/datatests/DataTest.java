package com.asemicanalytics.cli.internal.datatests;

import com.asemicanalytics.cli.internal.CachingChartClient;
import picocli.CommandLine;

public abstract class DataTest {
  protected final String name;
  protected final String appId;

  public DataTest(String name, String appId) {
    this.name = name;
    this.appId = appId;
  }

  public abstract boolean supported();

  protected abstract TestResult run(CachingChartClient queryEngineClient);

  private String dots(String prefix) {
    return ".".repeat(60 - prefix.length());
  }

  public void test(CachingChartClient queryEngineClient) {
    if (!supported()) {
      System.out.println(
          CommandLine.Help.Ansi.AUTO.string(name + dots(name)
              + "@|fg(yellow) SKIPPED|@"));
      return;
    }

    try {
      TestResult result = run(queryEngineClient);
      if (result.expected().equals(result.actual())) {
        System.out.println(
            CommandLine.Help.Ansi.AUTO.string(name + dots(name)
                + "@|fg(green) SUCCESS|@"));
        System.out.println("    Value: " + result.expected());
      } else {
        System.out.println(
            CommandLine.Help.Ansi.AUTO.string(name + dots(name)
                + "@|fg(red) FAILED|@"));
        System.out.println("    Expected: " + result.expected());
        System.out.println("    Actual: " + result.actual());
      }
    } catch (Exception e) {
      System.out.println(
          CommandLine.Help.Ansi.AUTO.string(name + dots(name)
              + "@|fg(red) FAILED|@"));
      System.out.println("    " + e);
    }
  }
}
