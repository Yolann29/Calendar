package Days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Cards {

    public ArrayList<String> cardsLines;
    public int columnLength, lineLength;

    public Cards(String cardsPath) {
        this.cardsLines = new ArrayList<>();
        getMap(cardsPath);
        getResult();
    }

    public void getMap(String cardsPath) {
        try {
            InputStream cards = getClass().getResourceAsStream(cardsPath);
            BufferedReader lines = new BufferedReader(new InputStreamReader(Objects.requireNonNull(cards)));

            this.columnLength = 189;
            for (int i = 0; i < columnLength; i++) {
                String line = lines.readLine();
                cardsLines.add(line);
            }
            this.lineLength = cardsLines.get(0).length();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLine(String line) {
        String[] lines = line.split("\\|");
        String ourLine = lines[1].substring(1);
        String winLine = lines[0].split(": +")[1];
        String[] ourNumbers = ourLine.split(" +");
        String[] winNumbers = winLine.split(" +");
        int points = 0;
        for (String num : ourNumbers) {
            if (Arrays.asList(winNumbers).contains(num)) {
                points *= 2;
                if (points == 0) {
                    points = 1;
                }
            }
        }
        return points;
//        System.out.println(Arrays.toString(ourNumbers));
//        System.out.println(Arrays.toString(winNumbers));
//        System.out.println(ourLine);
//        System.out.println(winLine);

    }

    public void getResult() {
        int sum = 0;
        for (String line : cardsLines) {
            sum += getLine(line);
        }
        System.out.println(sum);
    }
}
