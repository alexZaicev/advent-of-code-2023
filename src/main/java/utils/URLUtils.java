package utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class URLUtils {

  public static String getRawStringFromURL(String url) {
    HttpResponse<String> response;
    try {
      HttpClient client = HttpClient.newBuilder().build();
      HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
      response = client.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    } catch (Exception ex) {
      throw new RuntimeException("failed to get input from URL", ex);
    }

    return response.toString();
  }

}
