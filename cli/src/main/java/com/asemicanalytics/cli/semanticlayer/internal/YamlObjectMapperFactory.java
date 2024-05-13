package com.asemicanalytics.cli.semanticlayer.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class YamlObjectMapperFactory {
  public static ObjectMapper build() {
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    objectMapper.registerModule(new Jdk8Module());
    return objectMapper;
  }
}
