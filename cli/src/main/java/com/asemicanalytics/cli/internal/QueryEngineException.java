package com.asemicanalytics.cli.internal;

public class QueryEngineException extends RuntimeException {
  public QueryEngineException(String message) {
    super(message);
  }

  public QueryEngineException(String message, Throwable cause) {
    super(message, cause);
  }
}
