package com.asemicanalytics.cli;

import com.asemicanalytics.cli.semanticlayer.SemanticLayerCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;

@Command(name = "asemic", mixinStandardHelpOptions = true, subcommands = {
    SemanticLayerCommand.class})
public class Asemic implements Runnable {
  public static void main(String[] args) throws Exception {
    PicocliRunner.run(Asemic.class, args);
  }

  @Override
  public void run() {

  }
}
