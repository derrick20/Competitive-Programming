/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class CompleteProjects {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        int initial = sc.nextInt();
        need = new int[N];
        delta = new int[N];
        TreeMap<Integer, Integer> project = new TreeMap<>();
        for (int i = 0; i < N; i++) {
            need[i] = sc.nextInt();
            delta[i] = sc.nextInt();

        }
        visited = new boolean[N];
        boolean ans = solve(initial);
        out.println(ans ? "YES" : "NO");
        out.close();
    }

    static int N;
    static int[] need, delta;
    static boolean[] visited;

    static boolean solve(int curr) {
        boolean ans = false;
        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                if (curr >= need[i] && curr + delta[i] >= 0) {
                    visited[i] = true;
                    ans |= solve(curr + delta[i]);
                    visited[i] = false;
                }
            }
        }
        boolean allVisited = true;
        for (boolean did : visited) {
            if (!did) {
                allVisited = false;
                break;
            }
        }
        return ans | allVisited;
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