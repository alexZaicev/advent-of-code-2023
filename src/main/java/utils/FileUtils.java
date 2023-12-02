package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

  public static String[] readResourceFile(String file) throws IOException {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try (InputStream is = loader.getResourceAsStream(file)) {
      if (is == null) {
        throw new FileNotFoundException("file [%s] not found".formatted(file));
      }

      return new String(is.readAllBytes()).split("\n");
    }
  }

}
