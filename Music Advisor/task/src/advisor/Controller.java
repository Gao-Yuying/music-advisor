package advisor;

import advisor.http.Authorization;
import advisor.models.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private boolean authorized = false;
    private final View view = new View();
    private static SpotifyModel model = null;
    private static final Map<String, SpotifyModel> modelMap;
    static {
        modelMap = new HashMap<>();
        modelMap.put("featured", new FeaturedModel());
        modelMap.put("new", new NewModel());
        modelMap.put("categories", new CategoriesModel());
        modelMap.put("playlists", new PlaylistsModel());
        modelMap.put("next", model);
        modelMap.put("prev", model);
    }

    public void execute(String cmd) {
        if ("auth".equals(cmd)) {
            authorize();
            return;
        }
        if (!authorized) {
            System.out.println("Please, provide access for application.");
            return;
        }
        if (!cmd.equals("prev") && !cmd.equals("next")) {
            setModel(cmd);
            if (model == null) {
                System.out.println("Invalid command.");
                return;
            }
            model.setData();
        }
        updateView(cmd.equals("prev") ? model.prevPage() : model.nextPage());
    }

    private void authorize() {
        Authorization.getInstance().authorize();
        authorized = true;
    }

    private void setModel(String cmd) {
        String modelName = cmd.split("\\s+")[0];
        model = modelMap.getOrDefault(modelName, null);
        setCategory(cmd);
    }

    private void updateView(List<LinkedHashMap<String, Object>> data) {
        view.display(data, model.getCurrentPageNum(), model.totalPages());
    }

    private void setCategory(String cmd) {
        if (cmd.matches("playlists(\\s+\\S+)+")) {
            model.setCategory(cmd.replaceFirst("playlists ", ""));
        }
    }
}
