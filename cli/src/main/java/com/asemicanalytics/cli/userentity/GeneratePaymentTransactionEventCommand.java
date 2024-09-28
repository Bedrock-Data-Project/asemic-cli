package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.dsgenerator.DsGeneratorHelper;
import com.asemicanalytics.cli.internal.dsgenerator.MostSimilarColumn;
import com.asemicanalytics.cli.model.ColumnDto;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "payment-transaction-event", description = "Generate config for a payment transaction event", mixinStandardHelpOptions = true)
public class GeneratePaymentTransactionEventCommand extends GenerateEventCommand {

  @Option(names = "--transaction-amount-column", description = "Name of transaction amount column.")
  Optional<String> transactionAmountColumnOption;

  @Inject
  QueryEngineClient queryEngineClient;

  @Inject
  ObjectMapper objectMapper;

  protected List<String> logicalTableTags() {
    var tags = super.logicalTableTags();
    tags.add("payment_transaction_event");
    return tags;
  }

  protected Map<String, List<String>> additionalColumnTags(List<ColumnDto> columns) {
    var dsGeneratorHelper = new DsGeneratorHelper(queryEngineClient, noWizardOption);

    Map<String, List<String>> columnTags = new HashMap<>();

    final String transactionAmountColumn = dsGeneratorHelper.readInput(
        dateColumnOption, "transaction-amount-column",
        Optional.of("\nEnter the name of transaction amount column."),
        "Enter transaction amount column name",
        MostSimilarColumn.find("amount", columns, Set.of("integer", "number")));

    columnTags.put(transactionAmountColumn,
        List.of("transaction_amount_column"));

    return columnTags;
  }

}
