/**
 * @author derrick20
 * Arghh come back to this. Don't get the bellman-ford stuff
 */
import java.io.*;
import java.util.*;

public class CoinsRespawn {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        int M = sc.nextInt();
        int P = sc.nextInt();
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int wt = sc.nextInt();
            adjList[u].add(new Edge(v, wt - P));
            // Each edge also costs a bit due to the payment at the end
        }

        visited = new int[N];
        hasPositiveCycle = new boolean[N];
        dist = new int[N];
        Arrays.fill(dist, -1);
        dist[0] = 0;
    }

    static ArrayList<Edge>[] adjList;
    static int[] visited;
    static boolean[] hasPositiveCycle;
    static int[] dist; // from the start
    static int N;

    // Return the max cost to the end. However, if we can't reach, or if
    // it is negative, return -1.
    static int solve(int node) {
        if (node == N - 1) {
            return 0;
        }
        int ans = -1;
        visited[node] = 1;
        for (Edge e : adjList[node]) {
            if (visited[e.to] == 1) {
                // Meaning we looped back into ourselves
                if (dist[e.to] != -1 && dist[node] + e.wt > dist[e.to]) {
                    hasPositiveCycle[node] = true;
                }
            }
            else if (visited[e.to] == 0) {
                ans = Math.max(ans, solve(e.to));
                // Update the distance to this point, if it's possible!
                if (dist[e.to] != -1) {
                    dist[node] = dist[e.to] + e.wt;
                }
            }
        }
        visited[node] = 2;
        return ans;
    }

    static class Edge {
        int to, wt;

        public Edge(int t, int w) {
            to = t; wt = w;
        }

        public String toString() {
            return "(" + to + ", " + wt + ")";
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
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}