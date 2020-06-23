package advisor;

import java.util.LinkedHashMap;
import java.util.List;

public class View {

    public void display(List<LinkedHashMap<String, Object>> data, int currentPageNum, int totalPages) {
        if (data.size() == 0) {
            System.out.println("No more pages.");
            return;
        }
        for (LinkedHashMap<String, Object> item : data) {
            for (String key : item.keySet()) {
                if (key.equals("id")) { continue; }
                System.out.println(item.get(key));
                if (key.equals("url")) { System.out.println(); }
            }
        }
        System.out.printf("---PAGE %d OF %d---\n", currentPageNum, totalPages);
    }
}
