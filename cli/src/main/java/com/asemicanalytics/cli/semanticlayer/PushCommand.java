package com.asemicanalytics.cli.semanticlayer;

import com.asemicanalytics.cli.semanticlayer.internal.GlobalConfig;
import com.asemicanalytics.cli.semanticlayer.internal.QueryEngineClient;
import com.asemicanalytics.cli.semanticlayer.internal.ZipUtils;
import com.asemicanalytics.cli.semanticlayer.internal.cli.SpinnerCli;
import jakarta.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import picocli.CommandLine;

@CommandLine.Command(name = "push", mixinStandardHelpOptions = true)
public class PushCommand implements Runnable {

  @Inject
  QueryEngineClient queryEngineClient;

  @CommandLine.Option(names = "--version", description = "Custom config version")
  Optional<String> version;

  public static void push(QueryEngineClient queryEngineClient, Optional<String> version) {
    new SpinnerCli().spin(() -> {
      try {
        Path zipFilePath = null;
        try {
          zipFilePath = ZipUtils.zipDirectory(GlobalConfig.getAppIdDir());
          queryEngineClient.uploadConfig(GlobalConfig.getAppId(), zipFilePath, version);
        } finally {
          if (zipFilePath != null) {
            Files.delete(zipFilePath);
          }
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      return null;
    });
  }

  @Override
  public void run() {
    push(queryEngineClient, version);
    System.out.println(CommandLine.Help.Ansi.AUTO.string("@|fg(green) OK|@"));
  }
}
