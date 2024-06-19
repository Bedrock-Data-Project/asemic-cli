package com.asemicanalytics.cli.config;

import picocli.CommandLine;

@CommandLine.Command(name = "config", description = "Manage asemic semantic layer config", mixinStandardHelpOptions = true, subcommands = {
    AuthCommand.class,
    ValidateCommand.class,
    PushCommand.class,
    PullCommand.class,
})
public class ConfigCommand {
}
