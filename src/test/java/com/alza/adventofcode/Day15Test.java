package com.alza.adventofcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day15Test {

  static List<Arguments> getPart1Success() {
    return List.of(
        Arguments.of(52, "HASH"),
        Arguments.of(253, "cm-"),
        Arguments.of(97, "qp=3")
    );
  }

  static List<Arguments> getPart2Success() {
    return List.of(
        Arguments.of(145, "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getPart1Success")
  void calculateFolderArrangements_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day15.Application(Tools.parseInput(input, true)).getHashSum();

    // assert
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @MethodSource(value = "getPart2Success")
  void calculateArrangements_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day15.Application(Tools.parseInput(input, true)).getFocusingPowerSum();

    // assert
    assertEquals(expectedResult, result);
  }

}