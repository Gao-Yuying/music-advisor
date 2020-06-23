package advisor.models;

import advisor.Config;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedHashMap;

public class FeaturedModel extends SpotifyModel {

    @Override
    protected String uri() {
        return Config.resourcePath + Config.featuredPath;
    }

    @Override
    protected void setDataFromJson(JsonObject json) {
        for (JsonElement elem : json.getAsJsonObject("playlists").getAsJsonArray("items")) {
            JsonObject obj = elem.getAsJsonObject();
            String title = obj.get("name").getAsString();
            String url = obj.getAsJsonObject("external_urls").get("spotify").getAsString();
            data.add(new LinkedHashMap<>() {{
                put("title", title);
                put("url", url);
            }});
        }
    }
}
