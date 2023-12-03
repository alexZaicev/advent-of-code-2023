package day3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {

    static List<Arguments> getSumSuccess() {
        return List.of(
                Arguments.of(4361, "467..114..\n" +
                        "...*......\n" +
                        "..35..633.\n" +
                        "......#...\n" +
                        "617*......\n" +
                        ".....+.58.\n" +
                        "..592.....\n" +
                        "......755.\n" +
                        "...$.*....\n" +
                        ".664.598.."),
                Arguments.of(7182, "............................................411..................\n" +
                        "......429...836..$............../..960........*.............+....\n" +
                        ".........*...&...641..........924..*.........855....492..495.....\n" +
                        ".........900......................239.325..............*........."),
                Arguments.of(12283, "..232......&.676...738....#......839....876.45........866...555...664......+..68.......941.........51*585..............937.......*...917*691\n" +
                        ".........@........*.......8...31*...........+.....577*.........*....*...399....*.=....@......./...................................59........\n" +
                        ".......740.......781..........................................105.353........791.579...........900.463..............909.....................")
        );
    }

    static List<Arguments> getRatioSuccess() {
        return List.of(
                Arguments.of(467835, "467..114..\n" +
                        "...*......\n" +
                        "..35..633.\n" +
                        "......#...\n" +
                        "617*......\n" +
                        ".....+.58.\n" +
                        "..592.....\n" +
                        "......755.\n" +
                        "...$.*....\n" +
                        ".664.598..")
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getSumSuccess")
    void calculateSum_success(int expectedResult, String input) {
        // arrange
        // act
        var result = Day3.calculatePartSum(input.split("\n"));

        // assert
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource(value = "getRatioSuccess")
    void calculateGearRatio_success(int expectedResult, String input) {
        // arrange
        // act
        var result = Day3.calculateGearRatio(input.split("\n"));

        // assert
        assertEquals(expectedResult, result);
    }

}