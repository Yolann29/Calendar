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
    public ArrayList<Long> soil, fertilizer, water, light, humidity, temperature, location;
    public Long time;

    public Seeds(String path) {
        this.lines = new ArrayList<>();
        this.soil = new ArrayList<>();
        this.fertilizer = new ArrayList<>();
        this.water = new ArrayList<>();
        this.light = new ArrayList<>();
        this.temperature = new ArrayList<>();
        this.humidity = new ArrayList<>();
        this.location = new ArrayList<>();
        getMap(path);
        storeSeeds();
        getData();
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
//        System.out.println(seeds);
//        System.out.println(lines);
    }

    public void getData() {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher;
        int row = 7;
        for (int i = lines.size()-1; i >= 0; i--) {
            System.out.println(row);
            if (lines.get(i) != null && !lines.get(i).isEmpty()) {
                matcher = pattern.matcher(lines.get(i));
                if (matcher.find()) {
                    row -= 1;
//                    System.out.println("Row :" + row);
                } else {
                    String[] numbers = lines.get(i).split(" +");
                    Long[] num = new Long[3];
                    num[0] = Long.parseLong(numbers[0]);
                    num[1] = Long.parseLong(numbers[1]);
                    num[2] = Long.parseLong(numbers[2]);
                    if (row == 7) {
                        location.add(num[0]);
                        location.add(num[1]);
                        location.add(num[2]);
                    }
                    if (row == 6) {
                        humidity.add(num[0]);
                        humidity.add(num[1]);
                        humidity.add(num[2]);
                    }
                    if (row == 5) {
                        temperature.add(num[0]);
                        temperature.add(num[1]);
                        temperature.add(num[2]);
                    }
                    if (row == 4) {
                        light.add(num[0]);
                        light.add(num[1]);
                        light.add(num[2]);
                    }
                    if (row == 3) {
                        water.add(num[0]);
                        water.add(num[1]);
                        water.add(num[2]);
                    }
                    if (row == 2) {
                        fertilizer.add(num[0]);
                        fertilizer.add(num[1]);
                        fertilizer.add(num[2]);
                    }
                    if (row == 1) {
                        soil.add(num[0]);
                        soil.add(num[1]);
                        soil.add(num[2]);
                    }
                }
            }
        }
        System.out.println("soil :" + soil);
        System.out.println("location :" + location);
    }

    public long getSeed(Long location) {
        long value = location;
        long valueOriginal = location;
//        System.out.println("000000000000000000000000000000000000");
        for (long row = 7; row > 0; row--) {
//            System.out.println("RRRROOOWWW:   " + row);
            value = returnNextValue(value, row);
        }
        for (int i = 0; i < seeds.size()-1; i = i + 2) {
//            System.out.println("\nValue: " + value);
//            System.out.println("ValueOriginal: " + valueOriginal);
//            System.out.println("Start: " + seeds.get(i));
//            System.out.println("Range: " + seeds.get(i + 1));
            if (Long.parseLong(seeds.get(i)) < value && value < Long.parseLong(seeds.get(i)) + Long.parseLong(seeds.get(i + 1))) {
                System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
                return valueOriginal;
            }
        }
        return 0;
    }

    public long returnNextValue(long value, long valueRow) {
//        System.out.println("#######################NextValue###########################");
        long nextValue = value;
        ArrayList<Long> list = new ArrayList<>();
        if (valueRow == 1) list = soil;
        if (valueRow == 2) list = fertilizer;
        if (valueRow == 3) list = water;
        if (valueRow == 4) list = light;
        if (valueRow == 5) list = temperature;
        if (valueRow == 6) list = humidity;
        if (valueRow == 7) list = location;
        if (list.isEmpty()) System.out.println("ERROR not good row" + valueRow);
//        System.out.println(list);
//        System.out.println("Soil: " + soil);
        for (int i = 0; i < list.size(); i = i + 3) {
            if (list.get(i) <= value && value < list.get(i) + list.get(i + 2)) {
                nextValue = value + (list.get(i + 1) - list.get(i));
//                System.out.println("Next: " + nextValue);
//                System.out.println("Value: " + value);
            }
        }
        return nextValue;
    }

    public void getResult() {
        long minlocation = 0;
        long i = 0; // 26381840 (10) [200000000] // 5756337 (10) // 2735237 (3) // 1145390 (2) // 407512 (1)
        while (minlocation == 0) {
            System.out.println(i);
            if (getSeed(i) != 0) minlocation = getSeed(i);
            i = i + 1;
        }
        System.out.println(minlocation);
//        for (int i = 0; i < seeds.size(); i = i + 2) {
////            System.out.println(":::::::::::::::::Seed:::::::::::::::::");
//            long location = getLocation(seeds.get(i), seeds.get(i+1));
//            if (minlocation == 0) minlocation = location;
//            if (minlocation > location) minlocation = location;
//        }
//        minlocation = getLocation(seeds.get(0), seeds.get(1));
//        System.out.println(minlocation);
    }
}