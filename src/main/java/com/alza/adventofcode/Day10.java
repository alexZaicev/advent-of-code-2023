package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Day10 {

  static final String INPUT_FILE = "day_10/input.txt";

  @SneakyThrows
  public static void main(String[] args) {
    var app = new Application(FileUtils.readResourceFile(INPUT_FILE));
    log.info("Chain length: %d".formatted(app.findChainLength()));
    log.info("Sum of enclosed tiles: %d".formatted(app.findEnclosedTiles()));
  }

  @RequiredArgsConstructor
  public static class Application {

    private final List<String> lines;

    public int findChainLength() {
      var grid = getGrid();
      var startingPos = getStartingPosition(grid).orElseThrow();
      var chain = getChain(grid, startingPos);
      return Math.round((float) chain.size() / 2);
    }

    public int findEnclosedTiles() {
      var grid = getGrid();
      var startingPos = getStartingPosition(grid).orElseThrow();
      var chain = getChain(grid, startingPos);

      replaceStartingPosChar(grid, startingPos);
      chain.add(startingPos);
      normalizeGrid(grid, chain);

      var outside = getOutsideTiles(grid);
      outside.addAll(chain);

      return grid.get(0).size() * grid.size() - outside.size();
    }

    private Set<Axis> getChain(List<List<String>> grid, Axis startingPos) {
      Set<Axis> chain = new HashSet<>();
      Deque<Axis> deq = new ArrayDeque<>();
      deq.add(startingPos);

      while (!deq.isEmpty()) {
        var pos = deq.pollFirst().getAvailableMoves(grid);
        if (!pos.isEmpty() && !chain.containsAll(pos)) {
          deq.addAll(pos);
          chain.addAll(pos);
        }
      }

      return chain;
    }

    private Set<Axis> getOutsideTiles(List<List<String>> grid) {
      Set<Axis> outside = new HashSet<>();

      for (int row = 0; row < grid.size(); ++row) {
        var enclosed = false;
        var up = -1;

        for (int col = 0; col < grid.get(row).size(); ++col) {
          var ch = grid.get(row).get(col);

          if (ch.equals("|")) {
            enclosed = !enclosed;
          } else if ("LF".contains(ch)) {
            up = ch.equals("L") ? 1 : 0;
          } else if ("7J".contains(ch)) {
            if (!ch.equals(up == 1 ? "J" : "7")) {
              enclosed = !enclosed;
            }
            up = -1;
          }

          if (!enclosed) {
            outside.add(Axis.of(row, col));
          }
        }
      }

      return outside;
    }

    private void normalizeGrid(List<List<String>> grid, Set<Axis> chain) {
      for (int row = 0; row < grid.size(); ++row) {
        for (int col = 0; col < grid.get(row).size(); ++col) {
          if (!chain.contains(Axis.of(row, col))) {
            grid.get(row).set(col, ".");
          }
        }
      }
    }

    private void replaceStartingPosChar(List<List<String>> grid, Axis pos) {
      Set<String> replacements = new HashSet<>(Set.of("|", "-", "J", "L", "7", "F"));

      if (pos.canMoveLeft(grid)) {
        replacements.retainAll(Set.of("|", "J", "L"));
      }

      if (pos.canMoveRight(grid)) {
        replacements.retainAll(Set.of("|", "7", "F"));
      }

      if (pos.canMoveUp(grid)) {
        replacements.retainAll(Set.of("-", "J", "7"));
      }

      if (pos.canMoveDown(grid)) {
        replacements.retainAll(Set.of("-", "L", "F"));
      }

      grid.get(pos.row()).set(pos.col(), List.copyOf(replacements).get(0));
    }

    private List<List<String>> getGrid() {
      List<List<String>> grid = new ArrayList<>();

      for (String line : lines) {
        List<String> gridRow = new ArrayList<>();
        for (int col = 0; col < lines.get(0).length(); ++col) {
          gridRow.add(line.substring(col, col + 1));
        }

        grid.add(gridRow);
      }

      return grid;
    }

    private Optional<Axis> getStartingPosition(List<List<String>> grid) {
      for (int row = 0; row < grid.size(); ++row) {
        if (grid.get(row).contains("S")) {
          return Optional.of(new Axis(row, grid.get(row).indexOf("S")));
        }
      }
      return Optional.empty();
    }

    record Axis(int row, int col) {
      public static Axis of(int row, int col) {
        return new Axis(row, col);
      }

      public List<Axis> getAvailableMoves(List<List<String>> grid) {
        List<Axis> pos = new ArrayList<>();

        if (this.canMoveLeft(grid)) {
          pos.add(Axis.of(this.row - 1, this.col));
        }
        if (this.canMoveRight(grid)) {
          pos.add(Axis.of(this.row + 1, this.col));
        }
        if (this.canMoveUp(grid)) {
          pos.add(Axis.of(this.row, this.col - 1));
        }
        if (this.canMoveDown(grid)) {
          pos.add(Axis.of(this.row, this.col + 1));
        }

        return List.copyOf(pos);
      }

      private boolean canMoveLeft(List<List<String>> grid) {
        var ch = this.getCell(grid);
        var left = Axis.of(this.row - 1, this.col);
        return this.row > 0 && "S|JL".contains(ch) && "|7F".contains(left.getCell(grid));
      }

      private boolean canMoveRight(List<List<String>> grid) {
        var ch = this.getCell(grid);
        var right = Axis.of(this.row + 1, this.col);
        return this.row < grid.size() && "S|7F".contains(ch) && "|JL".contains(right.getCell(grid));
      }

      private boolean canMoveUp(List<List<String>> grid) {
        var ch = this.getCell(grid);
        var top = Axis.of(this.row, this.col - 1);
        return this.col > 0 && "S-J7".contains(ch) && "-LF".contains(top.getCell(grid));
      }

      private boolean canMoveDown(List<List<String>> grid) {
        var ch = this.getCell(grid);
        var bottom = Axis.of(this.row, this.col + 1);
        return this.col < grid.get(0).size() && "S-LF".contains(ch) &&
            "-J7".contains(bottom.getCell(grid));
      }

      private String getCell(List<List<String>> grid) {
        return grid.get(this.row).get(this.col);
      }
    }
  }

}
