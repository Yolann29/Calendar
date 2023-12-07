package Days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Seeds {

    public ArrayList<String> lines;
    public long columnLength, lineLength;
    public ArrayList<String> seeds;
    public ArrayList<Long> seedsTested;
    public Long time;

    public Seeds(String path) {
        this.lines = new ArrayList<>();
        this.seedsTested = new ArrayList<>();
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
        time = 0L;
        for (int i = 0; i < seeds.size(); i = i + 2) {
            time += Long.parseLong(seeds.get(i));
        }
        System.out.println(time);
//        System.out.println(seeds);
//        System.out.println(lines);
    }

    public long getSeed(Long location, String range) {
        long value = location;
        long rangeValue = Long.parseLong(range);
        long minlocation = 0;
        for (long i = value; i < value + rangeValue; i++) {
            long newValue = i;
            time -= 1;
            System.out.println(time);
            if (!seedsTested.contains(newValue)) {
                seedsTested.add(newValue);
                //            System.out.println("000000000000000000000000000000000000");
                for (long row = 1; row <= 7; row++) {
//                    System.out.println(newValue);
                    newValue = returnNextValue(newValue, row);
                    //                System.out.println("row: " + row);
                }
                if (minlocation == 0) minlocation = newValue;
                if (minlocation > newValue) minlocation = newValue;
            }
        }
        return minlocation;
    }

    public long returnNextValue(long value, long valueRow) {
//        System.out.println("#######################NextValue###########################");
        int row = 0;
        long nextValue = value;
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher;
        for (String line : lines) {
            if (line != null && !line.isEmpty()) {
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    row += 1;
//                    System.out.println("Row :" + row);
                } else {
                    String[] numbers = line.split(" +");
//                    numbers[0] = numbers[0] + "L";
//                    numbers[1] = numbers[1] + "L";
//                    numbers[2] = numbers[2] + "L";
//                    System.out.println(line);
//                    System.out.println(Arrays.toString(numbers));
                    Long[] num = new Long[3];
                    num[0] = Long.parseLong(numbers[0]);
                    num[1] = Long.parseLong(numbers[1]);
                    num[2] = Long.parseLong(numbers[2]);
                    if (row == valueRow) {
                        if (num[1] <= value && value < num[1] + num[2]) {
                            nextValue = value + (num[0] - num[1]);
//                            System.out.println("Next: " + nextValue);
//                            System.out.println("Value: " + value);
                        }
                    }
                }
            }
        }
        return nextValue;
    }

    public void getResult() {
        long minlocation = 0;
        while (getLocation())
//        for (int i = 0; i < seeds.size(); i = i + 2) {
////            System.out.println(":::::::::::::::::Seed:::::::::::::::::");
//            long location = getLocation(seeds.get(i), seeds.get(i+1));
//            if (minlocation == 0) minlocation = location;
//            if (minlocation > location) minlocation = location;
//        }
        minlocation = getLocation(seeds.get(0), seeds.get(1));
        System.out.println(minlocation);
    }
}