package com.asemicanalytics.cli.internal;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import java.io.IOException;
import java.util.function.Supplier;

public class RetryableHttpClient implements BlockingHttpClient {

  private final int maxRetries;
  private final BlockingHttpClient baseClient;

  public RetryableHttpClient(int maxRetries, BlockingHttpClient baseClient) {
    this.maxRetries = maxRetries;
    this.baseClient = baseClient;
  }

  @Override
  public void close() throws IOException {
    baseClient.close();
  }

  public static class RetryCommand<T> {

    private int maxRetries;

    RetryCommand(int maxRetries) {
      this.maxRetries = maxRetries;
    }

    public T run(Supplier<T> function) {
      try {
        return function.get();
      } catch (RuntimeException e) {
        return retry(function);
      }
    }

    private T retry(Supplier<T> function) {

      int retryCounter = 0;
      while (retryCounter < maxRetries) {
        try {
          return function.get();
        } catch (RuntimeException ex) {
          if (ex instanceof HttpClientResponseException httpEx) {
            if (httpEx.getStatus().getCode() < 500) {
              throw ex;
            }
          }
          try {
            Thread.sleep(10000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
          retryCounter++;
          if (retryCounter == maxRetries) {
            throw ex;
          }
        }
      }
      throw new IllegalStateException();
    }
  }

  @Override
  public <I> String retrieve(HttpRequest<I> request) {
    return new RetryCommand<String>(maxRetries).run(() -> baseClient.retrieve(request));
  }

  @Override
  public String retrieve(String uri) {
    return new RetryCommand<String>(maxRetries).run(() -> baseClient.retrieve(uri));
  }

  @Override
  public <I, O, E> HttpResponse<O> exchange(HttpRequest<I> request, Argument<O> bodyType,
                                            Argument<E> errorType) {
    return new RetryCommand<HttpResponse<O>>(maxRetries).run(
        () -> baseClient.exchange(request, bodyType, errorType));
  }

  @Override
  public <O> HttpResponse<O> exchange(String uri, Class<O> bodyType) {
    return new RetryCommand<HttpResponse<O>>(maxRetries).run(
        () -> baseClient.exchange(uri, bodyType));
  }
}
