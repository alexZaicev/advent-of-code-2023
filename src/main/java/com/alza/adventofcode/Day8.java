package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import com.alza.adventofcode.utils.MathUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Day8 {

  static final String INPUT_FILE = "day_8/input.txt";

  @SneakyThrows
  public static void main(String[] args) {
    var app = new Application(FileUtils.readResourceFile(INPUT_FILE));
    log.info("Steps: %d".formatted(app.calculateDirections()));
    log.info("Steps: %d".formatted(app.calculateDirectionsGhosts()));
  }

  @RequiredArgsConstructor
  public static class Application {

    private final List<String> lines;

    public int calculateDirections() {
      String instructions = lines.get(0);
      Map<String, Direction> directions = getDirections();

      var steps = 0;
      var idx = 0;
      var current = "AAA";
      while (!current.equals("ZZZ")) {
        if (idx == instructions.length()) {
          idx = 0;
        }
        current = getNextDirection(instructions, directions, current, idx);
        idx += 1;
        steps += 1;
      }

      return steps;
    }

    public long calculateDirectionsGhosts() {
      String instructions = lines.get(0);
      Map<String, Direction> directions = getDirections();
      List<String> startingPositions = directions.keySet().stream()
          .filter(key -> key.endsWith("A"))
          .toList();

      Set<Long> stepSet = new HashSet<>();
      for (String start : startingPositions) {
        var current = start;
        var steps = 0L;
        var idx = 0;

        while (!current.endsWith("Z")) {
          if (idx == instructions.length()) {
            idx = 0;
          }
          current = getNextDirection(instructions, directions, current, idx);
          idx += 1;
          steps += 1;
        }

        stepSet.add(steps);
      }

      return MathUtils.lcm(stepSet);
    }

    private String getNextDirection(String instructions,
                                    Map<String, Direction> directions,
                                    String current,
                                    int idx) {
      var direction = directions.get(current);
      var next = instructions.substring(idx, idx + 1);
      return next.equals("L") ? direction.left() : direction.right();
    }

    private Map<String, Direction> getDirections() {
      Map<String, Direction> directions = new HashMap<>();
      for (int i = 1; i < lines.size(); ++i) {
        var tokens = lines.get(i).split(" = ");

        var key = tokens[0].strip();

        tokens = tokens[1]
            .replace("(", "")
            .replace(")", "")
            .replace(",", "")
            .split(" ");

        directions.put(key, new Direction(tokens[0], tokens[1]));
      }

      return directions;
    }

    record Direction(String left, String right) {
    }
  }

}
