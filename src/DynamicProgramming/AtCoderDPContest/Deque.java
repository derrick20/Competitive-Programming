/**
 * @author derrick20
 * Come back to this and do the special trick for stacks
 */
import java.io.*;
import java.util.*;

public class Deque {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        long[] points = new long[N];
        for (int i = 0; i < points.length; i++) {
            points[i] = sc.nextLong();
        }
        long[][] dp = new long[N + 1][N + 1];
        // dp[i][j] stores the optimal value for whatever player is playing,
        // having played until we've shrunk the bounds to [i + 1, N - j - 1]
        // WRONG: dp[i][j] should store the optimally played value of X - Y
        // Note, if Taro maxes X - Y, and can onyl control X, it's just picking
        // optimal to get highest score
        // If Jiro minimizes X - Y, and can only control Y, he's just
        // trying to maximize the end score of Y.
        // Also, just keep in mind, this is a VALUE DP.
        // The values are huge (10^9), but N is small...

        // So, try all transitions
        for (int i = N - 1; i >= 0; i--) {
            for (int j = i; j < N; j++) {
                if (i == j) {
                    dp[i][j] = points[i];
                }
                else {
                    long takeLeft = -dp[i + 1][j] + points[i];
                    long takeRight = -dp[i][j - 1] + points[j];
                    dp[i][j] = Math.max(takeLeft, takeRight);
                }
            }
        }
        out.println(dp[0][N - 1]);
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