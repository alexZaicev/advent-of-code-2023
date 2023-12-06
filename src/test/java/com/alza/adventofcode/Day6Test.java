package com.alza.adventofcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day6Test {

  static List<Arguments> getRecordSuccess() {
    return List.of(
        Arguments.of(288, """
Time:      7  15   30
Distance:  9  40  200
"""),
        Arguments.of(71503, """
Time:      71530
Distance:  940200
""")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getRecordSuccess")
  void calculateNumberOfRecordWays_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day6.Application(Tools.parseInput(input)).calculateNumberOfRecordWays();

    // assert
    assertEquals(expectedResult, result);
  }

}