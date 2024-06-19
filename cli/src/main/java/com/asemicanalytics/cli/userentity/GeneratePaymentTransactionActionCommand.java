package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.model.ColumnDto;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.dsgenerator.DsGeneratorHelper;
import com.asemicanalytics.cli.internal.dsgenerator.MostSimilarColumn;
import com.asemicanalytics.core.logicaltable.action.PaymentTransactionActionLogicalTable;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "payment-transaction-action", description = "Generate config for a payment transaction action", mixinStandardHelpOptions = true)
public class GeneratePaymentTransactionActionCommand extends GenerateActionCommand {

  @Option(names = "--transaction-amount-column", description = "Name of country column.")
  Optional<String> transactionAmountColumnOption;

  @Inject
  QueryEngineClient queryEngineClient;

  @Inject
  ObjectMapper objectMapper;

  protected List<String> logicalTableTags() {
    return List.of(PaymentTransactionActionLogicalTable.TAG);
  }

  protected Map<String, List<String>> additionalColumnTags(List<ColumnDto> columns) {
    var dsGeneratorHelper = new DsGeneratorHelper(queryEngineClient, noWizard);

    Map<String, List<String>> columnTags = new HashMap<>();

    final String transactionAmountColumn = dsGeneratorHelper.readInput(
        dateColumnOption, "transaction-amount-column",
        Optional.of("\nEnter the name of transaction amount column."),
        "Enter date column name",
        MostSimilarColumn.find("amount", columns, Set.of("integer", "number")));

    columnTags.put(transactionAmountColumn,
        List.of(PaymentTransactionActionLogicalTable.TRANSACTION_AMOUNT_COLUMN_TAG));

    return columnTags;
  }

}
