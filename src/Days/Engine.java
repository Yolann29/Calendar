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

    public int getLine(String line, int row) {
//        line = line.replace(".", "1");
        Pattern pattern = Pattern.compile("\\*");
        Matcher matcher = pattern.matcher(line);
        int sum = 0;
        while (matcher.find()) {
//            indexes.add(Arrays.asList(row,matcher.start()));
            sum += getPossiblePlaces(row,matcher.start());
            System.out.println("Sum changing:   " + sum);
        }
        return sum;
    }

    public void getSpecialCharacters() {
        int rows = 0;
        for (String line : engineLines) {
            getLine(line, rows);
            rows += 1;
        }
        System.out.println(indexes);
    }

    public int getPossiblePlaces(int row, int column) {
        this.places = new Integer[columnLength][lineLength];
        places[row][column] = 2;
        if (row != 0) {
            places[row - 1][column] = 1;
            if (column != 0) places[row - 1][column - 1] = 1;
            if (column != lineLength) places[row - 1][column + 1] = 1;
        }
        places[row + 1][column] = 1;
        if (column != 0) places[row + 1][column - 1] = 1;
        if (column != lineLength) places[row + 1][column + 1] = 1;
        if (column != 0) {
            places[row][column - 1] = 1;
        }
        if (column != lineLength) {
            places[row][column + 1] = 1;
        }
        System.out.println(Arrays.deepToString(places));
        int rowLine = 0;
        this.numbers = new ArrayList<>();
        for (String line : engineLines) {
            getNumbersLine(line, rowLine);
            rowLine += 1;
        }
        System.out.println("Numbers:  " + numbers);
        if (numbers.size() == 2) return numbers.get(0)*numbers.get(1);
        return 0;
    }

    public void getNumbersLine(String line, int row) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(line);
//        System.out.println(matcher.find());
        while (matcher.find()) {
            boolean added = false;
//            System.out.println("enter");
            for (int i = 0; i < places[row].length; i++) {
                System.out.println("Start   :" + matcher.start());
                System.out.println("End   :" + matcher.end());
                System.out.println("Places   :" + places[row][i] + "    " + i);
                if (places[row][i] != null && matcher.start() <= i && matcher.end()-1 >= i) {
                    System.out.println("Num matched:  " + Integer.parseInt(matcher.group()));
                    System.out.println("Numbers changing:  " + numbers);
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
        int row = 0;
        int sum = 0;
        for (String line : engineLines) {
            sum += getLine(line, row);
            row += 1;
        }
        System.out.println("Sum is:  " + sum);
    }
}
