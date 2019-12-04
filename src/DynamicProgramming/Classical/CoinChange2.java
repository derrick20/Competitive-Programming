/*
 * @author derrick20
 * 518. Coin Change 2 Leetcode
 * Find number of ways to make a total amount of money using some denominations
 * Key idea: In this case, we want the number of unique sets of coins, where the coins
 * are indistinguishable. So, we don't want to be able to keep adding any type of coin
 * Instead, go by cases: see how far we can go adding only the first coin type, then
 * the second and so on. In this way, we generate only the UNIQUE sets of coin groupings
 *
 * A bit more insight on this is that we can think about picking the lexicogrpahically
 * smallest of each version of a set. To put this more organizedly, lol, let's do it
 * by solution bagging! essentially, we go from each coin one by one and solve the problem with it!
 * This essentially simulates adding on to the end of each seuqence, which is equivalent to
 * being the lexicographically shortest way.
 * Even better, we can think about the DAG of this. Either we can keep using another of this
 * coin, or we can move on an try using the next coin. This is important to realize as the
 * graph is always able to be created such that it represents the transitions. THus, we can only
 * reach a state in ONE WAY by transitioning throughout the graph
 */
import java.io.*;
import java.util.*;

public class CoinChange2 {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int goal = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }

        int[] dp = new int[goal + 1];
        // dp stores the number of ways to reach a given sum with the allowed
        // denominations
        dp[0] = 1;
        // All other things are assumed to have 0 ways to reach initially
//        for (int sum = 1; sum <= goal; sum++) {
//            for (int val : arr) {
//                if (sum - val >= 0) {
//                    dp[sum] += dp[sum - val];
//                }
//            }
//        }
        for (int val : arr) {
            for (int sum = 1; sum <= goal; sum++) {
                if (sum - val >= 0) {
                    dp[sum] += dp[sum - val];
                }
            }
        }
        out.println(dp[goal]);
        out.close();
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
