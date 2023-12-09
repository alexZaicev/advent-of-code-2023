package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Log4j2
public class Day5 {

    static final String INPUT_FILE = "day_5/seeds.txt";

    @SneakyThrows
    public static void main(String[] args) {
        var app = new Application(FileUtils.readResourceFile(INPUT_FILE));
        log.info("Lowest location: %d".formatted(app.findLowestLocation()));
    }

    @RequiredArgsConstructor
    public static class Application {

        private final List<String> lines;

        @SneakyThrows
        public long findLowestLocation() {
            // create seed ranges
            Deque<Range> currentRanges = getSeedRanges();
            List<List<MapRange>> mappingRanges = getMappingRanges();

            for (List<MapRange> ranges : mappingRanges) {
                Deque<Range> mappedRanges = new ArrayDeque<>();
                while (!currentRanges.isEmpty()) {
                    Range currentRange = currentRanges.pollLast();

                    var shouldAdd = true;
                    for (MapRange range : ranges) {
                        long sourceStart = Math.max(currentRange.start(), range.source());
                        long sourceEnd = Math.min(currentRange.end(), range.sourceEnd());

                        if (sourceStart < sourceEnd) {
                            mappedRanges.addLast(new Range(
                                    sourceStart - range.source() + range.target(),
                                    sourceEnd - range.source() + range.target()
                            ));
                            if (sourceStart > currentRange.start()) {
                                currentRanges.addLast(new Range(currentRange.start(), sourceStart));
                            }
                            if (currentRange.end() > sourceEnd) {
                                currentRanges.addLast(new Range(sourceEnd, currentRange.end()));
                            }
                            shouldAdd = false;
                            break;
                        }
                    }

                    if (shouldAdd) {
                        mappedRanges.add(currentRange);
                    }
                }
                currentRanges.addAll(mappedRanges);
            }

            return currentRanges.stream()
                    .map(Range::start)
                    .sorted()
                    .findFirst()
                    .orElse(0L);
        }

        private Deque<Range> getSeedRanges() {
            Deque<Range> seedRanges = new ArrayDeque<>();

            var tokens = lines.get(0).split(":")[1].split(" ");
            for (int i = 1; i < tokens.length; i += 2) {
                long start = Long.parseLong(tokens[i]);
                long amount = Long.parseLong(tokens[i + 1]);
                seedRanges.add(new Range(start, start + amount));
            }

            return seedRanges;
        }

        private List<List<MapRange>> getMappingRanges() {
            List<List<MapRange>> maps = new ArrayList<>();

            List<MapRange> tmp = new ArrayList<>();
            for (int i = 1; i < lines.size(); ++i) {
                var line = lines.get(i).strip();
                if (line.isEmpty() || !Character.isDigit(line.toCharArray()[0])) {
                    if (!tmp.isEmpty()) {
                        maps.add(List.copyOf(tmp));
                        tmp.clear();
                    }
                    continue;
                }

                var tokens = line.split(" ");
                tmp.add(new MapRange(
                        Long.parseLong(tokens[1]),
                        Long.parseLong(tokens[0]),
                        Long.parseLong(tokens[2]))
                );
            }

            maps.add(List.copyOf(tmp));
            return maps;
        }

        record Range(long start, long end) {
        }

        record MapRange(long source, long target, long amount) {
            long sourceEnd() {
                return source + amount;
            }
        }
    }
}
