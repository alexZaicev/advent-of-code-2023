package com.alza.adventofcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {

  static List<Arguments> getPart1Success() {
    return List.of(
        Arguments.of(114, """
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45
"""),
            Arguments.of(5040820, """
10 16 22 28 34 40 46 52 58 64 70 76 82 88 94 100 106 112 118 124 130
9 19 41 88 184 362 655 1088 1698 2633 4409 8432 17912 39299 84354 172945 336675 623603 1104781 1884372 3117140
"""),
            Arguments.of(54398750, """
8 10 9 -2 -17 8 190 771 2208 5345 11753 24409 49063 97014 190779 375658 743112 1477240 2945141 5868120 11637263
-9 -16 -26 -42 -67 -89 -41 278 1345 4163 10710 24787 53710 111805 227699 459451 925644 1868703 3782952 7672020 15562542
""")
    );
  }

  static List<Arguments> getPart2Success() {
    return List.of(
        Arguments.of(6, """
LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)
""")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getPart1Success")
  void calculateDirections_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day9.Application(Tools.parseInput(input)).calculateExtrapolatesOnEnd();

    // assert
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @MethodSource(value = "getPart2Success")
  void calculateDirectionGhosts_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day8.Application(Tools.parseInput(input)).calculateDirectionsGhosts();

    // assert
    assertEquals(expectedResult, result);
  }

}