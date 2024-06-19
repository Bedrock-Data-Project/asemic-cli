package com.asemicanalytics.cli.config;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.ZipUtils;
import com.asemicanalytics.cli.internal.cli.SpinnerCli;
import jakarta.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;
import picocli.CommandLine;

@CommandLine.Command(name = "pull", description = "Download current config", mixinStandardHelpOptions = true)
public class PullCommand implements Runnable {

  @Inject
  QueryEngineClient queryEngineClient;

  @CommandLine.Option(names = "--version", description = "Custom config version")
  Optional<String> version;

  private static void deleteDirContents(Path dir) throws IOException {
    Files.walkFileTree(dir, new SimpleFileVisitor<>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Files.delete(dir);
        return FileVisitResult.CONTINUE;
      }
    });
  }

  @Override
  public void run() {
    new SpinnerCli().spin(() -> {
      try {
        File tempDir = Files.createTempDirectory("asemic").toFile();
        if (version.isPresent()) {
          queryEngineClient.downloadConfigByVersion(GlobalConfig.getAppId(), version.get(),
              tempDir.toPath());
        } else {
          queryEngineClient.downloadCurrentConfig(GlobalConfig.getAppId(), tempDir.toPath());
        }
        deleteDirContents(GlobalConfig.getAppIdDir());
        ZipUtils.unzipToDirectory(tempDir.toPath(), GlobalConfig.getAppIdDir());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return null;
    });
    System.out.println(CommandLine.Help.Ansi.AUTO.string("@|fg(green) OK|@"));
  }
}
