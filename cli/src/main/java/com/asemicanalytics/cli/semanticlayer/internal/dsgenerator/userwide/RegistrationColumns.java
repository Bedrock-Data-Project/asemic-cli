package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide;

import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.registration.columns.RegistrationCountryColumn;
import com.asemicanalytics.cli.semanticlayer.internal.dsgenerator.userwide.registration.columns.RegistrationPlatformColumn;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnRegistrationDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.UserWideColumnsDto;
import java.util.ArrayList;
import java.util.List;

public class RegistrationColumns {
  public static UserWideColumnsDto build(
      String countryColumn,
      String platformColumn) {

    List<UserWideColumnRegistrationDto> registrationColumns = new ArrayList<>();
    if (!countryColumn.isEmpty()) {
      registrationColumns.add(new RegistrationCountryColumn(countryColumn));
    }
    if (!platformColumn.isEmpty()) {
      registrationColumns.add(new RegistrationPlatformColumn(platformColumn));
    }

    return new UserWideColumnsDto(
        registrationColumns,
        List.of(),
        List.of(),
        List.of()
    );
  }
}
