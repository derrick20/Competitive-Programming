/*
 * @author derrick20
 * 322. Coin Change
 * Given different coin denominations, and a goal amount,
 * find fewest coins to make that amount
 */
import java.io.*;
import java.util.*;

public class CoinChange {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int goal = sc.nextInt();

        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }

        ///* Bottom-up approach:
        // dp[X] stores the fewest coins needed to reach X
        int[] dp = new int[goal + 1];
        int oo = (int) 1e9;
        Arrays.fill(dp, oo); // Everything starts with infinity but 0
        dp[0] = 0; // Base case

        // Transition is therefore creating a new amount from
        // a solution bag of minimally formed amounts. That transition
        // has a cost of 1 coin used, and we see if that is an improved method

        // for each starting amount, build the things reachable above us and
        // try to improve their answers
        // todo KEY ISSUE: NEVER ADD TO GO ABOVE,
        //  aSUBTRACT TO SEE WHAT'S
        //  BELOW TO AVOIDDDD OVERFLOWOWOWOWOWOWWWW
        //  One approach is forward/push dynamic programming. We know
        //  a value and use it to update future values.
        //  On the other hand, we could procrastinate and rely ony
        //  earlier people to give us the answer!
//        for (int amount = 1; amount <= goal; amount++) {
//            for (int value : arr) {
//                if (amount - value >= 0) {
//                    dp[amount] = Math.min(dp[amount], 1 + dp[amount - value]);
//                }
//            }
//        }
        // Interestingly, whereas for COIN CHANGE 2 the order mattered
        // here it doesn't since we're just finding the min magnitude of
        // the number of items in the set, not how MANY ways there are!!>#>$
        for (int value : arr) {
            for (int amount = 1; amount <= goal; amount++) {
                if (amount - value >= 0) {
                    dp[amount] = Math.min(dp[amount], 1 + dp[amount - value]);
                }
            }
        }
        int ans = dp[goal];
        if (ans == oo) {
            ans = -1;
        }
        //*/

        /* Top-down approach:

        int[] memo = new int[goal + 1];
        int oo = (int) 1e9;
        Arrays.fill(memo, -1); // Treat everything as unvisited first
        // If we ever visit it, mark it with the possibility (most likely 0)
        memo[0] = 0;
        int ans = solve(goal, memo, arr);
        if (ans == oo) {
            ans = -1;
        }

        out.println(ans);
        */
        out.close();
    }

    static int solve(int amount, int[] memo, int[] arr) {
        int oo = (int) 1e9;
        if (amount == 0) {
            // base case
            return 0;
        }
        else if (amount < 0) {
            // this should never be valid
            return oo;
        }
        else if (memo[amount] != -1) {
            return memo[amount];
        }
        else {
//            System.out.println(amount);
//            System.out.println(Arrays.toString(memo));
            int ret = oo;
            for (int val : arr) {
                ret = Math.min(ret, solve(amount - val, memo, arr) + 1);
            }
            return memo[amount] = ret;
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
