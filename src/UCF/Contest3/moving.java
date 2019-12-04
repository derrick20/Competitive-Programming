/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class moving {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int c = 0; c < C; c++) {
            int N = sc.nextInt();
            adjList = new ArrayList[N + 1];
            for (int i = 1; i <= N; i++) {
                adjList[i] = new ArrayList<>();
            }
            visited = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                int v = sc.nextInt();
                if (v != i) {
                    adjList[i].add(v);
                    adjList[v].add(i);
                }
            }
            noCycle = true;
            for (int i = 1; i <= N; i++) {
                if (visited[i] == 0) {
                    dfs(i, i);
                }
            }
            out.println(noCycle ? 1 : 0);
        }
        out.close();
    }
    static ArrayList<Integer>[] adjList;
    static int[] visited;
    static boolean noCycle;
    static void dfs(int node, int parent) {
        if (visited[node] == 0) {
            visited[node] = 1;
            for (int adj : adjList[node]) {
                if (adj != parent) {
                    dfs(adj, node);
                }
            }
        }
        else {
            noCycle = false;
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

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
