package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Day11 {

  static final String INPUT_FILE = "day_11/input.txt";

  @SneakyThrows
  public static void main(String[] args) {
    var app = new Application(FileUtils.readResourceFile(INPUT_FILE));
    log.info("Shortest path sum: %d".formatted(app.calculateSumOfShortestPaths(2)));
    log.info("Shortest path sum: %d".formatted(app.calculateSumOfShortestPaths(1000000)));
  }

  @RequiredArgsConstructor
  public static class Application {

    private final List<String> lines;

    public long calculateSumOfShortestPaths(int scale) {
      var grid = getExpandedGalaxy();
      var galaxies = getGalaxies(grid);

      var emptyRows = getEmptyRows(grid);
      var emptyColumns = getEmptyColumns(grid);

      long sum = 0;

      for (int idx = 0; idx < galaxies.size(); ++idx) {
        var g1 = galaxies.get(idx);
        for (var g2 : galaxies.subList(idx, galaxies.size())) {
          for (int a = Math.min(g1.row(), g2.row()); a < Math.max(g1.row(), g2.row()); ++a) {
            sum += emptyRows.contains(a) ? scale : 1;
          }

          for (int a = Math.min(g1.col(), g2.col()); a < Math.max(g1.col(), g2.col()); ++a) {
            sum += emptyColumns.contains(a) ? scale : 1;
          }
        }
      }

      return sum;
    }

    private List<List<Character>> getExpandedGalaxy() {
      List<List<Character>> grid = new ArrayList<>();

      for (String line : lines) {
        List<Character> gridRow = new ArrayList<>();
        char[] chars = line.toCharArray();
        for (char aChar : chars) {
          gridRow.add(aChar);
        }
        grid.add(gridRow);
      }

      return grid;
    }

    private Set<Integer> getEmptyRows(List<List<Character>> grid) {
      Set<Integer> emptyRows = new HashSet<>();
      for (int row = 0; row < grid.size(); ++row) {
        if (!grid.get(row).contains('#')) {
          emptyRows.add(row);
        }
      }
      return emptyRows;
    }

    private Set<Integer> getEmptyColumns(List<List<Character>> grid) {
      Set<Integer> emptyColumns = new HashSet<>();
      for (int col = 0; col < grid.get(0).size(); ++col) {
        int finalCol = col;
        if (grid.stream().filter(row -> row.get(finalCol) == '#').findFirst().isEmpty()) {
          emptyColumns.add(col);
        }
      }
      return emptyColumns;
    }

    private List<Galaxy> getGalaxies(List<List<Character>> grid) {
      List<Galaxy> galaxies = new ArrayList<>();
      for (int row = 0; row < grid.size(); ++row) {
        for (int col = 0; col < grid.get(0).size(); ++col) {
          if (grid.get(row).get(col) == '#') {
            galaxies.add(new Galaxy(row, col));
          }
        }
      }
      return galaxies;
    }

    record Galaxy(int row, int col) {
    }
  }

}
