package com.asemicanalytics.cli.semanticlayer.internal;

import com.asemicanalytics.cli.model.ChartDataDto;
import com.asemicanalytics.cli.model.ChartRequestDto;
import com.asemicanalytics.cli.model.ColumnDto;
import com.asemicanalytics.cli.model.DatabaseDto;
import com.asemicanalytics.cli.model.DatasourceDto;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Singleton
public class QueryEngineClient {
  private final BlockingHttpClient httpClient;

  @Inject
  public QueryEngineClient(@Client HttpClient httpClient) {
    this.httpClient = httpClient.toBlocking();
  }

  public List<ColumnDto> getColumns(String appId, String table) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("datasources")
        .path(table)
        .path("columns")
        .build();

    HttpRequest<?> request = HttpRequest.GET(uri)
        .bearerAuth(GlobalConfig.getApiToken());

    return httpClient.retrieve(request, Argument.listOf(ColumnDto.class));
  }

  public Map<String, DatasourceDto> getDailyDatasources(String appId, Optional<String> version) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("datasources/daily")
        .build();

    MutableHttpRequest<?> request = HttpRequest.GET(uri)
        .bearerAuth(GlobalConfig.getApiToken());
    version.ifPresent(v -> request.header("AppConfigVersion", v));

    return httpClient.retrieve(request, Argument.mapOf(String.class, DatasourceDto.class));
  }

  public ChartDataDto submitChart(String appId, ChartRequestDto chartRequestDto,
                                  Optional<String> version) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("charts/submit")
        .build();

    MutableHttpRequest<?> request = HttpRequest.POST(uri, chartRequestDto)
        .bearerAuth(GlobalConfig.getApiToken())
        .contentType(MediaType.APPLICATION_JSON);
    version.ifPresent(v -> request.header("AppConfigVersion", v));

    return httpClient.retrieve(request, ChartDataDto.class);
  }

  public void submitDbAuth(String appId, DatabaseDto databaseDto) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("datasources-configure")
        .path("db-auth")
        .build();

    HttpRequest<?> request = HttpRequest.POST(uri, databaseDto)
        .bearerAuth(GlobalConfig.getApiToken())
        .contentType(MediaType.APPLICATION_JSON);

    httpClient.retrieve(request, String.class);
  }

  public void downloadCurrentConfig(String appId, Path configPath) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("datasources/current-config")
        .build();

    MutableHttpRequest<?> request = HttpRequest.GET(uri)
        .bearerAuth(GlobalConfig.getApiToken())
        .accept(MediaType.APPLICATION_OCTET_STREAM);

    byte[] response = httpClient.retrieve(request, byte[].class);

    try (FileOutputStream fos = new FileOutputStream(configPath.toFile())) {
      fos.write(response);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void downloadConfigByVersion(String appId, String version, Path configPath) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("datasources/config")
        .path(version)
        .build();

    MutableHttpRequest<?> request = HttpRequest.GET(uri)
        .bearerAuth(GlobalConfig.getApiToken())
        .accept(MediaType.APPLICATION_OCTET_STREAM);

    byte[] response = httpClient.retrieve(request, byte[].class);

    try (FileOutputStream fos = new FileOutputStream(configPath.toFile())) {
      fos.write(response);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void uploadConfig(String appId, Path configPath, Optional<String> version) {
    UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("datasources-configure/config")
        .build();

    var uri =
        UriBuilder.of("http://localhost:8083/api/v1/heroicmini/datasources-configure/config")
            .build();

    File file = configPath.toFile();

    MultipartBody body = MultipartBody.builder()
        .addPart("appConfig", file.getName(), MediaType.MULTIPART_FORM_DATA_TYPE, file)
        .build();

    MutableHttpRequest<?> request = HttpRequest.POST(uri, body)
        .bearerAuth(GlobalConfig.getApiToken())
        .contentType(MediaType.MULTIPART_FORM_DATA_TYPE);
    version.ifPresent(v -> request.header("AppConfigVersion", v));

    httpClient.exchange(request, String.class);
  }
}
