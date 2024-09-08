package com.asemicanalytics.cli.userentity;

import picocli.CommandLine;

@CommandLine.Command(name = "user-entity-model", description = "Generate and backill user entity model", mixinStandardHelpOptions = true, subcommands = {
    BackfillEntityCommand.class,
    BackfillStatisticsCommand.class,
    GenerateActionCommand.class,
    GenerateFirstAppearanceActionCommand.class,
    GenerateActivityActionCommand.class,
    GeneratePaymentTransactionActionCommand.class,
    GenerateEntityCommand.class,
})
public class UserEntityCommand {
}
