package day2;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class Day2 {

    static final String INPUT_FILE = "day_2/games.txt";
    private static final GameRules RULES = new GameRules(12, 14, 13);

    @SneakyThrows
    public static void main(String[] args) {
        var lines = FileUtils.readResourceFile(INPUT_FILE);
        log.info("Game ID sum: %d".formatted(calculateGameSum(lines)));
        log.info("Game Power sum: %d".formatted(calculateGamePower(lines)));
    }

    static int calculateGameSum(String... lines) {
        var sum = 0;

        for (String line : lines) {
            line = line.strip();

            var gameID = getGameID(line);
            var game = convertRecordToGame(line);

            if (game.matchesRule()) {
                sum += gameID;
            }
        }

        return sum;
    }

    static int calculateGamePower(String... lines) {
        var sum = 0;

        for (String line : lines) {
            line = line.strip();

            var game = convertRecordToGame(line);
            sum += game.power();
        }

        return sum;
    }

    private static int getGameID(String s) {
        Matcher matcher = Pattern.compile(" ([0-9]+):").matcher(s);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(0).replace(":", "").strip());
        }

        return -1;
    }

    private static Game convertRecordToGame(String s) {
        List<GameSet> sets = new ArrayList<>();

        for (String subset : s.split(";")) {
            int red = 0;
            int blue = 0;
            int green = 0;

            Matcher matcher = Pattern.compile("([0-9]+) (red|blue|green)").matcher(subset);
            while (matcher.find()) {
                var tokens = matcher.group().split(" ");
                var amount = Integer.parseInt(tokens[0]);

                switch (tokens[1]) {
                    case "red":
                        red += amount;
                        continue;
                    case "blue":
                        blue += amount;
                        continue;
                    case "green":
                        green += amount;
                        continue;
                    default:
                        log.error("unknown color: " + tokens[1]);
                }
            }

            sets.add(new GameSet(red, blue, green));
        }

        return new Game(sets);
    }

    record GameSet(int red, int blue, int green) {

        boolean matchesRule() {
            return this.red <= RULES.red() && this.blue <= RULES.blue() && this.green <= RULES.green();
        }
    }

    record Game(List<GameSet> sets) {

        boolean matchesRule() {
            for (GameSet set : this.sets) {
                if (!set.matchesRule()) {
                    return false;
                }
            }
            return true;
        }

        int power() {
            return this.getMaxRed() * this.getMaxBlue() * this.getMaxGreen();
        }

        int getMaxRed() {
            int max = 0;
            for (GameSet set : this.sets) {
                if (max < set.red()) {
                    max = set.red();
                }
            }
            return max;
        }

        int getMaxBlue() {
            int max = 0;
            for (GameSet set : this.sets) {
                if (max < set.blue()) {
                    max = set.blue();
                }
            }
            return max;
        }

        int getMaxGreen() {
            int max = 0;
            for (GameSet set : this.sets) {
                if (max < set.green()) {
                    max = set.green();
                }
            }
            return max;
        }
    }


    record GameRules(int red, int blue, int green) {
    }
}
