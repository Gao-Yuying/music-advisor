package advisor.models;

import advisor.Config;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

public class NewModel extends SpotifyModel{

    @Override
    protected String uri() {
        return Config.resourcePath + Config.newPath;
    }

    @Override
    protected void setDataFromJson(JsonObject json) {
        for (JsonElement elem : json.getAsJsonObject("albums").getAsJsonArray("items")) {
            JsonObject obj = elem.getAsJsonObject();
            String title = obj.get("name").getAsString();
            List<String> artists = new ArrayList<>();
            obj.getAsJsonArray("artists").forEach(
                    artist -> artists.add(artist.getAsJsonObject().get("name").getAsString()));
            String url = obj.getAsJsonObject("external_urls").get("spotify").getAsString();
            data.add(new LinkedHashMap<>() {{
                put("title", title);
                put("artists", artists);
                put("url", url);
            }});
        }
    }
}
