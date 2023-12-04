package com.alza.adventofcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day4Test {

  static List<Arguments> getScratchCardSuccess() {
    return List.of(
        Arguments.of(13, """
            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"""),
        Arguments.of(1024, "Card   1:  9 32  7 82 10 36 31 12 85 95 |  7 69 23  9 32 22 47 10 95 14 24 71 57 12 31 59 36 68  2 82 38 80 85 21 92\n" +
            "Card   2: 16 35 95 22 59 82 76 60 19 88 | 63 91 16 35 26 82 95 51 53 60 94 59 56 73 28 76 12 44 22 62  8  7 19 38 88"),
        Arguments.of(66, "Card  80: 91 81 96 55 44 82 31 23 11 74 | 24 51 96 77 40 28 56 44 54 89 78 38 76 74 17 92  3 23 36 63 80 65 55  7 11\n" +
            "Card  81: 81 77 30 26 93 28 97 10 84 88 | 57 28 36 33 54 11 96 58 18 99 30  1  5 79 12 24 56 93 25 78 10 40 76 84 81\n" +
            "Card  82: 31 67  3 90 28 76 55  6 29 26 | 75 34 82 73 38 17 67 91 86 40 43 45 42 60 37 63 55 87 93 84 58 78 80 20 11")
    );
  }

  static List<Arguments> getScratchCardCopiesSuccess() {
    return List.of(
        Arguments.of(30, """
            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11""")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getScratchCardSuccess")
  void calculateWinningPoints_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day4.Application(input.split("\n")).calculateWinningPoints();

    // assert
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @MethodSource(value = "getScratchCardCopiesSuccess")
  void calculateNumberOfScratchcard_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day4.Application(input.split("\n")).calculateNumberOfScratchcard();

    // assert
    assertEquals(expectedResult, result);
  }

}