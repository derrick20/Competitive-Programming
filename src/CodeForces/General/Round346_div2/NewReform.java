import java.io.*;
import java.util.*;
/*
 * CodeForces Round 346 Div 2 NewReform
 * @derrick20
 */

public class NewReform {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        adjList = new ArrayList[N+1];
        visited = new boolean[N+1];
        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adjList[u].add(v);
            adjList[v].add(u);
        }
        int ct = 0;
        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                seen = 1;
                int edges = dfs(i, 0);
                ct += Math.max(0, seen - edges);
            }
        }
        System.out.println(ct);
    }
    static boolean visited[];
    static int seen;
    static ArrayList<Integer>[] adjList;

    static int dfs(int node, int parent) {
        int edges = 0;
        for (int adj : adjList[node]) {
            if (adj != parent) {
                edges++; // count all outgoing edges, minus its parent
            }
            if (adj != parent && !visited[adj]) {
                // our rule is to mark something visited right before we start exploring its neighbors
                visited[adj] = true;
                seen++;
                edges += dfs(adj, node);
            }
        }
        return edges;
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

