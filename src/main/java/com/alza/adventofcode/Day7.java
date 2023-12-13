package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import com.alza.adventofcode.utils.MapUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Day7 {

  static final String INPUT_FILE = "day_7/bids.txt";

  @SneakyThrows
  public static void main(String[] args) {
    for (var file : List.of(INPUT_FILE)) {
      var app = new Application(FileUtils.readResourceFile(file));
      log.info("Bids: %d".formatted(app.calculateBids()));
      log.info("Bids with joker: %d".formatted(app.calculateBidsWithJoker()));
    }
  }

  @RequiredArgsConstructor
  public static class Application {

    private final List<String> lines;

    @SneakyThrows
    public int calculateBids() {
      List<Bid> bids = new ArrayList<>(getBids());
      bids.sort((a, b) -> {
        var ar = getRank(a, false);
        var ab = getRank(b, false);

        var com = ab.compareTo(ar);
        if (com != 0) {
          return com;
        }

        for (int i = 0; i < a.hand().length(); ++i) {
          int fa = getRankOfFirstCard(a.hand().toCharArray()[i]);
          int fb = getRankOfFirstCard(b.hand().toCharArray()[i]);
          if (fa == fb) {
            continue;
          }

          return Integer.compare(fa, fb);
        }

        return 0;
      });

      int result = 0;
      for (int i = bids.size(); i > 0; --i) {
        result += bids.get(i - 1).bid() * i;
      }

      return result;
    }

    @SneakyThrows
    public int calculateBidsWithJoker() {
      List<Bid> bids = new ArrayList<>(getBids());
      bids.sort((a, b) -> {
        var ar = getRank(a, true);
        var ab = getRank(b, true);

        var com = ab.compareTo(ar);
        if (com != 0) {
          return com;
        }

        for (int i = 0; i < a.hand().length(); ++i) {
          int fa = getRankOfFirstCardWithJoker(a.hand().toCharArray()[i]);
          int fb = getRankOfFirstCardWithJoker(b.hand().toCharArray()[i]);
          if (fa == fb) {
            continue;
          }

          return Integer.compare(fa, fb);
        }

        return 0;
      });

      int result = 0;
      for (int i = bids.size(); i > 0; --i) {
        result += bids.get(i - 1).bid() * i;
      }

      return result;
    }

    private List<Bid> getBids() {
      return lines.stream().map(l -> {
        var tokens = l.split(" ");
        return new Bid(tokens[0], Integer.parseInt(tokens[1]));
      }).toList();
    }

    private Combination getRank(Bid bid, boolean withJokers) {
      Map<Character, Integer> charCount = new HashMap<>();

      for (char ch : bid.hand().toCharArray()) {
        charCount.put(ch, charCount.getOrDefault(ch, 0) + 1);
      }

      if (withJokers && charCount.containsKey('J') && charCount.get('J') < 5) {
        int count = charCount.get('J');
        charCount.remove('J');

        List<Character> keys = List.copyOf(MapUtils.sortByValue(charCount).keySet());
        char ch = keys.get(keys.size() - 1);
        charCount.put(ch, charCount.get(ch) + count);
      }

      List<Integer> counts = charCount.values().stream().sorted().toList();

      if (counts.size() == 1) {
        return Combination.FIVE_OF_KIND;
      }

      if (counts.equals(List.of(1, 4))) {
        return Combination.FOUR_OF_KIND;
      }

      if (counts.equals(List.of(2, 3))) {
        return Combination.FULL_HOUSE;
      }

      if (counts.equals(List.of(1, 1, 3))) {
        return Combination.THREE_OF_KIND;
      }

      if (counts.equals(List.of(1, 2, 2))) {
        return Combination.TWO_PAIR;
      }

      if (counts.equals(List.of(1, 1, 1, 2))) {
        return Combination.ONE_PAIR;
      }

      return Combination.HIGH_CARD;
    }

    private int getRankOfFirstCard(Character ch) {
      List<String> a = List.of(
          "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"
      );

      return a.indexOf(Character.toString(ch)) + 1;
    }

    private int getRankOfFirstCardWithJoker(Character ch) {
      List<String> a = List.of(
          "J", "2", "3", "4", "5", "6", "7", "8", "9", "T", "Q", "K", "A"
      );

      return a.indexOf(Character.toString(ch)) + 1;
    }

    enum Combination {
      FIVE_OF_KIND, FOUR_OF_KIND, FULL_HOUSE, THREE_OF_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD
    }

    record Bid(String hand, int bid) {
    }
  }
}
