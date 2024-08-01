package com.asemicanalytics.cli.internal.dsgenerator.entity;

import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortDayColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.CohortSizeColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.columns.RegistrationDimensionColumn;
import com.asemicanalytics.cli.internal.dsgenerator.entity.registration.kpis.RegistrationsKpi;
import com.asemicanalytics.core.logicaltable.action.FirstAppearanceActionLogicalTable;
import com.asemicanalytics.core.logicaltable.entity.EntityLogicalTable;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.ActionColumnDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityKpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.EntityPropertiesDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.KpisDto;
import com.asemicanalytics.semanticlayer.config.dto.v1.semantic_layer.PropertiesDto;

public class FirstAppearance {
  public static EntityPropertiesDto buildProperties(FirstAppearanceActionLogicalTable datasource) {
    var properties = new PropertiesDto();

    properties.setAdditionalProperty(EntityLogicalTable.FIRST_APPEARANCE_DATE_COLUMN,
        new RegistrationDimensionColumn(datasource.getDateColumn().getId(),
            ActionColumnDto.DataType.DATE));

    for (var column : datasource.getColumns()
        .getColumnsByTag(FirstAppearanceActionLogicalTable.FIRST_APPEARANCE_PROPERTY_TAG)) {
      String id = "first_appearance_" + column.getId();
      properties.setAdditionalProperty(id, new RegistrationDimensionColumn(
          column.getId(),
          ActionColumnDto.DataType.valueOf(column.getDataType().name())));
    }

    properties.setAdditionalProperty(CohortDayColumn.ID,
        new CohortDayColumn(datasource.getDateColumn().getId()));
    properties.setAdditionalProperty(CohortSizeColumn.ID,
        new CohortSizeColumn(datasource.getDateColumn().getId()));

    return new EntityPropertiesDto(properties);
  }

  public static EntityKpisDto buildKpis(FirstAppearanceActionLogicalTable logicalTable) {
    var kpis = new KpisDto();
    kpis.setAdditionalProperty(RegistrationsKpi.ID,
        new RegistrationsKpi(logicalTable.getDateColumn().getId()));
    return new EntityKpisDto(kpis);
  }
}
