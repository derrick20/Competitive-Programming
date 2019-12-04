/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class board {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int i = 0; i < C; i++) {
            N = sc.nextInt();
            adjList =  new ArrayList[N];
            memo = new int[N][2];
            nodes = new int[N];
            for (int j = 0; j < N; j++) {
                adjList[j] = new ArrayList<>();
            }
            for (int j = 0; j < N; j++) {
                int par = sc.nextInt();
                int val = sc.nextInt();
                nodes[j] = val;
                if (par != -1) {
                    adjList[par].add(j);
                }
            }
            int ans = Math.max(solve(0, false), solve(0, true));
            out.println(ans);
        }
        out.close();
    }
    static int N;
    static int[][] memo;
    static int[] nodes;
    static ArrayList<Integer>[] adjList;
    // Use the current node or not
    static int solve(int node, boolean used) {
        if (memo[node][used ? 1 : 0] != 0) {
            return memo[node][used ? 1 : 0];
        }
        int value = used ? nodes[node] : 0;
        for (int nbr : adjList[node]) {
            if (!used) {
                // Either use it or don't
                value += Math.max(solve(nbr, false), solve(nbr, true));
            }
            else {
                value += solve(nbr, false);
            }
        }
        return memo[node][used ? 1 : 0] = value;
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
