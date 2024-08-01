package com.asemicanalytics.cli.userentity;

import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.dsgenerator.DsGeneratorHelper;
import com.asemicanalytics.cli.model.ColumnDto;
import com.asemicanalytics.core.logicaltable.action.ActivityLogicalTable;
import com.asemicanalytics.core.logicaltable.action.FirstAppearanceActionLogicalTable;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "activity-action", description = "Generate config for an activity action", mixinStandardHelpOptions = true)
public class GenerateActivityActionCommand extends GenerateActionCommand {

  @Inject
  QueryEngineClient queryEngineClient;

  @Inject
  ObjectMapper objectMapper;

  protected List<String> logicalTableTags() {
    return List.of(ActivityLogicalTable.TAG);
  }

  @Override
  protected Map<String, List<String>> additionalColumnTags(List<ColumnDto> columns) {
    Map<String, List<String>> tags = new HashMap<>();
    for (var column : columns) {
      if (column.getId().contains("country")
          || column.getId().contains("platform")
          || column.getId().contains("version")) {
        System.out.println("Adding " + ActivityLogicalTable.LAST_LOGIN_PROPERTY_TAG
            + " tag to " + column.getId() + ". It means entity property will be generated from it.");
        tags.put(column.getId(), List.of(ActivityLogicalTable.LAST_LOGIN_PROPERTY_TAG));
      }
    }
    return tags;
  }
}
