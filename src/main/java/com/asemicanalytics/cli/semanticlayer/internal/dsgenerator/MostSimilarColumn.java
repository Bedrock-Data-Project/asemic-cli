package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator;

import com.asemicanalytics.cli.model.ColumnDto;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MostSimilarColumn {
  public static Optional<String> find(String target, List<ColumnDto> columns, Set<String> allowedTypes) {
    int minDistance = Integer.MAX_VALUE;
    String mostSimilarColumn = null;

    for (var column : columns) {
      if (!allowedTypes.contains(column.getDataType())) {
        continue;
      }
      if (!column.getId().contains(target)) {
        continue;
      }

      int distance = StringEditDistance.of(target, column.getId());
      if (distance < minDistance) {
        minDistance = distance;
        mostSimilarColumn = column.getId();
      }
    }

    return Optional.ofNullable(mostSimilarColumn);
  }
}
