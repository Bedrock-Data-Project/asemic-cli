package com.asemicanalytics.cli.internal;

import java.nio.file.Path;
import java.util.Optional;

public class GlobalConfig {
  public static String getApiUri() {
    var apiUri = System.getenv("ASEMIC_API_URL");
    if (apiUri != null) {
      return apiUri;
    }
    return "https://api.asemicanalytics.com";
  }

  /**
   * The web app (control plane) base URL, where a personal access token is
   * exchanged for a short-lived engine JWT. Only used when ASEMIC_PAT is set.
   */
  public static String getWebUri() {
    var webUri = System.getenv("ASEMIC_WEB_URL");
    if (webUri != null) {
      return webUri;
    }
    return "https://app.asemicanalytics.com";
  }

  /**
   * A personal access token (preferred): exchanged for a short-lived,
   * configure-scoped engine JWT. When unset, the CLI falls back to the legacy
   * per-project {@code ASEMIC_API_TOKEN} (view-only {@code Apikey}).
   */
  public static Optional<String> getPat() {
    return Optional.ofNullable(System.getenv("ASEMIC_PAT"));
  }

  public static String getApiToken() {
    var apiToken = System.getenv("ASEMIC_API_TOKEN");
    if (apiToken != null) {
      return apiToken;
    }
    throw new IllegalStateException(
        "API token not found. Define ASEMIC_API_TOKEN environment variable");
  }

  public static Path getAppIdDir() {
    var appIdDir = System.getenv("ASEMIC_APP_ID_DIR");
    if (appIdDir != null) {
      return Path.of(appIdDir);
    }

    return Path.of(System.getProperty("user.dir"));
  }


  public static String getAppId() {
    return getAppIdDir().getFileName().toString();
  }
}
