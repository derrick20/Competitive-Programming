/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class CompleteProjects2 {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        int initial = sc.nextInt();
        need = new int[N];
        delta = new int[N];
        for (int i = 0; i < N; i++) {
            need[i] = sc.nextInt();
            delta[i] = sc.nextInt();
        }
        visited = new boolean[N];
        int ans = solve(initial);
        out.println(ans);
        out.close();
    }

    static int N;
    static int[] need, delta;
    static boolean[] visited;

    static int solve(int curr) {
        int ans = 0;
        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                if (curr >= need[i] && curr + delta[i] >= 0) {
                    visited[i] = true;
                    // skip it, or not. Perhaps one will increase total count more!
                    ans = Math.max(1 + solve(curr + delta[i]), solve(curr));
                    visited[i] = false;
                }
            }
        }
        int ct = 0;
//        for (boolean did : visited) {
//            if (did) {
//                ct++;
//            }
//        }
        return Math.max(ans, ct);
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