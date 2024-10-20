package com.asemicanalytics.cli.userentity;

import picocli.CommandLine;

@CommandLine.Command(name = "user-entity-model", description = "Generate and backill user entity model", mixinStandardHelpOptions = true, subcommands = {
    BackfillEntityCommand.class,
    BackfillStatisticsCommand.class,
    GenerateEventCommand.class,
    GenerateEventsCommand.class,
    GeneratePaymentTransactionEventCommand.class,
    GenerateEntityCommand.class,
    DataTestsCommand.class,
    KpiQueryCommand.class,
})
public class UserEntityCommand {
}
