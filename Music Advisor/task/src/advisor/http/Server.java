package advisor.http;

import advisor.Config;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private HttpServer server = null;

    private static final Server instance = new Server();

    private Server() {
        try {
            server = HttpServer.create();
            int port = Config.serverPort;
            server.bind(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Server getInstance() { return instance; }

    public String getCode() {
        final String[] CODE = new String[1];
        server.createContext("/",
                exchange -> {
                    String query = exchange.getRequestURI().getQuery();
                    String response = "Not found authorization code. Try again.";
                    if (query != null && query.contains("code=")) {
                        for (String kv : query.split("&")) {
                            if ("code".equals(kv.split("=")[0])) {
                                response = "Got the code. Return back to your program.";
                                CODE[0] = kv.split("=")[1];
                                break;
                            }
                        }
                    }
                    exchange.sendResponseHeaders(200, response.length());
                    exchange.getResponseBody().write(response.getBytes());
                    exchange.getResponseBody().close();
                }
        );
        try {
            server.start();
            int counter = 0;
            while ((CODE[0] == null || "".equals(CODE[0])) && counter++ < 50) { Thread.sleep(200); }
            server.stop(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CODE[0];
    }
}
