package com.alza.adventofcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day12Test {

  static List<Arguments> getPart1Success() {
    return List.of(
        Arguments.of(21, """
???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1
""")
    );
  }

  static List<Arguments> getPart2Success() {
    return List.of(
        Arguments.of(525152, """
???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1
""")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getPart1Success")
  void calculateFolderArrangements_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day12.Application(Tools.parseInput(input)).calculateFolderArrangements();

    // assert
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @MethodSource(value = "getPart2Success")
  void calculateArrangements_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day12.Application(Tools.parseInput(input)).calculateArrangements();

    // assert
    assertEquals(expectedResult, result);
  }

}