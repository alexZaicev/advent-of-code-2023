package com.alza.adventofcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day13Test {

  static List<Arguments> getPart1Success() {
    return List.of(
        Arguments.of(405, """
#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#
""")
    );
  }

  static List<Arguments> getPart2Success() {
    return List.of(
        Arguments.of(400, """
#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#
""")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getPart1Success")
  void calculateFolderArrangements_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day13.Application(Tools.parseInput(input, true)).calculateSymmetrySum();

    // assert
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @MethodSource(value = "getPart2Success")
  void calculateArrangements_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day13.Application(Tools.parseInput(input, true)).calculateSymmetrySumWithSmudges();

    // assert
    assertEquals(expectedResult, result);
  }

}