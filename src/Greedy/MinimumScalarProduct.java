/*
 * @author derrick20
 * Google Code Jam 1A 2008, A
 * Idea: given a < b, c < d. What is optimal (minimal) match? ad + bc.
 * Each swap only decreases the cost by a non-negative amount. Thus,
 * Once we can't swap any more pairs, we've reached a minimum for the
 * total cost. Thus, we have proven our greedy solution of pairing the
 * smallest with the biggest is JUST AS good (<= the optimal solution)
 * In fact, it cannot be <, since that violates the definition of an optimal
 * solution O*. Thus, we are done.
 */
import java.io.*;
import java.util.*;

public class MinimumScalarProduct {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(new FileReader("/Users/derrick/IntelliJProjects/src/Greedy/A-large-practice.in"));
        PrintWriter out = new PrintWriter(new FileWriter("/Users/derrick/IntelliJProjects/src/Greedylarge.out"));
        int T = sc.nextInt();
        for (int test = 1; test <= T; test++) {
            int N = sc.nextInt();
            long[] x = new long[N];
            long[] y = new long[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextLong();
            }
            for (int i = 0; i < N; i++) {
                y[i] = sc.nextLong();
            }
            Arrays.sort(x);
            Arrays.sort(y);

            long product = 0;
            for (int i = 0; i < N; i++) {
                product += x[i] * y[N - 1 - i];
            }
            out.println("Case #" + test + ": " + product);
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