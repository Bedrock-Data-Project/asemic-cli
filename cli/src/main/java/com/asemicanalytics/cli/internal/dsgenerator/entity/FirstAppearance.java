package com.asemicanalytics.cli.internal.dsgenerator.entity;

import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortSizeColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.RegistrationBuildVersionColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.RegistrationCountryColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.RegistrationDateColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.RegistrationPlatformColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.kpis.RegistrationsKpi;
import com.asemicanalytics.core.logicaltable.action.FirstAppearanceActionLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ColumnComputedDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityKpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertyFirstAppearanceDto;
import java.util.ArrayList;
import java.util.List;

public class FirstAppearance {
  public static EntityPropertiesDto buildProperties(FirstAppearanceActionLogicalTable datasource) {
    List<EntityPropertyFirstAppearanceDto> registrationColumns = new ArrayList<>();
    registrationColumns.add(new RegistrationDateColumn(datasource.getDateColumn().getId()));
    datasource.getCountryColumn().ifPresent(countryColumn ->
        registrationColumns.add(new RegistrationCountryColumn(countryColumn.getId()))
    );
    datasource.getPlatformColumn().ifPresent(platformColumn ->
        registrationColumns.add(new RegistrationPlatformColumn(platformColumn.getId()))
    );
    datasource.getBuildVersionColumn().ifPresent(buildVersionColumn ->
        registrationColumns.add(new RegistrationBuildVersionColumn(buildVersionColumn.getId()))
    );

    List<ColumnComputedDto> computedColumns = new ArrayList<>();
    computedColumns.add(new CohortDayColumn(datasource.getDateColumn().getId()));
    computedColumns.add(new CohortSizeColumn(datasource.getDateColumn().getId()));
    return new EntityPropertiesDto(
        registrationColumns,
        List.of(),
        List.of(),
        List.of(),
        computedColumns
    );
  }

  public static EntityKpisDto buildKpis(FirstAppearanceActionLogicalTable logicalTable) {
    return new EntityKpisDto("Registrations",
        List.of(
            new RegistrationsKpi(logicalTable.getDateColumn().getId())
        ),
        null,
        null
    );
  }
}
