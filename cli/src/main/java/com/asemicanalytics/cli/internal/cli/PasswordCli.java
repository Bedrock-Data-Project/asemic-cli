package com.asemicanalytics.cli.internal.cli;

public class PasswordCli {
  private final String prompt;

  public PasswordCli(String prompt) {
    this.prompt = prompt;
  }

  public String read() {
    return new String(System.console().readPassword(prompt + ": "));
  }

}
