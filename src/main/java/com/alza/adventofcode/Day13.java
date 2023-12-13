package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import com.alza.adventofcode.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Day13 {

  static final String INPUT_FILE = "day_13/input.txt";

  @SneakyThrows
  public static void main(String[] args) {
    var app = new Application(FileUtils.readResourceFile(INPUT_FILE, true));
    log.info("Symmetry sum: %d".formatted(app.calculateSymmetrySum()));
    log.info("Symmetry sum with smudges: %d".formatted(app.calculateSymmetrySumWithSmudges()));
  }

  @RequiredArgsConstructor
  public static class Application {

    private final List<String> lines;

    public int calculateSymmetrySum() {
      var sum = 0;

      var blocks = getBlocks();
      for (var block : blocks) {
        var h = findSymmetry(block);
        var v = findSymmetry(ListUtils.transpose(block));

        sum += v + 100 * h;
      }
      return sum;
    }

    public int calculateSymmetrySumWithSmudges() {
      var sum = 0;

      var blocks = getBlocks();
      for (var block : blocks) {
        var h = findSymmetryWithSmudges(block);
        var v = findSymmetryWithSmudges(ListUtils.transpose(block));

        sum += v + 100 * h;
      }
      return sum;
    }

    private List<List<String>> getBlocks() {
      List<List<String>> blocks = new ArrayList<>();

      List<String> block = new ArrayList<>();
      for (var line : lines) {
        if (line.isBlank()) {
          blocks.add(block);
          block = new ArrayList<>();
          continue;
        }
        block.add(line);
      }

      if (!block.isEmpty()) {
        blocks.add(block);
      }

      return blocks;
    }

    private int findSymmetry(List<String> block) {
      for (int row = 1; row < block.size(); ++row) {
        var above = ListUtils.getReversedSublist(block, 0, row);
        var below = block.subList(row, block.size());

        above = above.subList(0, Math.min(below.size(), above.size()));
        below = below.subList(0, above.size());

        if (above.equals(below)) {
          return row;
        }
      }

      return 0;
    }

    private int findSymmetryWithSmudges(List<String> block) {
      for (int row = 1; row < block.size(); ++row) {
        var above = ListUtils.getReversedSublist(block, 0, row);
        var below = block.subList(row, block.size());

        if (countSmudges(above, below) == 1) {
          return row;
        }
      }

      return 0;
    }

    private int countSmudges(List<String> above, List<String> below) {
      int smudges = 0;

      for (int i = 0; i < Math.min(above.size(), below.size()); ++i) {
        var a = above.get(i);
        var b = below.get(i);
        for (int j = 0; j < Math.min(a.length(), b.length()); ++j) {
          if (a.charAt(j) != b.charAt(j)) {
            smudges++;
          }
        }
      }

      return smudges;
    }
  }

}
