/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class OutOfSorts {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name) throws Exception {
        sc = new FastScanner(new FileReader(problem_name + ".in"));
        out = new PrintWriter(new FileWriter(problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner(System.in);
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
        setupIO();
//        setupIO("sort");

        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }
        coordinateCompress(arr);
        int[] goal = new int[N];
        for (int i = 0; i < goal.length; i++) {
            goal[i] = arr[i];
        }
        Arrays.sort(goal);
        HashMap<Integer, Integer> freqMap = new HashMap<>();
        for (int val : arr) {
            update(freqMap, val);
        }

        HashMap<Integer, Integer> visited = new HashMap<>();
        int left = 0;
        int right = N - 1;
        int i = 0;
        int j = N - 1;
        int ct = 0;
        while (i < j && left < right) {
            // we have markers for numbers that have been processed already, so skip those
            while (visited.containsKey(arr[i]) && visited.get(arr[i]).equals(freqMap.get(arr[i]))) {
                i++;
                if (i == arr.length) {
                    break;
                }
            }
            while (visited.containsKey(arr[j]) && visited.get(arr[j]).equals(freqMap.get(arr[j]))) {
                j--;
                if (j < 0) {
                    break;
                }
            }

            // move pointers up until we reach a subarray that's actually unsorted
            while (arr[i] == goal[left]) {
                left++;
                i++;
            }
            while (arr[j] == goal[right]) {
                right--;
                j--;
            }
            if (i >= j || left >= right) {
                break;
            }

            update(visited, goal[i]);
            update(visited, goal[j]);
            left++;
            right--;
            ct++;
        }
        out.println(ct);
        System.out.println("ANS: " + ct);
        System.out.println("BRUTE FORCE: " + bruteForce(arr));
        out.close();
    }

    static void update(HashMap<Integer, Integer> map, int val) {
        if (!map.containsKey(val)) {
            map.put(val, 1);
        }
        else {
            map.put(val, map.get(val) + 1);
        }
    }

    static int bruteForce(int[] arr) {
        int[] copy = new int[arr.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = arr[i];
        }
        boolean sorted = false;
        int ct = 0;
        System.out.println(Arrays.toString(copy));
        while (!sorted) {
            sorted = true;
            ct++;
            for (int i = 0; i < copy.length - 2; i++) {
                if (copy[i + 1] < copy[i]) {
                    swap(copy, i, i + 1);
                }
            }
            System.out.println(Arrays.toString(copy));
            for (int i = copy.length - 2; i >= 0; i--) {
                if (copy[i + 1] < copy[i]) {
                    swap(copy, i, i + 1);
                }
            }
            for (int i = 0; i < copy.length - 2; i++) {
                if (copy[i + 1] < copy[i]) {
                    sorted = false;
                }
            }
            System.out.println(Arrays.toString(copy));
        }
        return ct;
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }

    static void coordinateCompress(int[] arr) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]);
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int val : set) {
            map.put(val, map.size());
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = map.get(arr[i]);
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        FastScanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException {
            return br.readLine();
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
