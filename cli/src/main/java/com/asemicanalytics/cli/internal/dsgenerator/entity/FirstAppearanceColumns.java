package com.asemicanalytics.cli.internal.dsgenerator.entity;

import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.RegistrationBuildVersionColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.RegistrationCountryColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.RegistrationPlatformColumn;
import com.asemicanalytics.core.logicaltable.action.FirstAppearanceActionLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyFirstAppearanceDto;
import java.util.ArrayList;
import java.util.List;

public class FirstAppearanceColumns {
  public static EntityPropertiesDto build(FirstAppearanceActionLogicalTable datasource) {
    List<EntityPropertyFirstAppearanceDto> registrationColumns = new ArrayList<>();
    datasource.getCountryColumn().ifPresent(countryColumn ->
        registrationColumns.add(new RegistrationCountryColumn(countryColumn.getId()))
    );
    datasource.getPlatformColumn().ifPresent(platformColumn ->
        registrationColumns.add(new RegistrationPlatformColumn(platformColumn.getId()))
    );
    datasource.getBuildVersionColumn().ifPresent(buildVersionColumn ->
        registrationColumns.add(new RegistrationBuildVersionColumn(buildVersionColumn.getId()))
    );

    return new EntityPropertiesDto(
        registrationColumns,
        List.of(),
        List.of(),
        List.of()
    );
  }
}
