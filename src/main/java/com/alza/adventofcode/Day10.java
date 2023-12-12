package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Log4j2
public class Day10 {

    static final String INPUT_FILE = "day_10/input.txt";

    @SneakyThrows
    public static void main(String[] args) {
        var app = new Application(FileUtils.readResourceFile(INPUT_FILE));
        log.info("Last pipe: %d".formatted(app.findTheLastPipe()));
    }

    @RequiredArgsConstructor
    public static class Application {

        private final List<String> lines;

        public int findTheLastPipe() {
            Set<Axis> chain = new HashSet<>();
            Deque<Axis> deq = new ArrayDeque<>();
            deq.add(getStartingPosition());

            while (!deq.isEmpty()) {
                var pos = deq.pollFirst().getAvailableMoves(lines);
                if (!pos.isEmpty() && !chain.containsAll(pos)) {
                    deq.addAll(pos);
                    chain.addAll(pos);
                }
            }

            return Math.round((float) chain.size() / 2);
        }

        public int findEnclosedTiles() {
            Set<Axis> chain = new HashSet<>();
            Deque<Axis> deq = new ArrayDeque<>();
            var startingPos = getStartingPosition();
            deq.add(startingPos);

            while (!deq.isEmpty()) {
                var pos = deq.pollFirst().getAvailableMoves(lines);
                if (!pos.isEmpty() && !chain.containsAll(pos)) {
                    deq.addAll(pos);
                    chain.addAll(pos);
                }
            }

            var grid = getGrid();
            replaceStartingPosChar(grid, startingPos);

            // normalize grid
            for (int row = 0; row < grid.size(); ++row) {
                for (int col = 0; col < grid.get(0).size(); ++col) {
                    var axis = Axis.of(row, col);
                    if (!axis.getCh(lines).equals("S") && chain.contains(axis)) {
                        continue;
                    }
                    grid.get(row).set(col, ".");
                }
            }

            return 0;
        }

        private void replaceStartingPosChar(List<List<String>> grid, Axis pos) {
            // TODO
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

        private Axis getStartingPosition() {
            for (int row = 0; row < lines.size(); ++row) {
                if (lines.get(row).contains("S")) {
                    return new Axis(row, lines.get(row).indexOf("S"));
                }
            }
            return null;
        }

        private String getCh(Axis axis) {
            return lines.get(axis.row()).substring(axis.col(), axis.col() + 1);
        }

        record Axis(int row, int col) {
            public static Axis of(int row, int col) {
                return new Axis(row, col);
            }

            public List<Axis> getAvailableMoves(List<String> lines) {
                List<Axis> pos = new ArrayList<>();

                if (this.canMoveLeft(lines)) {
                    pos.add(Axis.of(this.row - 1, this.col));
                }
                if (this.canMoveRight(lines)) {
                    pos.add(Axis.of(this.row + 1, this.col));
                }
                if (this.canMoveUp(lines)) {
                    pos.add(Axis.of(this.row, this.col - 1));
                }
                if (this.canMoveDown(lines)) {
                    pos.add(Axis.of(this.row, this.col + 1));
                }

                return List.copyOf(pos);
            }

            private boolean canMoveLeft(List<String> lines) {
                var ch = getCh(lines);
                var left = Axis.of(this.row - 1, this.col);
                return this.row > 0 && "S|JL".contains(ch) && "|7F".contains(left.getCh(lines));
            }

            private boolean canMoveRight(List<String> lines) {
                var ch = getCh(lines);
                var right = Axis.of(this.row + 1, this.col);
                return this.row < lines.size() && "S|7F".contains(ch) && "|JL".contains(right.getCh(lines));
            }

            private boolean canMoveUp(List<String> lines) {
                var ch = getCh(lines);
                var top = Axis.of(this.row, this.col - 1);
                return this.col > 0 && "S-J7".contains(ch) && "-LF".contains(top.getCh(lines));
            }

            private boolean canMoveDown(List<String> lines) {
                var ch = getCh(lines);
                var bottom = Axis.of(this.row, this.col + 1);
                return this.col < lines.get(0).length() && "S-LF".contains(ch) && "-J7".contains(bottom.getCh(lines));
            }

            private String getCh(List<String> lines) {
                return lines.get(this.row).substring(this.col, this.col + 1);
            }
        }
    }

}
