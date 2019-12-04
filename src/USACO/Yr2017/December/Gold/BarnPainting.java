/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class BarnPainting {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name) throws Exception {
        sc = new FastScanner(new FileReader(problem_name + ".in"));
        out = new PrintWriter(new FileWriter(problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner(System.in);
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
//        setupIO();
        setupIO("barnpainting");

        int N = sc.nextInt();
        int K = sc.nextInt();
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < N - 1; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjList[u].add(v);
            adjList[v].add(u);
        }
        color = new int[N];

        for (int i = 0; i < K; i++) {
            int u = sc.nextInt() - 1;
            int c = sc.nextInt();
            color[u] = c;
        }

        dp = new long[3][N];
        solve(0, -1);
        long ans = 0;
        for (int i = 0; i < 3; i++) {
            ans += dp[i][0];
            ans %= mod;
        }
        out.println(ans);

        out.close();
    }

    static ArrayList<Integer>[] adjList;
    static int[] color;
    static long mod = (long) 1e9 + 7;
    static long[][] dp;

    // How many ways to solve problem on the subtree?
    static void solve(int node, int par) {
        dp[0][node] = 1;
        dp[1][node] = 1;
        dp[2][node] = 1;
        // If we are already colored, the other ways will be 0 always
        if (color[node] != 0) {
            for (int col = 1; col <= 3; col++) {
                if (col != color[node]) {
                    // Set the REAL color which is col - 1 to be 0, if
                    // it's anything EXCEPT for the real color. If it
                    // is the real color, leave it as a 1
                    dp[col - 1][node] = 0;
                }
            }
        }
        for (int adj : adjList[node]) {
            if (adj != par) {
                solve(adj, node);
                // For all of our colors, find individually the product of ways to color subchildren, then
                // add to our total
                for (int myCol = 0; myCol < 3; myCol++) {
                    // Each child is an independent coloring, so find ways for them, then multiply onto us
                    // try all 3 colorings, and see how many ways there are.
                    long sum = 0;
                    for (int theirCol = 0; theirCol < 3; theirCol++) {
                        if (myCol != theirCol) {
                            sum += dp[theirCol][adj];
                            sum %= mod;
                        }
                    }
                    dp[myCol][node] *= sum; // Because of this multiplicative property,
                    // if we were already colored, only things that did NOT include our color
                    // as a possibility are counted
                    dp[myCol][node] %= mod;
                }
            }
        }
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
