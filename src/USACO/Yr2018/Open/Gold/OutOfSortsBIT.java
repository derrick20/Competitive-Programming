/*
 * @author derrick20
 * Argh Key idea I completely missed: a bubble sort moves something up and
 * down. Thus, we could visualize more specifically and less globally to understand it/
 * Each number that's out of place mus walk through many points. It is similar to
 * the syle of thinking of inversions. At a given bridge between two indices,
 * there are some number that must cross across this bridge to arrive at their proper position.
 * So, from an individual perspective, we have a clearer view of how many routines must be done
 * to fix it. However many things must invert through us either upward or downward is the count
 * In fact the amount that must go up is EQUAL to going down, since each item that is too big
 * displaces a normal item. There is a 1-to-1 correspondence of items to big and below and those
 * too small and above. Each pass will fix 1 from each side. Thus, the position with the most issues
 * will dominate the sort.
 * KEY ISSUE - case 9 troll is that you have to make sure that 1 pass still is needed if ALREADY SORTED
 * A way to prevent is always test the obvious edge cases against your brute force: sorted ascending/descending!
 */
import java.io.*;
import java.util.*;

public class OutOfSortsBIT {
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
//        setupIO();
        setupIO("sort");

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
        for (int i = 0; i < goal.length - 1; i++) {
            if (goal[i] > goal[i + 1]) {
                System.out.println(i);
            }
        }

        int maxSteps = 0;
        int maxVal = goal[N - 1];
        bit = new int[maxVal + 1];
        for (int i = 0; i < arr.length; i++) {
            int val = arr[i];
            int expected = goal[i];
            update(val);
            // Find how many things at least the value desired here that were placed strictly too early and must bubble up
            int flips = i + 1 - prefix(expected); // query(expected, maxVal);
//            System.out.println("Complementary: " + flips);
//            System.out.println("Regular: " + query(expected + 1, maxVal));
            maxSteps = Math.max(maxSteps, flips);
        }
        if (maxSteps == 0) {
            maxSteps = 1; // since we still do one pass
        }
        out.println(maxSteps);
//        System.out.println("BRUTE FORCE: " + bruteForce(arr));
        out.close();
    }

    static int[] bit;

    static void update(int i) {
        while (i < bit.length) {
            bit[i]++;
            i += i & -i;
        }
    }

    static int query(int i, int j) {
        return prefix(j) - prefix(i - 1);
    }

    static int prefix(int i) {
        int ans = 0;
        while (i > 0) {
            ans += bit[i];
            i -= i & -i;
        }
        return ans;
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
            map.put(val, map.size() + 1);
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
