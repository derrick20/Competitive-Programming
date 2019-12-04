/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Frog2 {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int K = sc.nextInt();
        int[] height = new int[N];
        for (int i = 0; i < height.length; i++) {
            height[i] = sc.nextInt();
        }

        int[] dp = new int[N]; // min cost to reach the i+1th stone
        dp[0] = 0;
        for (int i = 1; i < N; i++) {
            dp[i] = (int) 2e9;
            for (int jump = 1; jump <= K && i - jump >= 0; jump++) {
                int step = Math.abs(height[i] - height[i - jump]);
                dp[i] = Math.min(dp[i], step + dp[i - jump]);
            }
        }
        out.println(dp[N - 1]);
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