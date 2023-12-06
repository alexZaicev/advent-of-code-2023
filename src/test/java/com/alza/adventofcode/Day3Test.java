package com.alza.adventofcode;

import java.util.Arrays;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {

    static List<Arguments> getSumSuccess() {
        return List.of(
                Arguments.of(4361, """
                    467..114..
                    ...*......
                    ..35..633.
                    ......#...
                    617*......
                    .....+.58.
                    ..592.....
                    ......755.
                    ...$.*....
                    .664.598.."""),
                Arguments.of(7182, """
                    ............................................411..................
                    ......429...836..$............../..960........*.............+....
                    .........*...&...641..........924..*.........855....492..495.....
                    .........900......................239.325..............*........."""),
                Arguments.of(12283, """
                    ..232......&.676...738....#......839....876.45........866...555...664......+..68.......941.........51*585..............937.......*...917*691
                    .........@........*.......8...31*...........+.....577*.........*....*...399....*.=....@......./...................................59........
                    .......740.......781..........................................105.353........791.579...........900.463..............909.....................""")
        );
    }

    static List<Arguments> getRatioSuccess() {
        return List.of(
                Arguments.of(467835, """
                    467..114..
                    ...*......
                    ..35..633.
                    ......#...
                    617*......
                    .....+.58.
                    ..592.....
                    ......755.
                    ...$.*....
                    .664.598..""")
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getSumSuccess")
    void calculateSum_success(int expectedResult, String input) {
        // arrange
        // act
        var result = new Day3.Application(Tools.parseInput(input)).calculatePartSum();

        // assert
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource(value = "getRatioSuccess")
    void calculateGearRatio_success(int expectedResult, String input) {
        // arrange
        // act
        var result = new Day3.Application(Tools.parseInput(input)).calculateGearRatio();

        // assert
        assertEquals(expectedResult, result);
    }

}