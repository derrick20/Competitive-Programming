/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Grid1 {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int H = sc.nextInt();
        int W = sc.nextInt();
        int[][] grid = new int[H][W];
        for (int i = 0; i < H; i++) {
            String s = sc.next();
            for (int j = 0; j < W; j++) {
                if (s.charAt(j) == '#') {
                    grid[i][j] = -1;
                }
            }
        }

        long[][] dp = new long[H][W];
        dp[0][0] = 1;
        for (int r = 0; r < H; r++) {
            for (int c = 0; c < W; c++) {
                if (grid[r][c] == -1) continue;
                if (r > 0) {
                    dp[r][c] = addSelf(dp[r][c], dp[r - 1][c]);
                }
                if (c > 0) {
                    dp[r][c] = addSelf(dp[r][c], dp[r][c - 1]);
                }
            }
        }
        out.println(dp[H - 1][W - 1]);
        out.close();
    }

    static long mod = (long) 1e9 + 7;

    static long addSelf(long x, long val) {
        x += val;
        if (x >= mod) {
            x -= mod;
        }
        return x;
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