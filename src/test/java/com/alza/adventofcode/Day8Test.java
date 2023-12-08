package com.alza.adventofcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day8Test {

  static List<Arguments> getPart1Success() {
    return List.of(
        Arguments.of(2, """
RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)
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
    var result = new Day8.Application(Tools.parseInput(input)).calculateDirections();

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