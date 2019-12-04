/**
 * @author derrick20
 * DON'T USE ANDS IN THE FOR LOOP, THAT BREAKS OUT!!
 */
import java.io.*;
import java.util.*;

public class Vacation {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[][] value = new int[3][N]; // store happiness for each activity for each day
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 3; j++) {
                value[j][i] = sc.nextInt();
            }
        }

        int[][] dp = new int[3][N];
        // dp[x][i] stores the max happiness by this day, having done activity x on the ith day
        for (int activity = 0; activity < 3; activity++) {
            dp[activity][0] = value[activity][0];
        }
        for (int day = 1; day < N; day++) {
            // For each daily activity,
            for (int curr = 0; curr < 3; curr++) {
                // pick an activity different from today,
                for (int prev = 0; prev < 3; prev++) {
                    if (prev == curr) continue;
                    // and maximize the happiness to reach the state of this day and this activity
                    dp[curr][day] = Math.max(dp[curr][day], value[curr][day] + dp[prev][day - 1]);
                }
//                System.out.println("Day: " + day + " Curr: " + curr + Arrays.toString(dp[curr]));
            }
        }
        int best = 0;
        for (int activity = 0; activity < 3; activity++) {
            best = Math.max(best, dp[activity][N - 1]);
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