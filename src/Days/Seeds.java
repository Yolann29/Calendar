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

public class Seeds {

    public ArrayList<String> lines;
    public long columnLength, lineLength;
    public ArrayList<String> seeds;

    public Seeds(String path) {
        this.lines = new ArrayList<>();
        getMap(path);
        storeSeeds();
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

    public void storeSeeds() {
        String lineOne = lines.get(0).split(": +")[1];
        seeds = new ArrayList<>(Arrays.asList(lineOne.split(" +")));
        lines.remove(0);
        System.out.println(seeds);
        System.out.println(lines);
    }

    public long getLocation(String seed) {
        long value = Long.parseLong(seed);
        for (long row = 1; row <=7; row++) {
            value = returnNextValue(value, row);
        }
        return value;
    }

    public long returnNextValue(long value, long valueRow) {
        System.out.println("#######################NextValue###########################");
        int row = 0;
        long nextValue = value;
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher;
        for (String line : lines) {
            if (line != null && !line.isEmpty()) {
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    row += 1;
                    System.out.println("Row :" + row);
                } else {
                    String[] numbers = line.split(" +");
//                    numbers[0] = numbers[0] + "L";
//                    numbers[1] = numbers[1] + "L";
//                    numbers[2] = numbers[2] + "L";
                    System.out.println(line);
                    System.out.println(Arrays.toString(numbers));
                    Long[] num = new Long[3];
                    num[0] = Long.parseLong(numbers[0]);
                    num[1] = Long.parseLong(numbers[1]);
                    num[2] = Long.parseLong(numbers[2]);
                    if (row == valueRow) {
                        if (num[1] < value && value < num[1] + num[2]) {
                            nextValue = value + (num[0] - num[1]);
                            System.out.println("Next: " + nextValue);
                            System.out.println("Value: " + value);
                        }
                    }
                }
            }
        }
        return nextValue;
    }

    public void getResult() {
        long minlocation = 0;
        for (String seed : seeds) {
            System.out.println(":::::::::::::::::Seed:::::::::::::::::");
            long location = getLocation(seed);
            if (minlocation == 0) minlocation = location;
            if (minlocation > location) minlocation = location;
        }
        System.out.println(minlocation);
    }
}