/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Frog1 {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] height = new int[N];
        for (int i = 0; i < height.length; i++) {
            height[i] = sc.nextInt();
        }

        int[] dp = new int[N]; // min cost to reach the i+1th stone
        dp[0] = 0;
        dp[1] = Math.abs(height[1] - height[0]);
        for (int i = 2; i < N; i++) {
            int one = Math.abs(height[i] - height[i - 1]);
            int two = Math.abs(height[i] - height[i - 2]);
            dp[i] = Math.min(dp[i - 1] + one, dp[i - 2] + two);
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