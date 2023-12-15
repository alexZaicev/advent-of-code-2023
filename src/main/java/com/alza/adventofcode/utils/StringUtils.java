package com.alza.adventofcode.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

  public static int count(String s, char ch) {
    int count = 0;
    for (var sch : s.toCharArray()) {
      if (sch == ch) {
        count++;
      }
    }
    return count;
  }

}
