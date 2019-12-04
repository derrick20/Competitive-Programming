/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class LongestPath {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjList[u].add(v);
        }
        maxDist = new int[N];
        Arrays.fill(maxDist, -1);
        for (int i = 0; i < N; i++) {
            dfs(i);
        }
        out.println(ans);
        out.close();
    }

    static ArrayList<Integer>[] adjList;
    static int[] maxDist;
    static int ans;

    // Return the longest path we can get starting from this node
    static int dfs(int node) {
        if (maxDist[node] != -1) {
            // Memoizing!
            return maxDist[node];
        }
        else {
            int max = 0;
            for (int adj : adjList[node]) {
                max = Math.max(max, 1 + dfs(adj));
            }
            ans = Math.max(ans, max);
            return maxDist[node] = max;
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