package Days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Numbers {

    public String elfesPath;
    public ArrayList<String> elfesLines;

    public Numbers(String elfesPath) {
        this.elfesPath = elfesPath;
        this.elfesLines = new ArrayList<>();
        getNumbers(elfesPath);
    }

    public void getNumbers(String elfesPath) {
        try {
            InputStream elfes = getClass().getResourceAsStream(elfesPath);
            BufferedReader elfesNumbers = new BufferedReader(new InputStreamReader(Objects.requireNonNull(elfes)));

            for (int i = 0; i < 1000; i++) {
                String line = elfesNumbers.readLine();
                elfesLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char getFirstNumber(String line) {
        boolean found = false;
        char num = 0;
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i)) && !found) {
                String str = getFisrtWordNumber(line.substring(0,i+1));
                if (str.isEmpty()) {
                    num = line.charAt(i);
                } else {
                    num = str.charAt(0);
                }
                found = true;
            }
        }
        if (found) return num;
        String str = getFisrtWordNumber(line);
        if (!str.isEmpty()) return str.charAt(0);
        return num;
    }

    public char getLastNumber(String line) {
        boolean found = false;
        char num = 0;
        for (int i = line.length() - 1; i >= 0; i--) {
            if (Character.isDigit(line.charAt(i)) && !found) {
                String str = getFisrtWordNumber(line.substring(i));
                if (str.isEmpty()) {
                    num = line.charAt(i);
                } else {
                    num = str.charAt(1);
                }
                found = true;
            }
        }
        if (found) return num;
        String str = getFisrtWordNumber(line);
        if (!str.isEmpty()) return str.charAt(1);
        return num;
    }

    public int createNumber(char first, char last) {
        String numString = String.valueOf(first) + last;
        return Integer.parseInt(numString);
    }

    public String getFisrtWordNumber(String line) {
        StringBuilder str = new StringBuilder(line);
        StringBuilder strOriginal = str;
        Pattern pattern = Pattern.compile("one|two|three|four|five|six|seven|eight|nine", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        Pattern patternReverse = Pattern.compile("eno|owt|eerht|ruof|evif|xis|neves|thgie|enin", Pattern.CASE_INSENSITIVE);
        Matcher matcherReverse = patternReverse.matcher(str);
        int start = str.length()*2;
        int end = str.length()*2;
        int startFinal = str.length()*2;
        int endFinal = str.length()*2;
        while (matcher.find()) {
            if (start == str.length()*2) start = matcher.start();
            if (end == str.length()*2) end = matcher.end();
        }
        str.reverse();
        while (matcherReverse.find()) {
            if (startFinal == str.length()*2) startFinal = matcherReverse.start();
            if (endFinal == str.length()*2) endFinal = matcherReverse.end();
        }
        str.reverse();
        if (start != str.length()*2 && end != str.length()*2 && startFinal != str.length()*2 && endFinal != str.length()*2) {
            str = new StringBuilder(str.substring(start, end) + str.substring(strOriginal.length() - endFinal, strOriginal.length() - startFinal));
            String string = str.toString();
            string = string.replaceAll("one", "1");
            string = string.replaceAll("two", "2");
            string = string.replaceAll("three", "3");
            string = string.replaceAll("four", "4");
            string = string.replaceAll("five", "5");
            string = string.replaceAll("six", "6");
            string = string.replaceAll("seven", "7");
            string = string.replaceAll("eight", "8");
            string = string.replaceAll("nine", "9");
            return string;
        }
        return "";
    }

    public int sumElfesNumbers() {
        char first = 0;
        char last = 0;
        int sum = 0;
        for (String elfesLine : elfesLines) {
            first = getFirstNumber(elfesLine);
            last = getLastNumber(elfesLine);
            sum += createNumber(first, last);
        }
        return sum;
    }
}


