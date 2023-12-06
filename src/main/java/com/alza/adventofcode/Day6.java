package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Day6 {

  static final String INPUT_FILE = "day_6/records.txt";
  static final String INPUT_FILE_2 = "day_6/records_big.txt";

  @SneakyThrows
  public static void main(String[] args) {
    for (var file : List.of(INPUT_FILE, INPUT_FILE_2)) {
      var app = new Application(FileUtils.readResourceFile(file));
      log.info("Number of ways: %d".formatted(app.calculateNumberOfRecordWays()));
    }
  }

  @RequiredArgsConstructor
  public static class Application {

    private final List<String> lines;

    @SneakyThrows
    public long calculateNumberOfRecordWays() {
      List<Long> winWays = getRaces().stream()
          .map(this::getPossibleWinWays)
          .toList();

      long result = 1L;
      for (var way : winWays) {
        result *= way;
      }

      return result;
    }

    private List<Race> getRaces() {
      List<String> timeTokens = Arrays.stream(lines.get(0).split(" "))
          .filter(s -> !s.isBlank() && Character.isDigit(s.strip().toCharArray()[0]))
          .toList();
      List<String> recordTokens = Arrays.stream(lines.get(1).split(" "))
          .filter(s -> !s.isBlank() && Character.isDigit(s.strip().toCharArray()[0]))
          .toList();

      List<Race> races = new ArrayList<>();
      for (int i = 0; i < timeTokens.size(); ++i) {
        races.add(new Race(
            Long.parseLong(timeTokens.get(i)),
            Long.parseLong(recordTokens.get(i))
        ));
      }

      return List.copyOf(races);
    }

    private long getPossibleWinWays(Race race) {
      List<Integer> startWays = new ArrayList<>();
      for (int holdTime = 1; holdTime < race.time(); ++holdTime) {
        var distance = holdTime * (race.time - holdTime);

        if (distance > race.record()) {
          startWays.add(holdTime);
        }
      }

      return startWays.size();
    }

    record Race(long time, long record) {
    }
  }
}
