package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.*;

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

        private final String[] lines;

        @SneakyThrows
        public long findLowestLocation() {
            // create seed ranges
            Deque<Range> seedRanges = getSeedRanges();
            List<List<MapRange>> mapRanges = getMappingRanges();

            for (List<MapRange> ranges : mapRanges) {
                Deque<Range> newSeedRanges = new ArrayDeque<>();
                while (!seedRanges.isEmpty()) {
                    Range seedRange = seedRanges.pollLast();

                    var shouldAdd = true;
                    for (MapRange range : ranges) {
                        long sourceStart = Math.max(seedRange.start(), range.source());
                        long sourceEnd = Math.min(seedRange.end(), range.sourceEnd());

                        if (sourceStart >= sourceEnd) {
                            continue;
                        }

                        newSeedRanges.addLast(new Range(
                                sourceStart - range.source() + range.target(),
                                sourceEnd - range.source() + range.target()
                        ));
                        if (sourceStart > seedRange.start()) {
                            seedRanges.addLast(new Range(seedRange.start(), sourceStart));
                        }
                        if (seedRange.end() > sourceEnd) {
                            seedRanges.addLast(new Range(sourceEnd, seedRange.end()));
                        }
                        shouldAdd = false;
                        break;
                    }

                    if (shouldAdd) {
                        newSeedRanges.add(seedRange);
                    }
                }
                seedRanges.addAll(newSeedRanges);
            }

            List<Long> locations = new ArrayList<>();
            seedRanges.forEach(range -> {
                locations.add(range.start());
                locations.add(range.end());
            });
            Collections.sort(locations);
            return locations.get(0);
        }

        private Deque<Range> getSeedRanges() {
            Deque<Range> seedRanges = new ArrayDeque<>();

            var tokens = lines[0].split(":")[1].split(" ");
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
            for (int i = 1; i < lines.length; ++i) {
                var line = lines[i].strip();
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
