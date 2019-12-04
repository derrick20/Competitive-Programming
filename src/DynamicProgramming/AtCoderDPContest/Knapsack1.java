/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Knapsack1 {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int W = sc.nextInt();
        int[] weight = new int[N];
        long[] value = new long[N];
        for (int i = 0; i < N; i++) {
            weight[i] = sc.nextInt();
            value[i] = sc.nextLong();
        }

        long[] dp = new long[W + 1];
        // What's the best value achievable with weight W?
        for (int item = 0; item < N; item++) {
            long val = value[item];
            int wt = weight[item];
            for (int nextWt = W; nextWt - wt >= 0; nextWt--) {
                // We can either add to the first state or something that was
                // reachable already
                // This statement makes me feel safer but it's actually NOT necessary,
                // since we're jsut maximizing everything
                if (nextWt - wt == 0 || dp[nextWt - wt] != 0) {
                    dp[nextWt] = Math.max(dp[nextWt], dp[nextWt - wt] + val);
                }
            }
        }
        long best = 0;
        for (long val : dp) {
            best = Math.max(best, val);
        }
        out.println(best);
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