package com.asemicanalytics.cli.internal.cli;

import java.util.Optional;
import java.util.function.Function;

public class InputCli {
  private final String prompt;
  private final Optional<String> defaultValue;
  private final Function<String, Boolean> validator;

  public InputCli(String prompt, Optional<String> defaultValue,
                  Function<String, Boolean> validator) {
    this.prompt = prompt;
    this.defaultValue = defaultValue;
    this.validator = validator;
  }

  public InputCli(String prompt) {
    this(prompt, Optional.empty(), s -> true);
  }

  public InputCli(String prompt, String defaultValue) {
    this(prompt, Optional.of(defaultValue), s -> true);
  }

  public String read() {
    String renderedPrompt = prompt;
    if (defaultValue.isPresent()) {
      renderedPrompt += " [Leave empty for " + defaultValue.get() + "]";
    }
    renderedPrompt += ": ";

    for (int i = 0; i < 3; i++) {
      String input = System.console().readLine(renderedPrompt);
      if (input.isBlank() && defaultValue.isPresent()) {
        return defaultValue.get();
      }
      if (validator.apply(input)) {
        return input;
      }
    }

    throw new IllegalArgumentException("Could not get valid input after 3 attempts. Exiting.");
  }

}
