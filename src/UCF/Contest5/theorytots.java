/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class theorytots {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int C = sc.nextInt();
        while (C-->0) {
            N = sc.nextInt();
            K = sc.nextInt();
            int[] x = new int[N];
            int[] y = new int[N];
            int[] cycle = new int[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
                cycle[i] = sc.nextInt();
            }
            dist = new double[N][N];
            for (int i = 0; i < dist.length; i++) {
                for (int j = 1; j < dist[0].length; j++) {
                    double d = distance(x[i], y[i], x[j], y[j]);;
                    dist[i][j] = d;
                    dist[j][i] = d;
                }
            }
            memo = new double[N][1 << (N + 1)];
            double res = solveBottomUp();
//            double res = solveTopDown(1, 0);
            out.printf("%.2f\n", res);
        }
        out.close();
    }
    static int N, K;
    static double[][] memo;
    static double[][] dist;

    static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    static double solveBottomUp() {
        // The mask starts off with the 0th node being visited. The mask
        // can never be 0. The issue is we need to build up from a base case
        memo[0][1] = 0;
        for (int mask = 2; mask < (1 << N); mask++) {
            for (int last = 0; last < N; last++) {
                if ((mask & (1 << last)) > 0) {
                    int prev = mask - (1 << last);
                    memo[last][mask] = 1e9;
                    // this is the state, before we reached this last node
                    for (int v = 0; v < N; v++) {
                        // if there was a previous node we could come from
                        // Look at all possible nodes from which we can build off of
                        if ((prev & (1 << v)) > 0) {
//                            if (memo[last][i] == 0) {
//                                memo[last][i] = memo[v][prev] + dist[v][last];
//                            }
//                            else {
                            memo[last][mask] = Math.min(memo[last][mask], memo[v][prev] + dist[v][last]);
//                            }
                        }
                    }
                }
            }
        }
        int endState = (1 << N) - 1;
        double res = 1e9;
        // For the final step back to the original, iterate over the possible
        // "last" nodes visited, and then add the distance to return
        for (int last = 0; last < N; last++) {
            res = Math.min(res, memo[last][endState] + dist[last][0]);
        }
        return res;
    }

    static double solveTopDown(int bit, int last) {
        double min = 1e9;
        if (memo[last][bit] != 0) {
            return memo[last][bit];
        }
        boolean noneLeft = true;
        for (int nextLast = 0; nextLast < N; nextLast++) {
            if ((bit & (1 << nextLast)) == 0) {
                min = Math.min(min, solveTopDown(bit + (1 << nextLast), nextLast) + dist[nextLast][last]);
                noneLeft = false;
            }
        }
        if (noneLeft) {
            return dist[last][0];
        }
        else {
            return memo[last][bit] = min;
        }
    }

    static class Pair {
        int bit;
        int last;
        public Pair(int b, int l) {
            bit = b;
            last = l;
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

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
