package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Day15 {

  static final String INPUT_FILE = "day_15/input.txt";

  @SneakyThrows
  public static void main(String[] args) {
    var app = new Application(FileUtils.readResourceFile(INPUT_FILE));
    log.info("Hash sum: %d".formatted(app.getHashSum()));
    log.info("Focusing hash sum: %d".formatted(app.getFocusingPowerSum()));
  }

  @RequiredArgsConstructor
  public static class Application {

    private final List<String> lines;

    public long getHashSum() {
      long sum = 0;

      for (var seq : this.lines.get(0).split(",")) {
        sum += getHash(seq);
      }

      return sum;
    }

    private int getHash(String seq) {
      var seqSum = 0;
      for (var ch : seq.toCharArray()) {
        seqSum += ch;
        seqSum *= 17;
        seqSum %= 256;
      }
      return seqSum;
    }

    public long getFocusingPowerSum() {
      Map<Integer, List<Lens>> boxes = new TreeMap<>();

      for (var seq : this.lines.get(0).split(",")) {
        var isAdd = seq.contains("=");
        var lens = Lens.of(seq);
        var hash = getHash(lens.slot());
        if (isAdd) {
          addLens(boxes, lens, hash);
        } else {
          removeLens(boxes, lens, hash);
        }
      }

      long sum = 0;
      for (var e : boxes.entrySet()) {
        for (int i = 0; i < e.getValue().size(); ++i) {
          var lens = e.getValue().get(i);
          var tmp = (long) (e.getKey() + 1) * (i + 1) * lens.power();
          sum += tmp;
        }
      }
      return sum;
    }

    private void addLens(Map<Integer, List<Lens>> boxes, Lens lens, int hash) {
      if (boxes.containsKey(hash)) {
        for (var rl : boxes.get(hash)) {
          // replace with new one
          if (rl.slot().equals(lens.slot())) {
            var row = boxes.get(hash);
            row.set(row.indexOf(rl), lens);
            return;
          }
        }

        boxes.get(hash).add(lens);
        return;
      }

      boxes.put(hash, new ArrayList<>());
      boxes.get(hash).add(lens);
    }

    private void removeLens(Map<Integer, List<Lens>> boxes, Lens lens, int hash) {
      if (boxes.containsKey(hash)) {
        for (var rl : boxes.get(hash)) {
          if (rl.slot().equals(lens.slot())) {
            var row = boxes.get(hash);
            row.remove(rl);
            return;
          }
        }
      }
    }

    record Lens(String slot, int power) {
      public static Lens of(String seq) {
        var tokens = seq.contains("=")
            ? seq.split("=")
            : seq.split("-");
        if (tokens.length == 2) {
          return new Lens(tokens[0], Integer.parseInt(tokens[1]));
        }

        return new Lens(tokens[0], 0);
      }
    }
  }

}
