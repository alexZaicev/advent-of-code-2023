package day3;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import utils.FileUtils;

import java.util.*;
import java.util.regex.Pattern;

@Log4j2
public class Day3 {

    static final String INPUT_FILE = "day_3/schematics.txt";
    static final char GEAR_SYMBOL = '*';

    @SneakyThrows
    public static void main(String[] args) {
        var lines = FileUtils.readResourceFile(INPUT_FILE);
        log.info("Part sum: %d".formatted(calculatePartSum(lines)));
        log.info("Gear ratio: %d".formatted(calculateGearRatio(lines)));
    }

    static int calculatePartSum(String... lines) {
        int sum = 0;

        for (int idx = 0; idx < lines.length; ++idx) {
            var line = lines[idx].strip();

            Set<Integer> previousIdx = idx == 0
                    ? Set.of()
                    : getSymbolIndexes(lines[idx - 1]);
            Set<Integer> currentIdx = getSymbolIndexes(line);
            Set<Integer> nextIdx = idx == lines.length - 1
                    ? Set.of()
                    : getSymbolIndexes(lines[idx + 1]);

            for (Number nm : getNumbersFromLine(line)) {
                if (nm.isAdjacentNumber(currentIdx)
                        || nm.isAdjacentNumber(previousIdx)
                        || nm.isAdjacentNumber(nextIdx)) {
                    sum += nm.value();
                }
            }
        }

        return sum;
    }

    static int calculateGearRatio(String... lines) {
        int sum = 0;

        for (int idx = 0; idx < lines.length; ++idx) {
            var line = lines[idx].strip();

            if (!line.contains("*")) {
                continue;
            }
            Set<Integer> indexes = getSymbolIndexes(line, GEAR_SYMBOL);

            List<Number> previous = idx == 0
                    ? List.of()
                    : getNumbersFromLine(lines[idx - 1].strip());
            List<Number> current = getNumbersFromLine(line);
            List<Number> next = idx == lines.length - 1
                    ? List.of()
                    : getNumbersFromLine(lines[idx + 1].strip());

            previous = previous.stream().filter(nm -> nm.isAdjacentNumber(indexes)).toList();
            current = current.stream().filter(nm -> nm.isAdjacentNumber(indexes)).toList();
            next = next.stream().filter(nm -> nm.isAdjacentNumber(indexes)).toList();

            for (int symbolIdx : indexes) {
                var numbers = getSurroundingNumbers(symbolIdx, previous, current, next);
                if (numbers.size() != 2) {
                    continue;
                }
                sum += numbers.get(0).value() * numbers.get(1).value();
            }
        }

        return sum;
    }

    private static List<Number> getSurroundingNumbers(int idx, List<Number>... numberLists) {
        List<Number> result = new ArrayList<>();

        Arrays.asList(numberLists).forEach(numbers -> {
            for (Number nm : numbers) {
                if (nm.isAdjacentNumber(idx)) {
                    result.add(nm);
                }
            }
        });

        return result;
    }

    private static Set<Integer> getSymbolIndexes(String line) {
        Set<Integer> indexes = new TreeSet<>();
        char[] chars = line.toCharArray();

        for (int idx = 0; idx < chars.length; ++idx) {
            char ch = chars[idx];
            if (Character.isLetterOrDigit(ch) || ch == '.') {
                continue;
            }
            indexes.add(idx);
        }

        return indexes;
    }

    private static Set<Integer> getSymbolIndexes(String line, Character symbol) {
        Set<Integer> indexes = new TreeSet<>();
        char[] chars = line.toCharArray();

        for (int idx = 0; idx < chars.length; ++idx) {
            char ch = chars[idx];
            if (Character.isLetterOrDigit(ch) || ch == '.') {
                continue;
            }

            if (ch == symbol) {
                indexes.add(idx);
            }
        }

        return indexes;
    }

    private static List<Number> getNumbersFromLine(String line) {
        List<Number> numbers = new ArrayList<>();

        var matcher = Pattern.compile("([0-9]+)").matcher(line);
        while (matcher.find()) {
            numbers.add(new Number(Integer.parseInt(matcher.group()), matcher.start(), matcher.end()));
        }

        return numbers;
    }

    record Number(int value, int start, int end) {

        boolean isAdjacentNumber(Set<Integer> indexes) {
            for (int i : indexes) {
                if (isAdjacentNumber(i)) {
                    return true;
                }
            }
            return false;
        }

        boolean isAdjacentNumber(int idx) {
            return idx >= this.start - 1 && idx <= this.end;
        }

    }

}