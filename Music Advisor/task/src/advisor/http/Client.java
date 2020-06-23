package advisor.http;

import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class Client {
    private final HttpClient client;

    private static final Client instance = new Client();

    private Client() { client = HttpClient.newBuilder().build(); }

    public static Client getInstance() { return instance; }

    public String getResponseBody(String sendType, String uri, HashMap<String, String> headers, String body) {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        if (headers != null) {
            HttpRequest.Builder finalBuilder = builder;
            headers.keySet().forEach(key -> finalBuilder.header(key, headers.get(key)));
        }
        try {
            builder = builder.uri(URI.create(uri));
        } catch (NullPointerException e) {
            return null;
        }
        try {
            HttpResponse<String> response = client.send(
                    request(builder, sendType, body), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400) {
                System.out.println(JsonParser.parseString(response.body())
                        .getAsJsonObject()
                        .getAsJsonObject("error")
                        .get("message").getAsString());
                return null;
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpRequest request(HttpRequest.Builder builder, String sendType, String body) {
        return (sendType.toUpperCase().equals("POST") ?
                builder.POST(HttpRequest.BodyPublishers.ofString(body)) :
                builder.GET())
                .build();
    }

}
