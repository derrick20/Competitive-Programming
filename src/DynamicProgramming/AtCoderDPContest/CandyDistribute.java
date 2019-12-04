/**
 * @author derrick20
 * Lesson learned: always write an add_self and sub_self method
 */
import java.io.*;
import java.util.*;

public class CandyDistribute {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int K = sc.nextInt();
        int[] limit = new int[N];
        for (int i = 0; i < limit.length; i++) {
            limit[i] = sc.nextInt();
        }
        long[] dp = new long[K + 1];
        // dp[x] stores how many ways to distribute among the current
        // solution bag of children that have been given an appropriate amount
        // of candy to, so that the total candy given is K currently.
        // The approach is that, for each of 100 children, we update the
        // solution bag of ways to end with each candy amount by adding to
        // some previous solution bags.

        dp[0] = 1; // base case, 1 way to distribute 0 candies
        for (int i = 0; i < N; i++) {
            int max = limit[i];
            // We go from each starting point, then add some amount of candy,
            // reaching this point from a lesser candy amount. This works nicely,
            // so that the order of our states doesn't require more memory
            long[] prefixSums = new long[K + 1];
            for (int start = K - 1; start >= 0; start--) {
                // Since we are traversing downwards, once the amount added
                // causes us to exceed K, we can break as all future attempts would
                // be invalid (out of bounds)
                // The case where the amount added = 1 is tricky. What's happening
                // is we're attempting to overwrite our dp array by cleverly combining
                // the possibility of adding 0, and the possibility of adding something
                // TO REACH this point. Thus, we would double count if we tried added = 0!
                /*
                for (int added = 1; added <= max && start + added <= K; added++) {
                    dp[start + added] += dp[start];
                    dp[start + added] %= mod;
                }
                */
                // Now to optimize via prefix sums. Note that the above is literally
                // a range update by dp[start]
                int L = start + 1;
                int R = start + Math.min(max, K - start);
                prefixSums[L] += dp[start];
                prefixSums[L] %= mod;
                if (R + 1 <= K) {
                    prefixSums[R + 1] -= dp[start];
                    if (prefixSums[R + 1] < 0) {
                        prefixSums[R + 1] += mod;
                    }
                }
            }
//            System.out.println(Arrays.toString(prefixSums));
            for (int k = 1; k <= K; k++) {
                prefixSums[k] += prefixSums[k - 1];
                prefixSums[k] %= mod;
                dp[k] += prefixSums[k];
                dp[k] %= mod;
            }
//            System.out.println(Arrays.toString(dp));
        }
        out.println(dp[K]);
        out.close();
    }

    static long mod = (long) 1e9 + 7;

    static long fastExponentiate(long x, long p) {
        long ans = 1;
        while (p > 0) {
            if ((p & 1) > 0) {
                ans = (ans * x) % mod;
            }
            x = (x * x) % mod;
        }
        return ans;
    }

    static long divide(long x, long y) {
        return (x * fastExponentiate(y, mod - 2)) % mod;
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
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}