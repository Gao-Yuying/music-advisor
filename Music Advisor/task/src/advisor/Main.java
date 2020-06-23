package advisor;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        initializeConfig(args);
        run();
    }

    private static void run() {
        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller();
        String cmd = scanner.nextLine().trim();
        while (!"exit".equals(cmd)) {
            controller.execute(cmd);
            cmd = scanner.nextLine().trim();
        }
    }

    private static void initializeConfig(String[] args) {
        Config.accessPath = Optional.ofNullable(getArg(args, "-access"))
                .orElse("https://accounts.spotify.com");
        Config.resourcePath = Optional.ofNullable(getArg(args, "-resource"))
                .orElse("https://api.spotify.com");
        Config.page = Integer.parseInt(Optional.ofNullable(getArg(args, "-page"))
                .orElse("5"));
    }

    private static String getArg(String[] args, String name) {
        return IntStream.range(0, args.length)
                .filter(i -> args[i].equals(name))
                .mapToObj(i -> args[i + 1])
                .findFirst()
                .orElse(null);
    }
}
