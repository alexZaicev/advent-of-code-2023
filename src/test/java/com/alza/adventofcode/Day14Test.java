package com.alza.adventofcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day14Test {

  static List<Arguments> getPart1Success() {
    return List.of(
        Arguments.of(136, """
O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..Oß
.......O..
#....###..
#OO..#....
""")
    );
  }

  static List<Arguments> getPart2Success() {
    return List.of(
        Arguments.of(64, """
O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#....
""")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getPart1Success")
  void calculateFolderArrangements_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day14.Application(Tools.parseInput(input, true)).calculateNorthLoad();

    // assert
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @MethodSource(value = "getPart2Success")
  void calculateArrangements_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day14.Application(Tools.parseInput(input, true)).calculateNorthLoadAfterCycle(1000000000);

    // assert
    assertEquals(expectedResult, result);
  }

}