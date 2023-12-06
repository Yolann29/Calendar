package Days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cubes {

    public ArrayList<String> gamesLines;

    public Cubes(String gamesPath) {
        this.gamesLines = new ArrayList<>();
        getGames(gamesPath);
    }

    public void getGames(String gamesPath) {
        try {
            InputStream games = getClass().getResourceAsStream(gamesPath);
            BufferedReader gamesFile = new BufferedReader(new InputStreamReader(Objects.requireNonNull(games)));

            for (int i = 0; i < 100; i++) {
                String line = gamesFile.readLine();
                gamesLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int multiplyCubes(String line) {
        ArrayList<String> blueCubes = new ArrayList<>();
        ArrayList<String> redCubes = new ArrayList<>();
        ArrayList<String> greenCubes = new ArrayList<>();
        Pattern patternBlue = Pattern.compile("(\\d+) blue");
        Pattern patternRed = Pattern.compile("(\\d+) red");
        Pattern patternGreen = Pattern.compile("(\\d+) green");
        Matcher matcherBlue = patternBlue.matcher(line);
        Matcher matcherRed = patternRed.matcher(line);
        Matcher matcherGreen = patternGreen.matcher(line);
        while (matcherBlue.find()) {
            blueCubes.add(matcherBlue.group(1));
        }
        while (matcherRed.find()) {
            redCubes.add(matcherRed.group(1));
        }
        while (matcherGreen.find()) {
            greenCubes.add(matcherGreen.group(1));
        }
        int maxBlue = 0;
        for (String num : blueCubes) {
            if (Integer.parseInt(num) > maxBlue) maxBlue = Integer.parseInt(num);
        }
        int maxRed = 0;
        for (String num : redCubes) {
            if (Integer.parseInt(num) > maxRed) maxRed = Integer.parseInt(num);
        }
        int maxgreen = 0;
        for (String num : greenCubes) {
            if (Integer.parseInt(num) > maxgreen) maxgreen = Integer.parseInt(num);
        }
        return maxBlue*maxRed*maxgreen;
//        System.out.println(blueCubes);
//        System.out.println(redCubes);
//        System.out.println(greenCubes);
//        System.out.println(possible);
    }

    public void sumPossibleGames() {
        int sum = 0;
        for (String gamesLine : gamesLines) {
            sum += multiplyCubes(gamesLine);
        }
        System.out.println(sum);
    }
}
