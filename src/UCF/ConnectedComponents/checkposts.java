/*
 * @author derrick20
 * Strongly Connected Components
 * Keep track of the frequencies and the minimum prices of each
 * SCC, then multiply and mod. Use the DFS low link algorithm
 *
 */

import java.io.*;
import java.util.*;

public class checkposts {
    static int N;
    static ArrayList<Integer>[] graph;
    static int[] used;
    static int[] dfsNum; // pre-order numbering
    static int[] low; // low-link numbers
    static ArrayDeque<Integer> stack;

    static long[] cost; // cost for check post
    static int id; // for pre-order, keep track

    // global answers
    static long numWays;
    static long minCost;
    static long MOD = (long) 1e9 + 7;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        N = sc.nextInt();
        graph = new ArrayList[N];
        used = new int[N];
        cost = new long[N];
        dfsNum = new int[N];
        low = new int[N];
        stack = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            cost[i] = sc.nextLong();
            graph[i] = new ArrayList<>();
        }

        int M = sc.nextInt();
        for (int i = 0; i < M; i++) {
            // convert to 0-based
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            graph[u].add(v);
        }
        minCost = 0;
        numWays = 1;
        for (int i = 0; i < N; i++) {
            System.out.println(graph[i]);
            if (used[i] == 0) {
                dfs(i);
            }
        }
        out.println(minCost + " " + numWays);

        out.close();
    }

    public static void dfs(int v) {
        // marking it
        dfsNum[v] = low[v] = id++;
        stack.push(v);
        System.out.println(stack);
        used[v] = 1; // 0, 1, or 2

        // Check edges
        for (int next : graph[v]) {
            if (used[next] == 0) {
                // never been there, so branch through that point
                dfs(next);
                // We are the parent, so the child might've found
                // a lower low-link number, and we deserve to steal
                // that low number since we are higher up in the call stack
                low[v] = Math.min(low[v], low[next]);
            }
            else if (used[next] == 1) {
                // have returned to something we've seen,
                // we don't have access to their low-link number
                //
                low[v] = Math.min(low[v], dfsNum[next]);
            }
        }
        // we don't need to store the components, just need a global result
        // this is processing an SCC
        if (low[v] == dfsNum[v]) {
            int idx = stack.pop();
            long currMin = cost[idx];
            int freq = 1;
            // pop everything off
            while (v != idx) {
                v = stack.pop();
                if (cost[idx] < currMin) {
                    currMin = cost[idx];
                    freq = 1;
                }
                else if (cost[idx] == currMin) {
                    freq++;
                }
            }
            minCost = (minCost + currMin) % MOD;
            numWays = (numWays * freq) % MOD;
        }
        used[v] = 2;
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
