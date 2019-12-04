/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class tutorial {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int C = sc.nextInt();
        while (C-->0) {
            N = sc.nextInt();
            K = sc.nextInt();

            memo = new long[N][K + 1];
            value = new long[N];

            for (int i = 0; i < N; i++) {
                value[i] = sc.nextLong();
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j <= K; j++) {
                    memo[i][j] = -1;
                }
            }
            out.println(solve(0, K));
        }
        out.close();
    }
    static int N;
    static int K;
    static long[][] memo;
    static long[] value;
    static int inf = (int) 1e9;

    static long solve(int i, int rem) {
        if (i == N && rem == 0) {
            return 0;
        }
        // ways we terminate:
        // didn't make enough teams, or didn't use enough people
        if (i == N || rem <= 0) {
            // this means we cheated, and didn't use K groups exactly
            return inf;
        }
        if (memo[i][rem] != -1) {
            return memo[i][rem];
        }
        else {
            long max = 0;
            long min = inf;
            long best = inf;
            for (int size = 1; i + size - 1 < N; size++) {
                max = Math.max(max, value[i + size - 1]);
                min = Math.min(min, value[i + size - 1]);
                // the answer for this is the maximum of the differences
                // over all future groups
                long diff = Math.max(max - min, solve(i + size, rem - 1));
                best = Math.min(best, diff);
            }
            return memo[i][rem] = best;
        }
    }

    static class Scanner {
        BufferedReader br;
        StringTokenizer st;

        Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        Scanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
