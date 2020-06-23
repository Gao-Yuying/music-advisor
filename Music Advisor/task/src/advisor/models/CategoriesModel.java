package advisor.models;

import advisor.Config;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedHashMap;

public class CategoriesModel extends SpotifyModel{

    @Override
    protected String uri() {
        return Config.resourcePath + Config.categoriesPath;
    }

    @Override
    protected void setDataFromJson(JsonObject json) {
        for (JsonElement elem : json.getAsJsonObject("categories").getAsJsonArray("items")) {
            JsonObject obj = elem.getAsJsonObject();
            String name = obj.get("name").getAsString();
            String id = obj.get("id").getAsString();
            data.add(new LinkedHashMap<>() {{
                put("name", name);
                put("id", id);
            }});
        }
    }
}
