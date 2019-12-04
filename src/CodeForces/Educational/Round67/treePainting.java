/*
 * @author derrick20
 * LONGGGGG
 */
import java.io.*;
import java.util.*;

public class treePainting {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        N = sc.nextInt();
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
        subtreeSize = new int[N];
        setup(0, -1);

        long total = 0;
        for (int val : subtreeSize) {
            total += val;
        }
        best = total;
        dfs(0, -1, total);
        out.println(best);
        out.close();
    }

    static int N;
    static ArrayList<Integer>[] adjList;
    static int[] subtreeSize;

    static long best;
    static void dfs(int node, int parent, long oldValue) {
        for (int adj : adjList[node]) {
            if (adj != parent) {
                long alt = oldValue - subtreeSize[adj] + N - subtreeSize[adj];
                best = Math.max(best, alt);
                dfs(adj, node, alt);
            }
        }
    }

    static int setup(int node, int parent) {
        int size = 1;
        for (int adj : adjList[node]) {
            if (adj != parent) {
                size += setup(adj, node);
            }
        }
        return subtreeSize[node] = size;
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
