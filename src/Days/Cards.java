package Days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class Cards {

    public ArrayList<String> cardsLines;
    public int columnLength, lineLength;

    public Cards(String cardsPath) {
        this.cardsLines = new ArrayList<>();
        getMap(cardsPath);
    }

    public void getMap(String enginePath) {
        try {
            InputStream engine = getClass().getResourceAsStream(enginePath);
            BufferedReader map = new BufferedReader(new InputStreamReader(Objects.requireNonNull(engine)));

            this.columnLength = 140;
            for (int i = 0; i < columnLength; i++) {
                String line = map.readLine();
                cardsLines.add(line);
            }
            this.lineLength = cardsLines.get(0).length();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
