package com.alza.adventofcode;

import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Tools {

  public static List<String> parseInput(String s) {
    return Arrays.stream(s.split("\n")).filter(ss -> !ss.isBlank()).toList();
  }

}
