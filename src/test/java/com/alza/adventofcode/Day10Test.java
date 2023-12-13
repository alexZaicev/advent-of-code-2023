package com.alza.adventofcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {

  static List<Arguments> getPart1Success() {
    return List.of(
        Arguments.of(4, """
.....
.S-7.
.|.|.
.L-J.
.....
""")
    );
  }

  static List<Arguments> getPart2Success() {
    return List.of(
            Arguments.of(4, """
...........
.S-------7.
.|F-----7|.
.||.....||.
.||.....||.
.|L-7.F-J|.
.|..|.|..|.
.L--J.L--J.
...........
"""),
            Arguments.of(8, """
.F----7F7F7F7F-7....
.|F--7||||||||FJ....
.||.FJ||||||||L7....
FJL7L7LJLJ||LJ.L-7..
L--J.L7...LJS7F-7L7.
....F-J..F7FJ|L7L7L7
....L7.F7||L7|.L7L7|
.....|FJLJ|FJ|F7|.LJ
....FJL-7.||.||||...
....L---J.LJ.LJLJ...
""")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getPart1Success")
  void findTheLastPipe_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day10.Application(Tools.parseInput(input)).findChainLength();

    // assert
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @MethodSource(value = "getPart2Success")
  void findEnclosedTiles_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day10.Application(Tools.parseInput(input)).findEnclosedTiles();

    // assert
    assertEquals(expectedResult, result);
  }

}