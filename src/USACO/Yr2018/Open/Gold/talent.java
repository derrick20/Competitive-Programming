/*
 * @author derrick20
 * @problem MilkingOrder USACO 2018 Gold Open
 * Failed. The DP wasn't as simple as i thought. It needs to use binary search ?!? crap
 */
import java.io.*;
import java.util.*;

public class talent {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
//        Scanner sc = new Scanner(new FileReader("talent.in"));// new InputStreamReader(System.in)); //
//        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("talent.out")));
        int N = sc.nextInt();
        int minWeight = sc.nextInt();
        Pair[] cows = new Pair[N];
        for (int i = 0; i < N; i++) {
            int wt = sc.nextInt();
            int val = sc.nextInt();
            cows[i] = new Pair(wt, val);
        }
        // Sorting with objects is merge sort, so safe!
        Arrays.sort(cows);

        int totalWeight = 0;
        int totalValue = 0;
        for (int j = 0; j < N; j++){
            if (totalWeight >= minWeight) {
                out.println(totalWeight + " " + minWeight);
                break;
            }
            else {
                totalWeight += cows[j].wt;
                totalValue += cows[j].val;
            }
        }
        out.println(1000 * (double) totalValue / totalWeight);

//        memo = new int[N][totalWeight+1]; // it'll go up to the max possible weight
//        best = 0;
//        solve(0, 0, 0);
//        out.println((int) (1000*best));
        out.close();
    }

    static class Pair implements Comparable<Pair> {
        int wt, val;

        public Pair(int w, int v) {
            wt = w;
            val = v;
        }

        // better value goes earlier
        public int compareTo(Pair p2) {
            if ((double) val / wt >= (double) p2.val / p2.wt) {
                return -1;
            }
            else {
                return 1;
            }
        }
    }

//    static int[] weights;
//    static int[] values;
//    static double best;
//    static int[][] memo;
//    public static int solve(int i, int weight, int value) {
//        // Before anything, make sure we're in bounds
//        if (i == N) {
//            return value;
//        }
//        // We can't just quit if we've seen the state before. We might be reaching a better
//        // best value, so don't quit yet
//        if (memo[i][weight] != 0) {
//            if (value < memo[i][weight]) {
//                return 0;
//            }
//            memo[i][weight] = value; // Need to set it then, it's the first time we've reached this state
//        }
//        if (weight >= minWeight) {
//            best = Math.max(best, (double) value / weight);
//        }
//        solve(i+1, weight + weights[i], value + values[i]);
//        solve(i+1, weight, value); // Skipping this cow
//        return 0;
//    }

    static class Scanner {
        BufferedReader br;
        StringTokenizer st;

        Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        Scanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}