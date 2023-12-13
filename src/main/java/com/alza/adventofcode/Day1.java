package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Day1 {

  static final String INPUT_FILE = "day_1/numbers.txt";

  @SneakyThrows
  public static void main(String[] args) {
    var app = new Application(FileUtils.readResourceFile(INPUT_FILE));
    log.info("Calibration sum: %d".formatted(app.calculateSum()));
    log.info("Calibration with words sum: %d".formatted(app.calculateSumWithWords()));
  }

  @RequiredArgsConstructor
  public static class Application {

    private static final String[] numWords = new String[] {
        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    };
    private final List<String> lines;

    public int calculateSum() {
      int sum = 0;

      for (String line : lines) {
        StringBuilder sb = new StringBuilder();

        for (Character ch : line.strip().toCharArray()) {
          if (Character.isDigit(ch)) {
            sb.append(ch);
          }
        }

        sum += getNumberToAdd(sb);
      }

      return sum;
    }

    public int calculateSumWithWords() {
      int sum = 0;
      for (String line : lines) {
        StringBuilder sb = new StringBuilder();

        for (String chunk : splitIntoChunks(line.strip())) {
          var result = new Result(0, chunk);
          while (result.value() > -1) {
            result = getWordNumber(result.chunk());
            if (result.value() > -1) {
              sb.append(result.value());
            }
          }

          try {
            sb.append(Integer.parseInt(result.chunk()));
          } catch (NumberFormatException ex) {
            // ignore
          }
        }

        sum += getNumberToAdd(sb);
      }

      return sum;
    }

    private List<String> splitIntoChunks(String line) {
      List<String> chunks = new ArrayList<>();
      boolean isLastDigit = false;

      for (char ch : line.toCharArray()) {
        if (Character.isDigit(ch)) {
          if (isLastDigit) {
            var idx = chunks.size() - 1;
            chunks.set(idx, chunks.get(idx) + ch);
          } else {
            chunks.add(Character.toString(ch));
          }

          isLastDigit = true;
          continue;
        }

        if (isLastDigit || chunks.isEmpty()) {
          chunks.add(Character.toString(ch));
        } else {
          var idx = chunks.size() - 1;
          chunks.set(idx, chunks.get(idx) + ch);
        }

        isLastDigit = false;
      }

      return chunks;
    }

    private Result getWordNumber(String s) {
      StringBuilder sb = new StringBuilder();
      int idx = 0;
      for (Character ch : s.toCharArray()) {
        var ss = sb.append(ch).toString();

        for (int i = 0; i < numWords.length; ++i) {
          if (ss.toLowerCase().contains(numWords[i].toLowerCase())) {
            return new Result(i + 1, s.substring(idx + 1));
          }
        }

        ++idx;
      }

      return new Result(-1, s);
    }

    private int getNumberToAdd(StringBuilder sb) {
      if (sb.isEmpty()) {
        return 0;
      }

      return sb.length() == 1
          ? Integer.parseInt("%c%c".formatted(sb.charAt(0), sb.charAt(0)))
          : Integer.parseInt("%c%c".formatted(sb.charAt(0), sb.charAt(sb.length() - 1)));
    }

    record Result(int value, String chunk) {
    }
  }
}
