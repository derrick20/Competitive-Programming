import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class XOR {
    // Before submitting, make sure the main method hasn't been changed!
    // DO NOT MODIFY BELOW THIS LINE

    public static void main(String[] args) throws IOException {
        //"/Users/derrick/IntelliJProjects/src/PClassic/XORIN.txt"));//
        BufferedReader br = new BufferedReader(new FileReader("XORIN.txt"));
        String[] data = br.readLine().split(" ");
        int testCases = Integer.parseInt(data[0]);
        for( ; testCases > 0; testCases--) {
            int n = Integer.parseInt(br.readLine());
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(br.readLine());
            }
            System.out.println(solve(n, arr));
        }
        br.close();
    }
    // DO NOT MODIFY ABOVE THIS LINE
    
    // Fill out the body of this method
    public static long solve(int n, int[] arr) {
        int total = 0;
        for (int val : arr) {
            total ^= val;
        }
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> map2 = new HashMap<>();
        int total2 = 0;
        ArrayList<Integer> foo = new ArrayList<>();
        foo.add(0);
        map.put(0, foo);
        for (int i = 0; i < arr.length; i++) {
            total2 ^= arr[i];
            if (map.containsKey(total2)) {
                map.get(total2).add(i + 1);
            }
            else {
                ArrayList<Integer> add = new ArrayList<>();
                add.add(i + 1);
                map.put(total2, add);
            }
        }

        int total3 = 0;
        ArrayList<Integer> foo2 = new ArrayList<>();
        foo2.add(0);
        map2.put(0, foo2);
        for (int i = arr.length - 1; i >= 0; i--) {
            total3 ^= arr[i];
            if (map2.containsKey(total3)) {
                map2.get(total3).add(arr.length - i);
            }
            else {
                ArrayList<Integer> add = new ArrayList<>();
                add.add(arr.length - i);
                map2.put(total3, add);
            }
        }
//        System.out.println(map);
//        System.out.println(map2);
//        System.out.println(total);
        long sum = 0;
        for (int key : map.keySet()) {

            int map2key = total ^ key;
//            System.out.println("Key: "+key);
//            System.out.println("mapkey; "+map2key);
            if (map2.containsKey(map2key)) {
                for (int val1 : map.get(key)) {
                    for (int val2 : map2.get(map2key)) {
                        if (val1 + val2 < arr.length) {
                            sum += arr.length - 1 - val1 - val2;
                        }
                    }
                }
            }
        }
        return sum;
    }

}
