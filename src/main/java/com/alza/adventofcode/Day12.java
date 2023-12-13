package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Day12 {

  static final String INPUT_FILE = "day_12/input.txt";

  @SneakyThrows
  public static void main(String[] args) {
    var app = new Application(FileUtils.readResourceFile(INPUT_FILE));
    log.info("Result sum: %d".formatted(app.calculateFolderArrangements()));
    log.info("Result sum: %d".formatted(app.calculateArrangements()));
  }

  @RequiredArgsConstructor
  public static class Application {

    private static final Map<String, Long> cache = new HashMap<>();
    private final List<String> lines;

    public long calculateFolderArrangements() {
      return lines.stream()
          .map(Config::of)
          .map(c -> getArrangement(c.data(), c.combos(), false))
          .reduce(0L, Long::sum);
    }

    public long calculateArrangements() {
      return lines.stream()
          .map(Config::of)
          .map(Config::unfold)
          .map(c -> getArrangementCached(c.data(), c.combos(), false))
          .reduce(0L, Long::sum);
    }

    private long getArrangement(String data, List<Integer> combos, boolean on) {
      long sum = combos.stream().reduce(0, Integer::sum);

      if (data.isEmpty()) {
        return sum == 0 ? 1 : 0;
      }

      if (sum == 0) {
        return data.contains("#") ? 0 : 1;
      }

      if (data.charAt(0) == '#') {
        if (on && combos.get(0) == 0) {
          return 0;
        }
        return getArrangementCached(data.substring(1), reduceFirst(combos), true);
      }

      if (data.charAt(0) == '.') {
        if (on && combos.get(0) > 0) {
          return 0;
        }

        return getArrangementCached(
            data.substring(1),
            combos.get(0) == 0 ? combos.subList(1, combos.size()) : combos,
            false
        );
      }

      if (on) {
        if (combos.get(0) == 0) {
          return getArrangementCached(data.substring(1), combos.subList(1, combos.size()), false);
        }
        return getArrangementCached(data.substring(1), reduceFirst(combos), true);
      }

      return getArrangementCached(data.substring(1), combos, false) +
          getArrangementCached(data.substring(1), reduceFirst(combos), true);
    }

    private long getArrangementCached(String data, List<Integer> combos, boolean on) {
      String key = "%s|%s|%s".formatted(
          data,
          String.join(",", combos.stream().map(i -> Integer.toString(i)).toList()),
          on
      );

      if (!cache.containsKey(key)) {
        cache.put(key, getArrangement(data, combos, on));
      }
      return cache.get(key);
    }

    private List<Integer> reduceFirst(List<Integer> combos) {
      var newCombo = new ArrayList<>(combos);
      if (newCombo.get(0) > 0) {
        newCombo.set(0, newCombo.get(0) - 1);
      }
      return List.copyOf(newCombo);
    }

    record Config(String data, List<Integer> combos) {
      private static final int UNFOLDED_REPEATS = 5;

      public static Config of(String line) {
        var tokens = line.split(" ");
        var combos = Arrays.stream(tokens[1].split(","))
            .map(Integer::parseInt)
            .toList();

        return new Config(tokens[0], combos);
      }

      public static Config unfold(Config config) {
        List<String> unfoldedData = new ArrayList<>();
        List<Integer> unfoldedCombos = new ArrayList<>();

        for (int i = 0; i < UNFOLDED_REPEATS; ++i) {
          unfoldedData.add(config.data());
          unfoldedCombos.addAll(config.combos());
        }

        return new Config(String.join("?", unfoldedData), unfoldedCombos);
      }
    }
  }

}
