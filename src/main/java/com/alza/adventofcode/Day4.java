package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Day4 {

  static final String INPUT_FILE = "day_4/scratchcards.txt";

  @SneakyThrows
  public static void main(String[] args) {
    var app = new Application(FileUtils.readResourceFile(INPUT_FILE));
    log.info("Part sum: %d".formatted(app.calculateWinningPoints()));
    log.info("Scratchcard sum: %d".formatted(app.calculateNumberOfScratchcard()));
  }

  @RequiredArgsConstructor
  public static class Application {

    private final List<String> lines;

    public int calculateWinningPoints() {
      int sum = 0;

      for (var line : lines) {
        var scratchcard = getNumbers(line);
        sum += scratchcard.getPoints();
      }

      return sum;
    }

    public int calculateNumberOfScratchcard() {
      int sum = 0;

      Map<Integer, Integer> copies = new HashMap<>();

      for (int i = 0; i < lines.size(); ++i) {
        copies.put(i + 1, 1);
      }

      var idx = 1;
      for (var line : lines) {
        var scratchcard = getNumbers(line);
        var copiesWon = scratchcard.getMatchingCount();

        for (int j = 0; j < copies.get(idx); ++j) {
          for (int i = idx + 1; i <= idx + copiesWon; ++i) {
            copies.put(i, copies.get(i) + 1);
          }
        }

        idx += 1;
      }

      for (var entry : copies.entrySet()) {
        if (entry.getKey() <= lines.size()) {
          sum += entry.getValue();
        }
      }

      return sum;
    }

    private Scratchcard getNumbers(String line) {
      var tokens = line.split("\\|");

      var winning = extractNumbers(tokens[0].split(":")[1]);
      var hand = extractNumbers(tokens[1]);

      return new Scratchcard(winning, hand);
    }

    private List<Integer> extractNumbers(String s) {
      List<Integer> result = new ArrayList<>();

      var matcher = Pattern.compile("(\\d+)").matcher(s);
      while (matcher.find()) {
        result.add(Integer.parseInt(matcher.group().strip()));
      }

      return result;
    }

    record Scratchcard(List<Integer> winning, List<Integer> hand) {

      int getMatchingCount() {
        int count = 0;
        for (var nm : this.hand) {
          if (this.winning.contains(nm)) {
            ++count;
          }
        }
        return count;
      }

      int getPoints() {
        int points = 0;
        for (var nm : this.hand) {
          if (this.winning.contains(nm)) {
            if (points == 0) {
              points += 1;
            } else {
              points *= 2;
            }
          }
        }
        return points;
      }
    }
  }
}
