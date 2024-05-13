package com.asemicanalytics.cli;

import com.asemicanalytics.cli.semanticlayer.SemanticLayerCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;

@Command(name = "asemic", mixinStandardHelpOptions = true, subcommands = {
    SemanticLayerCommand.class})
public class Asemic{
  public static void main(String[] args) throws Exception {
    PicocliRunner.execute(Asemic.class, args);
  }
}
