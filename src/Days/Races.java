package Days;

import java.util.ArrayList;
import java.util.Arrays;

public class Races {

    public ArrayList<Integer> times, distances;

    public Races() {
        times = new ArrayList<>(Arrays.asList(62, 64, 91, 90));
        distances = new ArrayList<>(Arrays.asList(553, 1010, 1473, 1074));
        getResult();
    }

    public int getRace(int timeMax, int distanceMax) {
        int distance = 0;
        int sum = 0;
        for (int buttonPressedTime = 0; buttonPressedTime <= timeMax; buttonPressedTime++) {
            distance = (timeMax - buttonPressedTime) * buttonPressedTime;
            if (distance > distanceMax) sum += 1;
        }
        return sum;
    }

    public void getResult() {
        int result = 1;
        for (int i = 0; i < times.size(); i++) {
            result *= getRace(times.get(i), distances.get(i));
        }
        System.out.println(result);
    }
}
