package com.asemicanalytics.cli.internal;

import com.asemicanalytics.config.parser.yaml.YamlFileLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


public class YamlSerDe implements YamlFileLoader {

  @Override
  public <T> T load(String content, Class<T> valueType) {
    try {
      return YamlObjectMapperFactory.build().readValue(content, valueType);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> void save(String schemaName, T object, Path path) throws IOException {
    Files.writeString(
        path,
        "# $schema: http://schema.asemicanalytics.com/v1/semantic_layer/" + schemaName
            + ".json\n" + YamlObjectMapperFactory.build().writeValueAsString(object),
        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
  }
}
