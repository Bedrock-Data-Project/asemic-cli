package com.asemicanalytics.cli.semanticlayer.internal.dsgenerator;

public class StringEditDistance {
  public static int of(String source, String target) {
    int sourceLen = source.length();
    int targetLen = target.length();
    int[][] distance = new int[sourceLen + 1][targetLen + 1];

    for (int i = 0; i <= sourceLen; i++) {
      distance[i][0] = i;
    }

    for (int j = 0; j <= targetLen; j++) {
      distance[0][j] = j;
    }

    for (int i = 1; i <= sourceLen; i++) {
      for (int j = 1; j <= targetLen; j++) {
        int cost = (source.charAt(i - 1) == target.charAt(j - 1)) ? 0 : 1;

        distance[i][j] = Math.min(Math.min(
                distance[i - 1][j] + 1,
                distance[i][j - 1] + 1),
            distance[i - 1][j - 1] + cost);
      }
    }

    return distance[sourceLen][targetLen];
  }
}
