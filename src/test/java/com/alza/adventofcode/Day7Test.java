package com.alza.adventofcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day7Test {

  static List<Arguments> getBidsSuccess() {
    return List.of(
        Arguments.of(6440, """
32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483
"""),
        Arguments.of(24696, """
K99QT 53
TKQ7T 320
22A7J 490
267J9 69
665JJ 431
K856J 605
977K9 552
KKK8K 285
53697 370
3J337 879
""")
    );
  }

  static List<Arguments> getBidsWithJoker() {
    return List.of(
        Arguments.of(5905, """
32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483
"""),
        Arguments.of(24656, """
K99QT 53
TKQ7T 320
22A7J 490
267J9 69
665JJ 431
K856J 605
977K9 552
KKK8K 285
53697 370
3J337 879
"""),
        Arguments.of(21270, """
6T5J5 910
TQ722 976
9A9QQ 224
J93J5 948
JTTT7 576
JKAQK 184
AAAA5 764
J3939 262
"""),
        Arguments.of(16584, """
K2J53 537
JQQ22 470
AJA53 88
73797 672
657TT 869
2TTTT 142
JT49K 227
A7A77 338
5J2Q8 221
""")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getBidsSuccess")
  void calculateBids_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day7.Application(Tools.parseInput(input)).calculateBids();

    // assert
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @MethodSource(value = "getBidsWithJoker")
  void calculateBidsWithJoker_success(int expectedResult, String input) {
    // arrange
    // act
    var result = new Day7.Application(Tools.parseInput(input)).calculateBidsWithJoker();

    // assert
    assertEquals(expectedResult, result);
  }

}