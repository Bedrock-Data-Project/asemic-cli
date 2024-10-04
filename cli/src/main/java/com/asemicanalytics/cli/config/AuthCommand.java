package com.asemicanalytics.cli.config;

import com.asemicanalytics.cli.internal.GlobalConfig;
import com.asemicanalytics.cli.internal.QueryEngineClient;
import com.asemicanalytics.cli.internal.cli.InputCli;
import com.asemicanalytics.cli.internal.cli.MultichoiceCli;
import com.asemicanalytics.cli.internal.cli.PasswordCli;
import com.asemicanalytics.cli.model.DatabaseDto;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import picocli.CommandLine;

@CommandLine.Command(name = "auth", description = "Upload database credentials so asemic can access your data", mixinStandardHelpOptions = true)
public class AuthCommand implements Runnable {

  @Inject
  QueryEngineClient queryEngineClient;

  private DatabaseDto fromBigQuery() {
    String projectId = new InputCli("Enter your google billing project ID").read();
    String serviceAccountPath = new InputCli(
        "Enter path to your service account key "
            + "(Should be generated on google cloud console from a service account)").read();
    try {
      String serviceAccount = Files.readString(Path.of(serviceAccountPath));
      String encodedServiceAccount = Base64.getEncoder()
          .encodeToString(serviceAccount.getBytes(StandardCharsets.UTF_8));

      return new DatabaseDto()
          .databaseType("bigquery")
          .databaseConfig(Map.of(
              "gcp_project_id", projectId,
              "service_account_key", encodedServiceAccount));
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private DatabaseDto fromSnowflake() {
    String user = new InputCli("Enter username").read();
    String password = new PasswordCli("Enter password").read();
    String jdbcUrl = new InputCli("Enter JDBC URL").read();

    return new DatabaseDto()
        .databaseType("snowflake")
        .databaseConfig(Map.of(
            "user", user,
            "password", password,
            "jdbc_url", jdbcUrl));

  }

  @Override
  public void run() {
    String db = new MultichoiceCli("Choose a database type",
        List.of("BigQuery", "Snowflake"))
        .read();
    var databaseDto = switch (db) {
      case "BigQuery" -> fromBigQuery();
      case "Snowflake" -> fromSnowflake();
      default -> throw new IllegalArgumentException("Invalid choice");
    };

    System.out.println("Uploading database credentials...");
    queryEngineClient.submitDbAuth(GlobalConfig.getAppId(), databaseDto);
    System.out.println(CommandLine.Help.Ansi.AUTO.string("@|fg(green) OK|@"));


  }
}
