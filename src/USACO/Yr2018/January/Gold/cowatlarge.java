/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class cowatlarge {
    static String PROBLEM_NAME = "atlarge";
    static boolean testing = false;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        if (!testing) {
            sc = new Scanner(new FileReader(PROBLEM_NAME + ".in"));
            out = new PrintWriter(new FileWriter(PROBLEM_NAME + ".out"));
        }

        int N = sc.nextInt();
        int root = sc.nextInt() - 1;
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
        depth = new int[N];
        nearestLeaf = new int[N];
        // Root the tree at Bessie
        setupDepths(root, -1, 0);

        System.out.println(Arrays.toString(depth));
        System.out.println(Arrays.toString(nearestLeaf));
        out.println(solve(root, -1));
        out.close();
    }

    static ArrayList<Integer>[] adjList;
    static int[] depth;
    static int[] nearestLeaf;

    // Basically, we will keep walking down the tree until we hit a case
    // where a farmer (who started at a leaf), can reach the current point
    // of interest before Bessie can. If this is possible, then only the
    // one farmer needs to be stationed within that ENTIRE SUBTREE.
    // So, that's a base case where we can break out of. Otherwise
    // continue traversing down the tree.
    static int solve(int node, int parent) {
        if ((nearestLeaf[node] - depth[node] <= depth[node] - 0)) {
            return 1;
        }
        else {
            int ans = 0;
            for (int adj : adjList[node]) {
                if (adj != parent) {
                    ans += solve(adj, node);
                }
            }
            return ans;
        }
    }

    // Do a dfs, which will calculate the depths of each node
    // At the same time, we will also figure out the depth of the nearest leaf
    static int setupDepths(int node, int parent, int d) {
        depth[node] = d;
        // we must subtract 1 because it thinks its parent is a neighbor
        if (adjList[node].size() - 1 > 0) {
            int minDepth = (int) 1e5;
            for (int adj : adjList[node]) {
                if (adj != parent) {
                    int leafDepth = setupDepths(adj, node, d + 1);
                    minDepth = Math.min(minDepth, leafDepth);
                }
            }
            return nearestLeaf[node] = minDepth;
        }
        else {
            // we are a leaf, so we are the nearest leaf
            return nearestLeaf[node] = d;
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