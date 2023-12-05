package com.alza.adventofcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {

  static List<Arguments> getScratchCardSuccess() {
    return List.of(
        Arguments.of(46, """
                seeds: 79 14 55 13

                seed-to-soil map:
                50 98 2
                52 50 48

                soil-to-fertilizer map:
                0 15 37
                37 52 2
                39 0 15

                fertilizer-to-water map:
                49 53 8
                0 11 42
                42 0 7
                57 7 4

                water-to-light map:
                88 18 7
                18 25 70

                light-to-temperature map:
                45 77 23
                81 45 19
                68 64 13

                temperature-to-humidity map:
                0 69 1
                1 0 69

                humidity-to-location map:
                60 56 37
                56 93 4
""")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getScratchCardSuccess")
  void calculateWinningPoints_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day5.Application(input.split("\n")).findLowestLocation();

    // assert
    assertEquals(expectedResult, result);
  }

}