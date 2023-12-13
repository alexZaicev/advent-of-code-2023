package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Day9 {

  static final String INPUT_FILE = "day_9/input.txt";

  @SneakyThrows
  public static void main(String[] args) {
    var app = new Application(FileUtils.readResourceFile(INPUT_FILE));
    log.info("Extrapolate sum on ends: %d".formatted(app.calculateExtrapolatesOnEnd()));
    log.info("Extrapolate sum on start: %d".formatted(app.calculateExtrapolatesOnStart()));
  }

  @RequiredArgsConstructor
  public static class Application {

    private final List<String> lines;

    public int calculateExtrapolatesOnEnd() {
      var sum = 0;
      for (var seq : getSequences()) {
        sum += extrapolate(seq, true);
      }
      return sum;
    }

    public int calculateExtrapolatesOnStart() {
      var sum = 0;
      for (var seq : getSequences()) {
        sum += extrapolate(seq, false);
      }
      return sum;
    }

    private int extrapolate(List<Integer> values, boolean isEnd) {
      if (isAllZeros(values)) {
        return 0;
      }

      var sign = isEnd ? 1 : -1;
      var idx = isEnd ? values.size() - 1 : 0;

      var deltas = deltas(values);
      return values.get(idx) + sign * extrapolate(deltas, isEnd);
    }

    private List<Integer> deltas(List<Integer> values) {
      List<Integer> deltas = new ArrayList<>();
      for (int i = 0; i < values.size() - 1; ++i) {
        deltas.add(values.get(i + 1) - values.get(i));
      }
      return deltas;
    }

    private List<List<Integer>> getSequences() {
      List<List<Integer>> result = new ArrayList<>();

      for (var line : lines) {
        result.add(new ArrayList<>(
            Arrays.stream(line.split(" "))
                .map(Integer::parseInt)
                .toList()
        ));
      }

      return result;
    }

    private boolean isAllZeros(List<Integer> values) {
      for (var v : values) {
        if (v != 0) {
          return false;
        }
      }
      return true;
    }
  }

}
