package com.alza.adventofcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day1Test {

  static List<Arguments> getInput() {
    return List.of(
        Arguments.of(142, List.of("1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet"))
    );
  }

  static List<Arguments> getInputWithWords() {
    return List.of(
        Arguments.of(281, List.of(
            "two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen"
        ))
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getInput")
  void shouldCalculateSum(int expectedSum, List<String> lines) {
    // arrange
    // act
    var sum = new Day1.Application(lines).calculateSum();
    // assert
    assertEquals(expectedSum, sum);
  }

  @ParameterizedTest
  @MethodSource(value = "getInputWithWords")
  void shouldCalculateSum2(int expectedSum, List<String> lines) {
    // arrange
    // act
    var sum = new Day1.Application(lines).calculateSumWithWords();
    // assert
    assertEquals(expectedSum, sum);
  }
}