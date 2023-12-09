package com.alza.adventofcode;

import com.alza.adventofcode.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class Template {

    static final String INPUT_FILE = "day_X/input.txt";

    @SneakyThrows
    public static void main(String[] args) {
        var app = new Application(FileUtils.readResourceFile(INPUT_FILE));
        log.info("Result sum: %d".formatted(app.calculateSum()));
    }

    @RequiredArgsConstructor
    public static class Application {

        private final List<String> lines;

        public int calculateSum() {
            return lines.size() + lines.size();
        }
    }

}
