package Days;

import java.util.ArrayList;
import java.util.Arrays;

public class Races {

//    public ArrayList<Integer> times, distances;
    public long time;
    public double distance;

    public Races() {
//        times = new ArrayList<>(Arrays.asList(62, 64, 91, 90));
//        distances = new ArrayList<>(Arrays.asList(553, 1010, 1473, 1074));
        time = 62649190;
        distance = 553101014731074.;
        System.out.println(getRace(time, distance));
//        getResult();
    }

    public long getRace(long timeMax, double distanceMax) {
        long distance = 0;
        long sum = 0;
        for (int buttonPressedTime = 0; buttonPressedTime <= timeMax; buttonPressedTime++) {
            distance = (timeMax - buttonPressedTime) * buttonPressedTime;
            if (distance > distanceMax) sum += 1;
        }
        return sum;
    }

//    public void getResult() {
//        int result = 1;
//        for (int i = 0; i < times.size(); i++) {
//            result *= getRace(times.get(i), distances.get(i));
//        }
//        System.out.println(result);
//    }
}
