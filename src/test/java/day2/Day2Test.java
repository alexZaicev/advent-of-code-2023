package day2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    static List<Arguments> getSumSuccess() {
        return List.of(
                Arguments.of(
                        8,
                        List.of(
                                "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
                                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
                        )
                )
        );
    }

    static List<Arguments> getPowerSuccess() {
        return List.of(
                Arguments.of(
                        2286,
                        List.of(
                                "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
                                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getSumSuccess")
    void calculateGameSum_success(int expectedResult, List<String> lines) {
        // arrange
        var arrLines = new String[lines.size()];
        lines.toArray(arrLines);

        // act
        var result = Day2.calculateGameSum(arrLines);

        // assert
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource(value = "getPowerSuccess")
    void calculateGamePower_success(int expectedResult, List<String> lines) {
        // arrange
        var arrLines = new String[lines.size()];
        lines.toArray(arrLines);

        // act
        var result = Day2.calculateGamePower(arrLines);

        // assert
        assertEquals(expectedResult, result);
    }

}