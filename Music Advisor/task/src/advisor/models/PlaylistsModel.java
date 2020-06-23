package advisor.models;

import advisor.Config;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedHashMap;

public class PlaylistsModel extends SpotifyModel{

    @Override
    protected String uri() {
        String categoryId = categoryId();
        if (categoryId == null) {
            System.out.println("Unknown category name.");
            return null;
        }
        return String.format("%s%s/%s/playlists", Config.resourcePath, Config.categoriesPath, categoryId);
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

    private String categoryId() {
        CategoriesModel categoriesModel = new CategoriesModel();
        categoriesModel.setData();
        return (String) categoriesModel.data.stream()
                .filter(i -> i.get("name").equals(category))
                .map(i -> i.get("id"))
                .findFirst()
                .orElse(null);
    }
}
