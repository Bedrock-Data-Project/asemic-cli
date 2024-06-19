package com.asemicanalytics.cli;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.Serdeable;
import org.jsonschema2pojo.AbstractAnnotator;

public class MicronautJsonSchemaAnnotator extends AbstractAnnotator {
  @Override
  public void propertyOrder(JDefinedClass clazz, JsonNode propertiesNode) {
    clazz.annotate(ReflectiveAccess.class);
    clazz.annotate(Serdeable.class);
  }
}
