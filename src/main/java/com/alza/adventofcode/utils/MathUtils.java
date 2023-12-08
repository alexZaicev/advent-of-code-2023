package com.alza.adventofcode.utils;

import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MathUtils {

  private static long gcd(long x, long y) {
    return (y == 0) ? x : gcd(y, x % y);
  }

  public static long gcd(Set<Long> numbers) {
    return numbers.stream().reduce(0L, MathUtils::gcd);
  }

  public static long lcm(Set<Long> numbers) {
    return numbers.stream().reduce(1L, (x, y) -> x * (y / gcd(x, y)));
  }

}
