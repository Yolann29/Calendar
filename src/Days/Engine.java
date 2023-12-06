package Days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Engine {

    public int lineLength;
    public int columnLength;
    public ArrayList<String> engineLines;
    public ArrayList<List<Integer>> indexes;
    public Integer[][] places;
    public ArrayList<Integer> numbers;

    public Engine(String enginePath) {
        this.engineLines = new ArrayList<>();
        this.indexes = new ArrayList<>();
        getMap(enginePath);
        System.out.println(lineLength);
        this.places = new Integer[columnLength][lineLength];
        this.numbers = new ArrayList<>();
        getSpecialCharacters();
        getPossiblePlaces();
        getNumbers();
    }

    public void getMap(String enginePath) {
        try {
            InputStream engine = getClass().getResourceAsStream(enginePath);
            BufferedReader map = new BufferedReader(new InputStreamReader(Objects.requireNonNull(engine)));

            this.columnLength = 140;
            for (int i = 0; i < columnLength; i++) {
                String line = map.readLine();
                engineLines.add(line);
            }
            this.lineLength = engineLines.get(0).length();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getLine(String line, int row) {
        line = line.replace(".", "1");
        Pattern pattern = Pattern.compile("\\W");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            indexes.add(Arrays.asList(row,matcher.start()));
        }
    }

    public void getSpecialCharacters() {
        int rows = 0;
        for (String line : engineLines) {
            getLine(line, rows);
            rows += 1;
        }
        System.out.println(indexes);
    }

    public void getPossiblePlaces() {
        for (List<Integer> chara : indexes) {
            places[chara.get(0)][chara.get(1)] = 2;
            if (chara.get(0) != 0) {
                places[chara.get(0) - 1][chara.get(1)] = 1;
                if (chara.get(1) != 0) places[chara.get(0) - 1][chara.get(1) - 1] = 1;
                if (chara.get(1) != lineLength) places[chara.get(0) - 1][chara.get(1) + 1] = 1;
            }
            if (chara.get(0) != columnLength) {
                places[chara.get(0) + 1][chara.get(1)] = 1;
                if (chara.get(1) != 0) places[chara.get(0) + 1][chara.get(1) - 1] = 1;
                if (chara.get(1) != lineLength) places[chara.get(0) + 1][chara.get(1) + 1] = 1;
            }
            if (chara.get(1) != 0) {
                places[chara.get(0)][chara.get(1) - 1] = 1;
            }
            if (chara.get(1) != lineLength) {
                places[chara.get(0)][chara.get(1) + 1] = 1;
            }
        }
        System.out.println(Arrays.deepToString(places));
    }

    public void getNumbersLine(String line, int row) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            boolean added = false;
            for (int i = 0; i < places[row].length; i++) {
                System.out.println("Start   :" + matcher.start());
                System.out.println("End   :" + matcher.end());
                System.out.println("Places   :" + places[row][i] + "    " + i);
                if (places[row][i] != null && matcher.start() <= i && matcher.end()-1 >= i) {
                    System.out.println(Integer.parseInt(matcher.group()));
                    if (!added) {
                        added = true;
                        numbers.add(Integer.parseInt(matcher.group()));
                        System.out.println(Integer.parseInt(matcher.group()));
                    }
                }
            }
        }
    }

    public void getNumbers() {
        int rows = 0;
        for (String line : engineLines) {
            getNumbersLine(line, rows);
            rows += 1;
        }
        System.out.println(numbers);
        int sum = 0;
        for(Integer number : numbers) {
            sum += number;
        }
        System.out.println(sum);
    }
}
