/*
 * @author derrick20
 */
import java.io.*;
import java.rmi.dgc.Lease;
import java.util.*;

public class reconstruct {

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();
        while (C-->0) {
            N = sc.nextInt();
            M = sc.nextInt();
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

            // Bunch of set up for LCA
            setupLCA();
            delta = new int[N];

            for (int q = 0; q < M; q++) {
                int a = sc.nextInt() - 1;
                int b = sc.nextInt() - 1;
                int lca = leastCommonAncestor(a, b);
                delta[a] += 1;
                delta[b] += 1;
                delta[lca] -= 2;
            }

            prefixSum = new int[N];
            ANS = 0;
            solve(0, -1);
            //        // O(N log N) by approximating Riemann sum of 1/x as log N, then the N
            //        // which scales it up. Then, log N for each lca, so O(N log^2 N) overall!
            //        for (int i = 1; i < N; i++) {
            //            for (int j = i*2; j < N; j += i) {
            //                int lca = leastCommonAncestor(i, j);
            //                int path1 = depth[i] - depth[lca];
            //                int path2 = depth[j] - depth[lca];
            //                ans += path1 + path2 + 1; // +1 for the ancestor itself
            //            }
            //        }
//            System.out.println(Arrays.toString(delta));
//            System.out.println(Arrays.toString(prefixSum));
            out.println(ANS);
        }
        out.close();
    }

    static int N, M;
    static int maxDepth;
    static int[] depth;
    static int[][] parent;
    static int[] firstParents;
    static ArrayList<Integer>[] adjList;
    static int[] delta;
    static int[] prefixSum;
    static long mod = (long) 1e9 + 7;
    static long ANS;

    static long fastExponentiate(long x, int k) {
        if (k == 0) {
            return 1;
        }
        if (k == 1) {
            return x;
        }
        else {
            long root = fastExponentiate(x, k /  2);
            long res = (root * root) % mod;
            if (k % 2 == 0) {
                return res;
            }
            else {
                return (x * res) % mod;
            }
        }
    }

    static int solve(int node, int par) {
        int ans = 0;
        for (int adj : adjList[node]) {
            if (adj != par) {
                ans += solve(adj, node);
                delta[node] += delta[adj];
            }
        }
        ans += delta[node];
        // all but the root will have an edge associated
        if (node != 0) {
//            System.out.println(M + " " + delta[node]);
            ANS += fastExponentiate(2, M - delta[node]);
            ANS %= mod;
        }
        return prefixSum[node] = ans;
    }

    static void setupLCA() {
        maxDepth = 0;
        depth = new int[N]; // store the depth of each node
        firstParents = new int[N]; // the immediate parent
        dfs(0, -1, 0);
        int power = 1;
        // Increase the power higher until we go over the maxDepth
        // This will be one more than we need
        while (1 << power <= maxDepth) {
            power++;
        }
        parent = new int[power][N]; // go up 2^power from node i of N
        // Now, use that initial information acquired from the dfs to build
        // base cases of the sparse tables!
        for (int node = 0; node < N; node++) {
            parent[0][node] = firstParents[node];
        }
        // Necessary to set everything else to zero for now, so
        // that we never get confused if we are out of bounds

        for (int p = 1; p < parent.length; p++) {
            for (int i = 1; i < N; i++) {
                parent[p][i] = -1;
            }
        }
        for (int p = 1; p < parent.length; p++) {
            for (int node = 0; node < N; node++) {
                if (parent[p - 1][node] != -1) {
                    int myParent = parent[p - 1][node];
                    parent[p][node] = parent[p-1][myParent];
                }
            }
        }
    }

    // no visited array needed since it's a tree
    static void dfs(int node, int par, int d) {
        depth[node] = d;
        firstParents[node] = par;
        maxDepth = Math.max(maxDepth, d);
        for (int adj : adjList[node]) {
            if (adj != par) {
                dfs(adj, node, d + 1);
            }
        }
    }

    static int leastCommonAncestor(int a, int b) {
        if (depth[b] > depth[a]) {
            int c = a;
            a = b;
            b = c;
        }
        // We assume that a is farther down, so that's why we swap
        // Now, keep increasing the level of a by using sparse table. Try using the
        // biggest jumps possible, then exponentially refine it
        // This is just a more complex way of representing it as a binary number

        int dist = depth[a] - depth[b];
        while (dist > 0) {
            int power = (int) (Math.log(dist) / Math.log(2));
            a = parent[power][a];
            dist -= 1 << power;
        }

        // Sometimes, we'll be at the same level AND be the same, so return early
        // (the other statement will be wrong since it'll return our parent, when we were already the same
        if (a == b) {
            return a;
        }

        for (int j = parent.length - 1; j >= 0; j--) {
            // We want to make sure that we keep going while they're parents are different.
            // This process will take us right to here (since we can always represent
            // any number in binary, just subtract 1):
            //  lca
            //  / \
            // a   b
            if (parent[j][a] != parent[j][b]) {
                a = parent[j][a];
                b = parent[j][b];
            }
        }
        return parent[0][a];
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
