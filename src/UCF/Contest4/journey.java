/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class journey {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int N = sc.nextInt();
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
        visited = new boolean[N];
        out.println(solve(0,  -1));
        out.close();
    }
    static ArrayList<Integer>[] adjList;
    static boolean[] visited;

    static double solve(int node, int parent) {
        double ans = 0;
        if (visited[node] || adjList[node].size() == 0) {
            return 0;
        }
        int paths = 0;
        for (int adj : adjList[node]) {
            visited[node] = true;
            if (adj != parent) {
                paths++;
                ans += 1 + solve(adj, node);
            }
        }
        if (paths == 0) {
            return 0;
        }
        return ans / paths;
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
