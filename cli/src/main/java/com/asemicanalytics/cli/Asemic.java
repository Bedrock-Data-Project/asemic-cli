package com.asemicanalytics.cli;

import com.asemicanalytics.cli.config.ConfigCommand;
import com.asemicanalytics.cli.userentity.UserEntityCommand;
import io.micronaut.configuration.picocli.MicronautFactory;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "asemic", description = "A tool for managing asemic config.", mixinStandardHelpOptions = true,
    subcommands = {UserEntityCommand.class, ConfigCommand.class})
public class Asemic {
  public static void main(String[] args) throws Exception {
    int exitCode = execute(Asemic.class, args);
    System.exit(exitCode);
  }

  private static int execute(Class<?> clazz, String[] args) {
    try (ApplicationContext context = ApplicationContext.builder(
        clazz, Environment.CLI).start()) {

      return new CommandLine(clazz, new MicronautFactory(context))
          .setCaseInsensitiveEnumValuesAllowed(true)
          .setUsageHelpAutoWidth(true)
          .setExecutionExceptionHandler((ex, commandLine, parseResult) -> {
            commandLine.getErr().println(commandLine.getColorScheme().errorText(ex.getMessage()));

            return commandLine.getExitCodeExceptionMapper() != null
                ? commandLine.getExitCodeExceptionMapper().getExitCode(ex)
                : commandLine.getCommandSpec().exitCodeOnExecutionException();
          })
          .execute(args);
    }
  }
}
