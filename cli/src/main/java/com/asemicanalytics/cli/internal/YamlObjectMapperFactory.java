package com.asemicanalytics.cli.internal;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class YamlObjectMapperFactory {
  public static ObjectMapper build() {
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory()
        .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
        .enable(YAMLGenerator.Feature.INDENT_ARRAYS));
    objectMapper.registerModule(new Jdk8Module());
    return objectMapper;
  }
}
