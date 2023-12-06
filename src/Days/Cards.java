package Days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class Cards {

    public ArrayList<String> cardsLines;
    public ArrayList<Integer> numberOfCards;
    public int columnLength, lineLength;

    public Cards(String cardsPath) {
        this.cardsLines = new ArrayList<>();
        this.numberOfCards = new ArrayList<>();
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
                numberOfCards.add(1);
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
                points += 1;
//                points *= 2;
//                if (points == 0) {
//                    points = 1;
//                }
            }
        }
//        System.out.println(points);
        return points;
//        System.out.println(Arrays.toString(ourNumbers));
//        System.out.println(Arrays.toString(winNumbers));
//        System.out.println(ourLine);
//        System.out.println(winLine);
    }

    public void getResult() {
        int sum = 0;
        for (int j = 0; j < cardsLines.size(); j++) {
            sum = numberOfCards.get(j) * getLine(cardsLines.get(j));
            System.out.println("Sum :" + sum);
            System.out.println(numberOfCards);
            for (int i = 0; i < getLine(cardsLines.get(j)); i++) {
                if ((i + j + 1) < cardsLines.size()) {
                    numberOfCards.set(i + j + 1, numberOfCards.get(i + j + 1) + numberOfCards.get(j));
                }
            }
        }
        System.out.println(sum);
        sum = 0;
        for (int number : numberOfCards) {
            sum += number;
        }
        System.out.println(numberOfCards);
        System.out.println(sum);
    }
}
