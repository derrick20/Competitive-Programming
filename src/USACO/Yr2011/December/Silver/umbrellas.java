/*
 * @author derrick20
 * 2011 December Silver #3
 */
import java.io.*;
import java.util.*;

public class umbrellas {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] pos = new int[N];
        for (int i = 0; i < N; i++) {
            pos[i] = sc.nextInt();
        }
        Arrays.sort(pos);

        // cost[i] represents cost of umbrella spanning i stalls
        int[] cost = new int[M + 1];
        for (int i = 1; i <= M; i++) {
            cost[i] = sc.nextInt();
        }
        // todo SHOOOT the only thing i was missing was this
        // todo the fact that some intervals may be covered more easily
        // todo with a bigger umbrella means a cover may be done better with a bigger umbrella!#!!@$)$H(@O
        for (int i = M - 1; i >= 1; i--) {
            cost[i] = Math.min(cost[i], cost[i + 1]);
        }

        // Basically, O(N^2), trying each starting point and each ending point,
        // then rely on DP of the sub problem resulting with a shrunken set of cows
        // dp[i] stores the MIN cost way to group together the first i cows
        long[] dp = new long[N + 1];
        dp[0] = 0;
        for (int used = 1; used <= N; used++) {
            // Try removing
            dp[used] = (long) 1e18;
            int endCow = pos[used - 1];
            // Transition relies on using some subset of cows by covering the
            // end ones with one umbrella
            for (int group = 1; used - group >= 0; group++) {
                int startCow = pos[used - group];
                long umbrellaCost = cost[endCow - startCow + 1];
                dp[used] = Math.min(dp[used], dp[used - group] + umbrellaCost);
            }
        }
        out.println(dp[N]);
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
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException {
            return br.readLine();
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
