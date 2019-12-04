/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Coins {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        // Ah Errichto clever clever - just read the p when you're using it!
//        double[] p = new double[N];
//        for (int i = 0; i < p.length; i++) {
//            p[i] = sc.nextDouble();
//        }

        double[] dp = new double[N + 1];
        // dp[i] stores probability to get i heads, N - i tails
        // We implicitly store, with how many coins can we achieve this outcome
        // But really, we just go through all the coins and transition
        // the current heads to the new state, of using 1 more coin.
        dp[0] = 1;
        // A way to think about what's happening to the distribution of coins over time
        // is that, for every probability that's already in the dp array,
        // it is splitting itself and moving into two points:
        // Either it generates a head, which multiplies by p and sends it to the next
        // level, or it generates a tail * (1 - p), and changes the prob of the current
        // head count. Notice that this nets a sum of (p + 1 - p) * dp[heads].
        // However, when getting a tail, we don't add, but rather REPLACE the old
        // value of dp[heads]. So, what's happening is we add (p + 1 - p) * dp[heads],
        // but subtracted the old dp[heads], since that's no longer possible!
        for (int coin = 0; coin < N; coin++) {
            double p = sc.nextDouble();
            for (int heads = N - 1; heads >= 0; heads--) {
                // We must go downwards, because at each position,
                // we must rely on the answer for a smaller problem, then
                // update us!

                // There's a new way to reach the next coin
                dp[heads + 1] += dp[heads] * p;
                // In order to preserve this # heads, it's harder
                dp[heads] *= (1 - p);
            }
        }
        double sum = 0;
        for (int heads = N / 2 + 1; heads <= N; heads++) {
            sum += dp[heads];
        }
        out.println(sum);
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
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}