package com.asemicanalytics.cli.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class YamlObjectMapperFactory {
  public static ObjectMapper build() {
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory()
        .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
        .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
    );
    objectMapper.registerModule(new Jdk8Module());
    return objectMapper;
  }
}
