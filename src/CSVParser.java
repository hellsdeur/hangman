import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CSVParser {
    private HashMap<String, ArrayList<String>> map;
    private ArrayList<String> keys;

    public CSVParser(String path) throws IOException {
        // create map
        this.map = new HashMap<>();
        this.keys = new ArrayList<>();

        // read all rows and store in an array
        List<String> list = Files.readAllLines(Paths.get(path), StandardCharsets.ISO_8859_1);
        String[] rows = list.toArray(new String[list.size()]);

        // get column names and initiate empty lists
        String[] columns = rows[0].split(",");
        for (int i = 0; i < columns.length; ++i) {
            ArrayList<String> elements = new ArrayList<>();
            this.map.put(columns[i], elements);
            this.keys.add(columns[i]);
        }

        // store rows elements in lists
        for (int i = 1; i < rows.length; ++i) {
            // split string by comma
            String row = rows[i];
            String[] row_elements = row.split(",");

            // for each element, if not empty then update list
            for (int j = 0; j < row_elements.length; ++j) {
                String key = this.keys.get(j);
                if (!row_elements[j].equals("")) {
                    this.map.get(key).add(row_elements[j]);
                }
            }
        }
    }

    public String randomKey() {
        Random r = new Random();
        int i = r.nextInt(this.keys.size());

        return this.keys.get(i);
    }

    public String randomElement(String key) {
        ArrayList<String> elements = this.map.get(key);

        Random r = new Random();
        int i = r.nextInt(elements.size());

        return elements.get(i);
    }
}
