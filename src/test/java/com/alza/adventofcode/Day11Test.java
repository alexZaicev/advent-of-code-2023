package com.alza.adventofcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {

  static List<Arguments> getPart1Success() {
    return List.of(
        Arguments.of(374, """
...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....
""")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getPart1Success")
  void calculateSumOfShortestPaths_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day11.Application(Tools.parseInput(input)).calculateSumOfShortestPaths(2);

    // assert
    assertEquals(expectedResult, result);
  }

}