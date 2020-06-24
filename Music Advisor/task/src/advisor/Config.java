package advisor;

public class Config {
    public static String accessPath;
    public static String resourcePath;
    public static int page = 5;
    public static final int serverPort = 8080;
    public static final String serverPath = "http://localhost";
    public static final String tokenPath = "/api/token";
    public static final String featuredPath = "/v1/browse/featured-playlists";
    public static final String newPath = "/v1/browse/new-releases";
    public static final String categoriesPath = "/v1/browse/categories";
    public static final String clientId = "your client ID";
    public static final String clientSecret = "your client secret";

    public Config(String accessPath, String resourcePath, String pageStr) {
        Config.accessPath = accessPath == null ? "https://accounts.spotify.com" : accessPath;
        Config.resourcePath = resourcePath == null ? "https://api.spotify.com" : resourcePath;
        Config.page = pageStr == null ? 5 : Integer.parseInt(pageStr);
    }
}
