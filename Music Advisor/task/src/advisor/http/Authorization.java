package advisor.http;

import advisor.Config;

import java.util.HashMap;

public class Authorization {
    private boolean authorized;
    private String token;

    private static final Authorization instance = new Authorization();

    private Authorization() {
        authorized = false;
        token = null;
    }

    public static Authorization getInstance() { return instance; }

    public String getToken() { return token; }

    public void authorize() {
        if (authorized) {
            System.out.println("You've already authorized.");
            return;
        }
        if (setToken()) {
            authorized = true;
            System.out.println("Success!");
            return;
        }
        System.out.println("Failed at authorizing. Please try again.");
    }

    private boolean setToken() {
        String body = Client.getInstance().getResponseBody("POST",
                getTokenUri(), getHeaders(), getRequestBody(getAccessCode()));
        if (body == null) { return false; }
        token = body.split("\"access_token\"\\s*:\\s*\"")[1].split("\"")[0];
        return true;
    }

    private String getAccessCode() {
        System.out.println("use this link to request the access code:");
        System.out.println(getAccessUri());
        System.out.println("waiting for code...");
        return Server.getInstance().getCode();
    }

    private String getRequestBody(String code) {
        return "code=" + code + "&" +
                "grant_type=authorization_code&" +
                "redirect_uri=" + Config.serverPath + ":" + Config.serverPort + "&" +
                "client_id=" + Config.clientId + "&" +
                "client_secret=" + Config.clientSecret;
    }

    private String getAccessUri() {
        return Config.accessPath + "/authorize?" +
                "client_id=" + Config.clientId +
                "&redirect_uri=" + Config.serverPath + ":" + Config.serverPort +
                "&response_type=code";
    }

    private String getTokenUri() { return Config.accessPath + Config.tokenPath; }

    private HashMap<String, String> getHeaders() {
        return new HashMap<>() {{ put("Content-Type", "application/x-www-form-urlencoded"); }};
    }
}
