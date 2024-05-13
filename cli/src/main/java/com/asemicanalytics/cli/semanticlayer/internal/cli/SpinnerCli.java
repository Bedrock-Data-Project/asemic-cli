package com.asemicanalytics.cli.semanticlayer.internal.cli;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class SpinnerCli {

  public SpinnerCli() {
  }

  public <T> T spin(Supplier<T> process) {
    AtomicBoolean running = new AtomicBoolean(true);
    Thread t = new Thread(() -> {
      char[] spinner = new char[] {'|', '/', '-', '\\'};
      int i = 0;
      while (running.get()) {
        System.out.print("\r" + spinner[i]);
        i = (i + 1) % spinner.length;
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          break;
        }
      }
    });
    t.start();
    T result = process.get();
    running.set(false);
    try {
      t.join();
      System.out.print("\r");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    return result;
  }

}
