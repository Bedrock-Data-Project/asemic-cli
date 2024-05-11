package com.asemicanalytics.cli.semanticlayer.internal.cli;

import java.util.List;
import java.util.Optional;

public class MultichoiceCli {
  private final String header;
  private final List<String> choices;

  public MultichoiceCli(String header, List<String> choices) {
    this.header = header;
    this.choices = choices;
  }

  public String read() {
    System.out.println(header);
    for (int i = 0; i < choices.size(); i++) {
      System.out.println(" [" + (i + 1) + "] " + choices.get(i));
    }

    String choice = new InputCli(
        "Enter a number between 1 and " + choices.size() + ": ",
        Optional.empty(), this::validateInput).read();

    return choices.get(Integer.parseInt(choice) - 1);
  }

  private Boolean validateInput(String input) {
    try {
      int i = Integer.parseInt(input);
      if (i < 1 || i > choices.size()) {
        System.out.println(
            "Invalid input. Please enter a number between 1 and " + choices.size() + ".");
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      System.out.println(
          "Invalid input. Please enter a number between 1 and " + choices.size() + ".");
      return false;
    }
  }
}
