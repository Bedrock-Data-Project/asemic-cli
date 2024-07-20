package com.asemicanalytics.cli.internal;

import com.asemicanalytics.config.parser.yaml.YamlFileLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


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

    String initialYaml = YamlObjectMapperFactory.build().writeValueAsString(object);

    DumperOptions options = new DumperOptions();
    options.setWidth(Integer.MAX_VALUE);
    Yaml yaml = new Yaml(options);
    Map<String, Object> data = yaml.load(initialYaml);
    String reprocessed = yaml.dump(data);

    Files.writeString(
        path,
        "# $schema: http://schema.asemicanalytics.com/v1/semantic_layer/" + schemaName
            + ".json\n" + reprocessed,
        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
  }
}
