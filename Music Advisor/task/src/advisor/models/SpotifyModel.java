package advisor.models;

import advisor.Config;
import advisor.http.Authorization;
import advisor.http.Client;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class SpotifyModel {

    final protected int itemsPerPage = Config.page;
    protected List<LinkedHashMap<String, Object>> data = new ArrayList<>();
    protected String category = null;
    protected int currentPageNum = 0;

    public void setData() {
        resetModel();
        JsonObject json = json();
        if (json != null) { setDataFromJson(json); }
    }

    public List<LinkedHashMap<String, Object>> nextPage() {
        if (!hasNextPage()) { return new ArrayList<>(); }
        currentPageNum++;
        return currentPage();
    }

    public List<LinkedHashMap<String, Object>> prevPage() {
        if (!hasPrevPage()) { return new ArrayList<>(); }
        currentPageNum--;
        return currentPage();
    }

    public void setCategory(String category) { this.category = category; }

    public int getCurrentPageNum() { return currentPageNum; }

    public int totalPages() { return (int) Math.ceil((double) data.size() / itemsPerPage); }

    abstract protected String uri();

    protected void resetModel() {
        data.clear();
        currentPageNum = 0;
    }

    abstract protected void setDataFromJson(JsonObject json);

    protected List<LinkedHashMap<String, Object>> currentPage() {
        return data.subList((currentPageNum - 1) * itemsPerPage,
                Math.min(currentPageNum * itemsPerPage, data.size()));
    }

    protected JsonObject json() {
        String json = Client.getInstance().getResponseBody("GET", uri(), headers(), null);
        return json == null || "".equals(json) ? null : JsonParser.parseString(json).getAsJsonObject();
    }

    protected HashMap<String, String> headers() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token());
        return headers;
    }

    protected String token() { return Authorization.getInstance().getToken(); }

    protected boolean hasNextPage() { return currentPageNum < totalPages(); }

    protected boolean hasPrevPage() { return currentPageNum > 1; }
}
