/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class war {

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
            for (int i = 0; i < M; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                adjList[u].add(v);
                adjList[v].add(u);
            }
            Arrays.fill(visited, -1);
            boolean ret = true;
            for (int i = 0; i < N; i++) {
                if (visited[i] == -1) {
//                    if (ct1 >= 2) {
                    ret &= dfs(i, 1);
//                        ct1++;
//                    }
//                    else {
//                        ret &= dfs(i, 0);
                }
            }
            int group1 = 0;
            int group2 = 0;
            for (int i = 0; i < N; i++) {
                if (visited[i] == 0) {
                    group1++;
                }
                else if (visited[i] == 1) {
                    group2++;
                }
            }
            out.println(ret && (group1 >= 2 && group2 >= 2) ? "YES" : "NO");
        }
        out.close();
    }

    static ArrayList<Integer>[] adjList;
    static int[] visited;

    // alternate colors. If we ever reach a contradiction, exit and return false
    static boolean dfs(int node, int color) {
        if (visited[node] == 1 - color) {
            return false;
        }
        else {
            boolean ans = true;
            if (visited[node] == -1) {
                visited[node] = color;
                for (int adj : adjList[node]) {
                    ans &= dfs(adj, 1 - color);
                }
                return ans;
            }
            // either it's unvisited, or it's already our color, so we can
            // let it be true
            return ans;
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
