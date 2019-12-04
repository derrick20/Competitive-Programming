/*
 * @author derrick20
 * LeetCode 377. Combination Sum IV
 * Given an array of numbers, how many ways to reach a certain target
 * using those number (repeated usage allowed)?
 * Basically, we climb upwards transitioning off of previously produced
 * sums. Since we go upwards, we enable the use of sums we just created
 * from a specific value, which embodies the idea of duplicate usage
 * (reusing visited states kinda)
 */
import java.io.*;
import java.util.*;

public class CombinationSum {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int goal = sc.nextInt();

        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }

        /* Bottom-up approach:
        int[] dp = new int[goal + 1];
        dp[0] = 1;
        // Build off of each
        for (int sum = 0; sum <= goal; sum++) {
            for (int val : arr) {
                if (sum + val <= goal && dp[sum] > 0) {
                    // Ways to reach this new value now will
                    // include the ways to reach smaller (then to here
                    // by means of a transition
                    dp[sum + val] += dp[sum];
                }
            }
        }
        int ans = dp[goal];
        out.println(ans);*/

        /*
        Top Down approach
         */
        int[] memo = new int[goal + 1];
        Arrays.fill(memo, -1);
        memo[0] = 1;
        int ans = solve(goal, memo, arr);
        out.println(ans);
        out.close();
    }

    static int solve(int sum, int[] memo, int[] arr) {
        if (sum == 0) {
            return 1;
        }
        else if (sum < 0) {
            return 0;
        }
        else if (memo[sum] != -1) {
            return memo[sum];
        }
        else {
            int ret = 0;
            for (int val : arr) {
                ret += solve(sum - val, memo, arr);
            }
            return memo[sum] = ret;
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
