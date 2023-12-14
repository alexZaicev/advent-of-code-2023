package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import com.alza.adventofcode.utils.ListUtils;
import com.alza.adventofcode.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Day14 {

  static final String INPUT_FILE = "day_14/input.txt";

  @SneakyThrows
  public static void main(String[] args) {
    var app = new Application(FileUtils.readResourceFile(INPUT_FILE));
    log.info("North beam load #1: %d".formatted(app.calculateNorthLoad()));
    log.info("North beam load #1_000_000_000: %d".formatted(
        app.calculateNorthLoadAfterCycle(1000000000)));
  }

  @RequiredArgsConstructor
  public static class Application {

    private final List<String> lines;

    public long calculateNorthLoad() {
      return getNorthBeamLoad(ListUtils.transpose(rollTheRocks(this.lines)));
    }

    public long calculateNorthLoadAfterCycle(int numCycles) {
      var grid = List.copyOf(lines);
      Set<List<String>> seen = new HashSet<>();
      seen.add(grid);
      List<List<String>> performed = new ArrayList<>();
      performed.add(grid);
      var i = 0;
      while (true) {
        i++;
        grid = cycle(grid);
        if (seen.contains(grid)) {
          break;
        }

        seen.add(grid);
        performed.add(grid);
      }

      var lastGridIdx = performed.indexOf(grid);
      grid = performed.get((numCycles - lastGridIdx) % (i - lastGridIdx) + lastGridIdx);


      return getNorthBeamLoad(grid);
    }

    private List<String> cycle(List<String> grid) {
      for (int i = 0; i < 4; i++) {
        // roll the rocks and rotate the grid anti-clockwise
        grid = rollTheRocks(grid).stream()
            .map(row -> new StringBuilder(row).reverse().toString())
            .toList();
      }

      return grid;
    }

    private List<String> rollTheRocks(List<String> grid) {
      List<String> newGrid = new ArrayList<>();
      for (var line : ListUtils.transpose(grid)) {
        var sb = new StringBuilder();
        var offset = 0;
        var sections = line.split("#");
        for (String section : sections) {
          var dots = 0;
          for (var ch : section.toCharArray()) {
            if (ch == 'O') {
              sb.insert(offset, ch);
            } else {
              dots++;
            }
          }

          sb.append(".".repeat(Math.max(0, dots)));

          offset += section.length();
        }

        for (int i = 0; i < line.length(); ++i) {
          if (line.charAt(i) == '#') {
            sb.insert(i, "#");
          }
        }

        newGrid.add(sb.toString());
      }

      return newGrid;
    }

    private long getNorthBeamLoad(List<String> grid) {
      long sum = 0;
      for (int i = 0; i < grid.size(); ++i) {
        sum += (long) StringUtils.count(grid.get(i), 'O') * (grid.size() - i);
      }
      return sum;
    }
  }

}
