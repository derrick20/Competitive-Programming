/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class GasPipeline {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        while (T-->0) {
            int N = sc.nextInt();
            long pipe = sc.nextLong();
            long pillar = sc.nextLong();
            String road = sc.next() + "0";
            long[][] dp = new long[2][N + 1];
            // dp[i][j] stores, if we are height state i, and have
            // solved j parts, what is the min cost up till now.
            // Each index responsible for its pillar and the pipe behind
            long oo = (long) 1e18;
            dp[0][0] = pillar;
            dp[1][0] = oo;
            for (int i = 1; i <= N; i++) {
                if (road.charAt(i) == '1' || road.charAt(i - 1) == '1') {
                    dp[0][i] = oo; // not allowed
                    dp[1][i] = 2 * pillar + Math.min(dp[0][i - 1] + 2 * pipe, dp[1][i - 1] + pipe);
                }
                else if (road.charAt(i) == '0' && road.charAt(i - 1) == '0') {
                    dp[0][i] = pillar + Math.min(dp[0][i - 1] + pipe, dp[1][i - 1] + 2 * pipe);
                    dp[1][i] = 2 * pillar + Math.min(dp[0][i - 1] + 2 * pipe, dp[1][i - 1] + pipe);
                }

//                if (T == 3)
//                    System.out.println(i + " 1 Height: " +  dp[0][i] + " 2 Height: " + dp[1][i]);
            }
            out.println(dp[0][N]);
        }
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