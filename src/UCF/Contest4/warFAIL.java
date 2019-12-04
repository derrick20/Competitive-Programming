/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class warFAIL {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();
        while (C-->0) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            adjList = new ArrayList[N];
            visited = new int[N];
            for (int i = 0; i < N; i++) {
                adjList[i] = new ArrayList<>();
            }
            int[][] conn = new int[N][N];
            for (int i = 0; i < M; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                conn[u][v] = 1;
                conn[v][u] = 1;
            }
            // essentially invert the graph
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    conn[i][j] = 1 - conn[i][j];
                    if (conn[i][j] == 1 && i != j) {
                        adjList[i].add(j);
                    }
                }
            }
            int color = 1;
            for (int i = 0; i < N; i++) {
//                out.println(adjList[i]);
                if (visited[i] == 0) {
                    dfs(i, color++);
                }
            }
            int[] ct = new int[color + 1];
            for (int i = 0; i < N; i++) {
                int c = visited[i];
                ct[c]++;
            }
            boolean ret = true;
            int num = 0;
            for (int count : ct) {
//                out.println(count);
                if (count < 2 && count != 0) {
                    ret = false;
                }
                if (count != 0)
                    num++;
            }
            out.println(ret && num == 2 ? "YES" : "NO");
        }
        out.close();
    }


    static ArrayList<Integer>[] adjList;
    static int[] visited;

    static void dfs(int node, int color) {
        if (visited[node] == 0) {
            visited[node] = color;
            for (int adj : adjList[node]) {
                dfs(adj, color);
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
