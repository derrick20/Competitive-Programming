/*
 * @author derrick20
 * Another technique would be using Union Find on this - the
 * interesting idea is that sometimes the memory limit might be
 * really low, so you wouldn't be able to store the adjLists into
 * arrays. Instead, DSU/UnionFind would be able to process it
 * while you're inputting (so just to sz[node] choose 2
 */

import java.io.*;
import java.util.*;

public class geography {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int c = 0; c < C; c++) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            adjList = new ArrayList[N];
            visited = new int[N];

            for (int i = 0; i < N; i++) {
                adjList[i] = new ArrayList<>();
            }
            for (int i = 0; i < M; i++) {
                int u = sc.nextInt() - 1;
                int v = sc.nextInt() - 1;
                adjList[u].add(v);
                adjList[v].add(u);
            }
            int ct = 0;
            for (int i = 0; i < N; i++) {
                if (visited[i] == 0) {
                    ct++;
                    dfs(i, ct);
                }
            }
            int[] regions = new int[ct + 1];
            for (int i = 0; i < N; i++) {
                regions[visited[i]]++; // count how many are of that color
            }
            long res = 0;
            for (int i = 1; i <= ct; i++) {
                int nodes = regions[i];
                // TODO CRUCIAL OVERFLOWAAAAAAHHAHHF
                res += ((double) nodes / 2) * (nodes - 1);
            }
            out.println(res);
        }
        out.close();
    }

    static ArrayList<Integer>[] adjList;
    static int[] visited;
    static void dfs(int node, int ct) {
        if (visited[node] == 0) {
            visited[node] = ct;
            for (int adj : adjList[node]) {
                dfs(adj, ct);
            }
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
