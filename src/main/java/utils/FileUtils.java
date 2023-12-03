package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
