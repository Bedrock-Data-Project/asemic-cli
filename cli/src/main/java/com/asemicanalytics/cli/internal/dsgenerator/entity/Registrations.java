package com.asemicanalytics.cli.internal.dsgenerator.entity;

import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortSizeColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.RegistrationDimensionColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.kpis.RegistrationsKpi;
import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.core.logicaltable.event.RegistrationsLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.DataType;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityKpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.PropertiesDto;

public class Registrations {
  public static EntityPropertiesDto buildProperties(RegistrationsLogicalTable datasource) {
    var properties = new PropertiesDto();

    properties.setAdditionalProperty(EntityLogicalTable.REGISTRATION_DATE_COLUMN,
        new RegistrationDimensionColumn(datasource.getDateColumn().getId(),
            DataType.DATE));

    for (var column : datasource.getColumns()
        .getColumnsByTag(RegistrationsLogicalTable.REGISTRATIONS_PROPERTY_TAG)) {
      String id = "registration_" + column.getId();
      properties.setAdditionalProperty(id, new RegistrationDimensionColumn(
          column.getId(),
          DataType.valueOf(column.getDataType().name())));
    }

    properties.setAdditionalProperty(CohortDayColumn.ID,
        new CohortDayColumn(datasource.getDateColumn().getId()));
    properties.setAdditionalProperty(CohortSizeColumn.ID,
        new CohortSizeColumn());

    return new EntityPropertiesDto(properties);
  }

  public static EntityKpisDto buildKpis(RegistrationsLogicalTable logicalTable) {
    var kpis = new KpisDto();
    kpis.setAdditionalProperty(RegistrationsKpi.ID,
        new RegistrationsKpi(logicalTable.getDateColumn().getId()));
    return new EntityKpisDto(kpis);
  }
}
