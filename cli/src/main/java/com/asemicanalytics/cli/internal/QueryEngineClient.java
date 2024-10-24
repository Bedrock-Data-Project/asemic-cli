package com.asemicanalytics.cli.internal;

import com.asemicanalytics.cli.model.BackfillPropertiesStatistics;
import com.asemicanalytics.cli.model.BackfillTableStatisticsDto;
import com.asemicanalytics.cli.model.ChartDataDto;
import com.asemicanalytics.cli.model.ColumnDto;
import com.asemicanalytics.cli.model.DatabaseDto;
import com.asemicanalytics.cli.model.EntityChartRequestDto;
import com.asemicanalytics.cli.model.LogicalTableDto;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.DefaultHttpClientConfiguration;
import io.micronaut.http.client.HttpClientConfiguration;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.http.client.netty.DefaultHttpClient;
import io.micronaut.http.uri.UriBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class QueryEngineClient {
  private final BlockingHttpClient httpClient;

  public QueryEngineClient() {
    HttpClientConfiguration configuration = new DefaultHttpClientConfiguration();
    configuration.setReadTimeout(Duration.ofSeconds(600));
    this.httpClient =
        new RetryableHttpClient(3, new DefaultHttpClient((URI) null, configuration).toBlocking());
  }

  public List<ColumnDto> getColumns(String appId, String table) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("schemas/tables")
        .path(table)
        .build();

    HttpRequest<?> request = HttpRequest.GET(uri)
        .header("Authorization", "Apikey " + GlobalConfig.getApiToken());

    try {
      return httpClient.retrieve(request, Argument.listOf(ColumnDto.class));
    } catch (HttpClientResponseException e) {
      throw new QueryEngineException(e.getResponse().getBody(String.class).orElse("Unknown error"));
    } catch (Exception e) {
      throw new QueryEngineException("Unknown error");
    }
  }

  public Map<String, LogicalTableDto> getDailyDatasources(String appId, Optional<String> version) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("datasources/daily")
        .build();

    MutableHttpRequest<?> request = HttpRequest.GET(uri)
        .header("Authorization", "Apikey " + GlobalConfig.getApiToken());
    version.ifPresent(v -> request.header("AppConfigVersion", v));

    try {
      return httpClient.retrieve(request, Argument.mapOf(String.class, LogicalTableDto.class));
    } catch (HttpClientResponseException e) {
      throw new QueryEngineException(e.getResponse().getBody(String.class).orElse("Unknown error"));
    } catch (Exception e) {
      throw new QueryEngineException("Unknown error");
    }
  }

  public ChartDataDto submitChartValidate(String appId, EntityChartRequestDto chartRequestDto,
                                          Optional<String> version) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("charts/submit-validate")
        .build();

    MutableHttpRequest<?> request = HttpRequest.POST(uri, chartRequestDto)
        .header("Authorization", "Apikey " + GlobalConfig.getApiToken())
        .contentType(MediaType.APPLICATION_JSON);
    version.ifPresent(v -> request.header("AppConfigVersion", v));

    try {
      return httpClient.retrieve(request, ChartDataDto.class);
    } catch (HttpClientResponseException e) {
      throw new QueryEngineException(e.getResponse().getBody(String.class).orElse("Unknown error"));
    } catch (Exception e) {
      throw new QueryEngineException("Unknown error");
    }
  }

  public ChartDataDto submitChart(String appId, EntityChartRequestDto chartRequestDto,
                                  Optional<String> version) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("charts/submit")
        .build();

    MutableHttpRequest<?> request = HttpRequest.POST(uri, chartRequestDto)
        .header("Authorization", "Apikey " + GlobalConfig.getApiToken())
        .contentType(MediaType.APPLICATION_JSON);
    version.ifPresent(v -> request.header("AppConfigVersion", v));

    try {
      return httpClient.retrieve(request, ChartDataDto.class);
    } catch (HttpClientResponseException e) {
      throw new QueryEngineException(e.getResponse().getBody(String.class).orElse("Unknown error"));
    } catch (Exception e) {
      e.printStackTrace();
      throw new QueryEngineException("Unknown error");
    }
  }

  public void submitDbAuth(String appId, DatabaseDto databaseDto) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("datasources-configure")
        .path("db-auth")
        .build();

    HttpRequest<?> request = HttpRequest.POST(uri, databaseDto)
        .header("Authorization", "Apikey " + GlobalConfig.getApiToken())
        .contentType(MediaType.APPLICATION_JSON);

    try {
      httpClient.exchange(request);
    } catch (HttpClientResponseException e) {
      throw new QueryEngineException(e.getResponse().getBody(String.class).orElse("Unknown error"));
    } catch (Exception e) {
      throw new QueryEngineException("Unknown error");
    }
  }

  public void downloadCurrentConfig(String appId, Path configPath) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("datasources/current-config")
        .build();

    MutableHttpRequest<?> request = HttpRequest.GET(uri)
        .header("Authorization", "Apikey " + GlobalConfig.getApiToken())
        .accept(MediaType.APPLICATION_OCTET_STREAM);

    final byte[] response;
    try {
      response = httpClient.retrieve(request, byte[].class);
    } catch (Exception e) {
      throw new QueryEngineException(e.getMessage());
    }

    try (FileOutputStream fos = new FileOutputStream(configPath.toFile())) {
      fos.write(response);
    } catch (HttpClientResponseException e) {
      throw new QueryEngineException(e.getResponse().getBody(String.class).orElse("Unknown error"));
    } catch (Exception e) {
      throw new QueryEngineException("Unknown error");
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
        .header("Authorization", "Apikey " + GlobalConfig.getApiToken())
        .accept(MediaType.APPLICATION_OCTET_STREAM);

    final byte[] response;
    try {
      response = httpClient.retrieve(request, byte[].class);
    } catch (HttpClientResponseException e) {
      throw new QueryEngineException(e.getResponse().getBody(String.class).orElse("Unknown error"));
    } catch (Exception e) {
      throw new QueryEngineException("Unknown error");
    }

    try (FileOutputStream fos = new FileOutputStream(configPath.toFile())) {
      fos.write(response);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void uploadConfig(String appId, Path configPath, Optional<String> version) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("datasources-configure/config")
        .build();

    File file = configPath.toFile();

    MultipartBody body = MultipartBody.builder()
        .addPart("appConfig", file.getName(), MediaType.MULTIPART_FORM_DATA_TYPE, file)
        .build();

    MutableHttpRequest<?> request = HttpRequest.POST(uri, body)
        .header("Authorization", "Apikey " + GlobalConfig.getApiToken())
        .contentType(MediaType.MULTIPART_FORM_DATA_TYPE);
    version.ifPresent(v -> request.header("AppConfigVersion", v));

    try {
      httpClient.exchange(request);
    } catch (HttpClientResponseException e) {
      throw new QueryEngineException(e.getResponse().getBody(String.class).orElse("Unknown error"));
    } catch (Exception e) {
      throw new QueryEngineException("Unknown error");
    }
    System.out.println("Config uploaded successfully");
  }

  public List<BackfillTableStatisticsDto> backfillUserWide(String appId,
                                                           LocalDate dateFrom,
                                                           LocalDate dateTo,
                                                           Optional<String> version) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("datasources/backfill-userwide")
        .path(dateFrom.toString())
        .path(dateTo.toString())
        .build();

    MutableHttpRequest<?> request = HttpRequest.POST(uri, null)
        .header("Authorization", "Apikey " + GlobalConfig.getApiToken())
        .contentType(MediaType.APPLICATION_JSON);
    version.ifPresent(v -> request.header("AppConfigVersion", v));

    try {
      return httpClient.retrieve(request, Argument.listOf(BackfillTableStatisticsDto.class));
    } catch (HttpClientResponseException e) {
      throw new QueryEngineException(e.getResponse().getBody(String.class).orElse("Unknown error"));
    } catch (Exception e) {
      throw new QueryEngineException("Unknown error");
    }
  }

  public Map<LocalDate, BackfillPropertiesStatistics> backfillUserWideStatistics(
      String appId, LocalDate dateFrom, LocalDate dateTo, Optional<String> version) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("datasources/backfill-userwide")
        .path(dateFrom.toString())
        .path(dateTo.toString())
        .build();

    MutableHttpRequest<?> request = HttpRequest.GET(uri)
        .header("Authorization", "Apikey " + GlobalConfig.getApiToken())
        .contentType(MediaType.APPLICATION_JSON);
    version.ifPresent(v -> request.header("AppConfigVersion", v));

    try {
      return httpClient.retrieve(request,
          Argument.mapOf(LocalDate.class, BackfillPropertiesStatistics.class));
    } catch (HttpClientResponseException e) {
      throw new QueryEngineException(e.getResponse().getBody(String.class).orElse("Unknown error"));
    } catch (Exception e) {
      throw new QueryEngineException("Unknown error");
    }
  }

  public List<String> getTables(String appId, String schema) {
    var uri = UriBuilder.of(GlobalConfig.getApiUri())
        .path("api/v1")
        .path(appId)
        .path("schemas/list-tables")
        .path(schema)
        .build();

    HttpRequest<?> request = HttpRequest.GET(uri)
        .header("Authorization", "Apikey " + GlobalConfig.getApiToken());

    try {
      return httpClient.retrieve(request, Argument.listOf(String.class));
    } catch (HttpClientResponseException e) {
      throw new QueryEngineException(e.getResponse().getBody(String.class).orElse("Unknown error"));
    } catch (Exception e) {
      throw new QueryEngineException("Unknown error");
    }
  }
}
