/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Knapsack2Redo {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int W = sc.nextInt();
        long[] weight = new long[N];
        int[] value = new int[N];
        int totalVal = 0;
        for (int i = 0; i < N; i++) {
            weight[i] = sc.nextInt();
            value[i] = sc.nextInt();
            totalVal += value[i];
        }

        long[] dp = new long[totalVal + 1];
        Arrays.fill(dp, (long) 1e18);
        dp[0] = 0;
        // What's the best value achievable with weight W?
        // AH, dimension flip. Now we want minimum weight for some value
        for (int item = 0; item < N; item++) {
            int val = value[item];
            long wt = weight[item];
            for (int oldVal = totalVal - val; oldVal >= 0; oldVal--) {
                dp[oldVal + val] = Math.min(dp[oldVal + val], dp[oldVal] + wt);
            }
        }
        long best = 0;
        for (int val = totalVal; val >= 0; val--) {
            if (dp[val] <= W) {
                best = val;
                break;
            }
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