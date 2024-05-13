package com.asemicanalytics.cli.semanticlayer;

import com.asemicanalytics.cli.semanticlayer.internal.GlobalConfig;
import com.asemicanalytics.cli.semanticlayer.internal.QueryEngineClient;
import com.asemicanalytics.cli.semanticlayer.internal.ZipUtils;
import com.asemicanalytics.cli.semanticlayer.internal.cli.SpinnerCli;
import jakarta.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import picocli.CommandLine;

@CommandLine.Command(name = "pull", mixinStandardHelpOptions = true)
public class PullCommand implements Runnable {

  @Inject
  QueryEngineClient queryEngineClient;

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
        queryEngineClient.downloadCurrentConfig(GlobalConfig.getAppId(), tempDir.toPath());
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