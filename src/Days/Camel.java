package Days;

import com.sun.deploy.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Camel {

    public ArrayList<String> lines;
    public long columnLength, lineLength;
    public ArrayList<String> hands;
    public ArrayList<String> bids;
    public ArrayList<Character> powerScale;

    public Camel(String path) {
        this.lines = new ArrayList<>();
        this.hands = new ArrayList<>();
        this.bids = new ArrayList<>();
        this.powerScale = new ArrayList<>();
        getMap(path);
        getData();
        System.out.println("Power 0: " + getPower(hands.get(0)));
        System.out.println("Power 1: " + getPower(hands.get(1)));
        System.out.println("Power 2: " + getPower(hands.get(2)));
        System.out.println("Power 3: " + getPower(hands.get(3)));
        System.out.println("Power 4: " + getPower(hands.get(4)));
//        System.out.println(compare(hands.get(4), hands.get(1)));
//        System.out.println(compare(hands.get(2), hands.get(3)));
//        System.out.println(compare(hands.get(0), hands.get(3)));
//        System.out.println(compare(hands.get(0), hands.get(4)));
        int value = sort(hands, bids, 0, hands.size()-1);
        System.out.println(value);
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

    public void getData() {
        for (String line : lines) {
            if (line != null) {
                String[] split = line.split(" +");
                this.hands.add(split[0]);
                this.bids.add(split[1]);
            }
        }
        this.powerScale = new ArrayList<>(Arrays.asList('J','2','3','4','5','6','7','8','9','T','Q','K','A'));
        System.out.println(hands);
        System.out.println(bids);
        System.out.println(powerScale);
    }

    public int getPower(String hand) {
        int matches = 0;
        int j = 0;
        String handOriginal = hand;
        ArrayList<Integer> listMatches = new ArrayList<>();
//        System.out.println(powerScale);
        ArrayList<Character> power = new ArrayList<>(powerScale);
        while (!power.isEmpty()){
            matches = 0;
//            System.out.println(power);
//            System.out.println(hand);
            for (int i = 0; i < hand.length(); i++) {
//                System.out.println("power chara: " + power.get(0));
                if (hand.charAt(i) == power.get(0) && power.get(0) != 'J') {
//                    System.out.println("enter for: " + hand.charAt(i));
                    matches += 1;
                }
            }
            power.remove(0);
//            System.out.println(matches);
            listMatches.add(matches);
            j++;
        }
        int jMatches = 0;
        for (int i = 0; i < hand.length(); i++) {
//                System.out.println("power chara: " + power.get(0));
            if (hand.charAt(i) == 'J') {
//                    System.out.println("enter for: " + hand.charAt(i));
                jMatches += 1;
            }
        }
//        System.out.println("List: " + listMatches);
        if (listMatches.contains(5)) return 7;
        if (listMatches.contains(4)) {
            if (jMatches > 0) return 7;
            return 6;
        }
        if (listMatches.contains(3)) {
            if (jMatches == 1) return 6;
            if (jMatches > 1) return 7;
            if (listMatches.contains(2)) {
                return 5;
            }
            return 4;
        }
        if (listMatches.contains(2)) {
            listMatches.remove((Integer) 2);
            if (listMatches.contains(2)) {
                if (jMatches > 0) return 5;
                return 3;
            }
            if (jMatches == 1) return 4;
            if (jMatches == 2) return 6;
            if (jMatches > 2) return 7;
            return 2;
        }
        if (jMatches == 1) return 2;
        if (jMatches == 2) return 4;
        if (jMatches == 3) return 6;
        if (jMatches > 3) return 7;
        return 1;
    }

    public boolean equalPower(String hand1, String hand2) {
        return getPower(hand1) == getPower(hand2);
    }

    public boolean comparePower(String hand1, String hand2) {
        return getPower(hand1) < getPower(hand2);
    }

    public boolean compare(String hand1, String hand2) {
        if (equalPower(hand1, hand2)) {
            for (int i = 0; i < hand1.length(); i++) {
                if (hand1.charAt(i) != hand2.charAt(i)) {
                    return powerScale.indexOf(hand1.charAt(i)) < powerScale.indexOf(hand2.charAt(i));
                }
            }
            System.out.println(hand1);
            System.out.println(hand2);
            System.out.println("ERROR");
            return false;
        }
        return comparePower(hand1, hand2);
    }

    public int sort(List<String> hands, List<String> bids, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            System.out.println("Left :" + leftIndex);
            System.out.println("Middle: " + (rightIndex - leftIndex) / 2);
            System.out.println("Right: " + rightIndex);
            int middleIndex = (rightIndex + leftIndex) / 2;
            sort(hands, bids, leftIndex, middleIndex);
            sort(hands, bids, middleIndex + 1, rightIndex);
            merge(hands, bids, leftIndex, middleIndex, rightIndex);
        } else {
            System.out.println("Same Index");
            System.out.println(this.hands);
        }
        return 0;
    }

    public void merge(List<String> hands, List<String> bids, int leftIndex, int middleIndex, int rightIndex) {
        int n1 = middleIndex - leftIndex + 1;
        int n2 = rightIndex - middleIndex;

        ArrayList<String> leftArray = new ArrayList<>();
        ArrayList<String> rightArray = new ArrayList<>();
        ArrayList<String> leftArray2 = new ArrayList<>();
        ArrayList<String> rightArray2 = new ArrayList<>();

        for (int i = 0; i < n1; i++) {
            leftArray.add(hands.get(leftIndex + i));
            leftArray2.add(bids.get(leftIndex + i));
        }
        for (int j = 0; j < n2; j++) {
            rightArray.add(hands.get(middleIndex + 1 + j));
            rightArray2.add(bids.get(middleIndex + 1 + j));
        }

        int i = 0, j = 0, k = leftIndex;
        while (i < n1 && j < n2) {
            if (compare(leftArray.get(i), rightArray.get(j))) {
                hands.set(k, leftArray.get(i));
                bids.set(k, leftArray2.get(i));
                i++;
            } else {
                hands.set(k, rightArray.get(j));
                bids.set(k, rightArray2.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            hands.set(k, leftArray.get(i));
            bids.set(k, leftArray2.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            hands.set(k, rightArray.get(j));
            bids.set(k, rightArray2.get(j));
            j++;
            k++;
        }
    }

    public void getResult() {
        int sum = 0;
        System.out.println(hands);
        System.out.println(bids);
        for (int i = 0; i < bids.size(); i++) {
//            System.out.println(i+1);
//            System.out.println(bids.get(i));
//            System.out.println((i+1)*Integer.parseInt(bids.get(i)));
            sum += (i+1)*Integer.parseInt(bids.get(i));
        }
        System.out.println(sum);
    }
}
