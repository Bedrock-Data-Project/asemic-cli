package com.asemicanalytics.cli.internal;

import java.nio.file.Path;

public class GlobalConfig {
  public static String getApiUri() {
    var apiUri = System.getenv("ASEMIC_API_URL");
    if (apiUri != null) {
      return apiUri;
    }
    return "https://api.asemicanalytics.com";
  }

  /**
   * The web app (control plane) base URL, where the personal access token is
   * exchanged for a short-lived engine JWT.
   */
  public static String getWebUri() {
    var webUri = System.getenv("ASEMIC_WEB_URL");
    if (webUri != null) {
      return webUri;
    }
    return "https://app.asemicanalytics.com";
  }

  /**
   * The personal access token, exchanged for a short-lived, configure-scoped
   * engine JWT. Required — the legacy per-project {@code ASEMIC_API_TOKEN}
   * ({@code Apikey}) mechanism was removed from the engine.
   */
  public static String getPat() {
    var pat = System.getenv("ASEMIC_PAT");
    if (pat != null) {
      return pat;
    }
    throw new IllegalStateException(
        "Personal access token not found. Define the ASEMIC_PAT environment variable");
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
