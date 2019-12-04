import java.util.*;
class carpool {

    public static void main(String[] args) throws Exception {
        int[][] trips = {{2,1,5},{3,5,7}};
        int capacity = 3;
        System.out.println(carPooling(trips, capacity));
    }

    public static boolean carPooling(int[][] trips, int capacity) {
        int[] start = new int[1001];
        int[] stop = new int[1001];
        int[] amt = new int[1001];
        for (int[] trip : trips) {
            start[trip[1]] = 1;
            stop[trip[2]] = 1;
            amt[trip[1]] += trip[0];
            amt[trip[2]] += -1*trip[0];
        }
        int car = 0;

        for (int i = 0; i <= 1000; i++) {
            if (start[i] == 1 || stop[i] == 1) {
                car += amt[i];
            }
            if (car > capacity)
                return false;
        }
        return true;
    }
}
