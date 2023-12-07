package Days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class Camel {

    public ArrayList<String> lines;
    public long columnLength, lineLength;
    public ArrayList<String> seeds;
    public ArrayList<Long> seedsTested;
    public Long time;

    public Camel(String path) {
        this.lines = new ArrayList<>();
        this.seedsTested = new ArrayList<>();
        getMap(path);
        getResult();
    }

    public void getMap(String path) {
        try {
            InputStream input = getClass().getResourceAsStream(path);
            BufferedReader file = new BufferedReader(new InputStreamReader(Objects.requireNonNull(input)));

            this.columnLength = 1000;
            for (long i = 0; i < columnLength; i++) {
                String line = file.readLine();
                lines.add(line);
            }
            this.lineLength = lines.get(0).length();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void getResult() {

    }

}
