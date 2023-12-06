package com.alza.adventofcode.utils;

import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    public static List<String> readResourceFile(String file) throws IOException {
        return readResourceFile(file, "\n");
    }

    public static List<String> readResourceFile(String file, String splitSymbol) throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = loader.getResourceAsStream(file)) {
            if (is == null) {
                throw new FileNotFoundException("file [%s] not found".formatted(file));
            }

            return Arrays.stream(new String(is.readAllBytes()).split(splitSymbol))
                .filter(s -> !s.isBlank())
                .toList();
        }
    }

}
