package com.asemicanalytics.cli.semanticlayer;

import com.asemicanalytics.cli.semanticlayer.internal.GlobalConfig;
import com.asemicanalytics.cli.semanticlayer.internal.QueryEngineClient;
import com.asemicanalytics.cli.semanticlayer.internal.ZipUtils;
import com.asemicanalytics.cli.semanticlayer.internal.cli.SpinnerCli;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import picocli.CommandLine;

@CommandLine.Command(name = "push", mixinStandardHelpOptions = true)
public class PushCommand implements Runnable {

  @Inject
  QueryEngineClient queryEngineClient;

  public void push(String version) throws IOException {
    new SpinnerCli().spin(() -> {
      try {
        Path zipFilePath = null;
        try {
          zipFilePath = ZipUtils.zipDirectory(GlobalConfig.getAppIdDir());

          Map<String, String> headers = version != null
              ? Map.of("AppConfigVersion", version)
              : Map.of();
          queryEngineClient.uploadConfig(GlobalConfig.getAppId(), zipFilePath,
              Optional.ofNullable(version));
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

  public void push() throws IOException {
    push(null);
  }

  @Override
  public void run() {
    try {
      push();
      System.out.println(CommandLine.Help.Ansi.AUTO.string("@|fg(green) OK|@"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
