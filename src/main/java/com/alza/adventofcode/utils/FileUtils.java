package com.alza.adventofcode.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

  public static List<String> readResourceFile(String file) throws IOException {
    return readResourceFile(file, "\n", false);
  }

  public static List<String> readResourceFile(String file, boolean leaveBlank) throws IOException {
    return readResourceFile(file, "\n", leaveBlank);
  }

  public static List<String> readResourceFile(String file, String splitSymbol, boolean leaveBlank)
      throws IOException {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try (InputStream is = loader.getResourceAsStream(file)) {
      if (is == null) {
        throw new FileNotFoundException("file [%s] not found".formatted(file));
      }

      return Arrays.stream(new String(is.readAllBytes()).split(splitSymbol))
          .filter(s -> !s.isBlank() || leaveBlank)
          .toList();
    }
  }

}
