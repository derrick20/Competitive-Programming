/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class cowlandRevamp {
    static String PROBLEM_NAME = "cowland";
    static boolean testing = false;

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        if (!testing) {
            sc = new Scanner(new FileReader(PROBLEM_NAME + ".in"));
            out = new PrintWriter(new FileWriter(PROBLEM_NAME + ".out"));
        }
        // everything must go a bit beyond
        // In this case, we need to start from 1, otherwise more work later
        // so, everything will be N+1, and just never use node 0
        N = sc.nextInt();
        int Q = sc.nextInt();
        values = new int[N];

        for (int i = 0; i < values.length; i++) {
            values[i] = sc.nextInt();
        }

        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < N - 1; i++) {
            // todo MUST HAVE -1 FOR 0-INDEXING
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjList[u].add(v);
            adjList[v].add(u);
        }

        // Bunch of set up for LCA
        setupLCA();

        time = 1;
        inTime = new int[N];
        outTime = new int[N];
        dfsNumber(0, -1);

        dfsNum = new int[2 * N + 1];
        // This will give us a flattened tree, where a certain time range
        // will enclose a node's subtree. The inTime and outTimes will be
        // marked by that node, and are the borders for this subtree
        for (int node = 0; node < N; node++) {
            dfsNum[inTime[node]] = node;
            dfsNum[outTime[node]] = node;
        }
        int[] flattenedTree = new int[2 * N + 1];
        for (int t = 1; t <= 2 * N; t++) {
            int node = dfsNum[t];
            flattenedTree[t] = values[node];
        }

        BIT bit = new BIT(flattenedTree);

        for (int q = 0; q < Q; q++) {
            int type = sc.nextInt();
//            System.out.println(bit);
            if (type == 1) {
                // simple update to the Fenwick Tree
                int node = sc.nextInt() - 1;
                int value = sc.nextInt();
                int tIn = inTime[node];
                int tOut = outTime[node];
                bit.update(tIn, value);
                bit.update(tOut, value);
            }
            else if (type == 2) {
                int a = sc.nextInt() - 1;
                int b = sc.nextInt() - 1;
                int lca = leastCommonAncestor(a, b);
                // For our purposes, assume a is to the left, b is to the right
                // It doesn't actually matter, since the dfsNums will cancel
                // out perfectly for anything that's not on the direct path

                int left = bit.queryRange(inTime[lca], inTime[a]);
                int right = bit.queryRange(outTime[b], outTime[lca]);
                int ans = left ^ right;
                // No matter what case, it will double cancel the lca, so XOR it back in
                ans ^= bit.queryRange(inTime[lca], inTime[lca]);
                out.println(ans);
            }
        }

        out.close();
    }

    static int N;
    static int[] values;
    static int[] dfsNum;
    static int[] inTime, outTime;

    static int time;
    static void dfsNumber(int node, int par) {
        inTime[node] = time++;
        for (int adj : adjList[node]) {
            if (adj != par) {
                dfsNumber(adj, node);
            }
        }
        outTime[node] = time++;
    }

    static class BIT {
        int[] arr, tree;
        // todo Ideally, you pass in an array that is also 1-indexed,
        // todo so make sure to prepare for that!
        public BIT(int[] arr) {
            this.arr = arr;
            tree = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                tree[i] = arr[i];
            }
            constructBIT();
        }

        // Capitalize on the subset sum style of trick. Repeatedly
        // compound previous efforts
        // cleverly give our immediate superior our value.
        // They continually unload responsibility onto higher admins!
        // However, this is XOR, so we must follow appropriately
        public void constructBIT() {
            for (int i = 1; i < tree.length; i++) {
                int j = i + leastSignificantBit(i);
                if (j < tree.length) {
                    tree[j] ^= tree[i];
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Index: ");
            for (int i = 1; i < tree.length; i++) {
                sb.append(i + " ");
            }
            sb.append("\nValue: ");
            for (int i = 1; i < tree.length; i++) {
                sb.append(this.queryRange(i, i) + " ");
            }
            return sb.toString();
        }

        // cascading effect of 2's complement causing all zeroes after
        // LSB to flip, so then the final one will match
        public int leastSignificantBit(int x) {
            return x & -x;
        }

        public void update(int i, int val) {
            // do a quick negation by going up to all our superiors
            // whoah cool property: xor is inverse of xor
            // So, if everything needs to go from 3 -> 6, then let's
            // XOR everything with 6^3. It'll destroy all old 3 values, leaving 6 behind
            int oldValue = queryRange(i, i);
            int destroyer = val ^ oldValue; // basically shield us with our old value
            // this bulldozes through all copies of our old self
            while (i < tree.length) {
                tree[i] ^= destroyer;
                i += leastSignificantBit(i);
            }
        }

        public void updateRange(int i, int j, int val) {
            update(i, val);
            update(j + 1, val);
        }

        // XOR of items from i to j
        public int queryRange(int i, int j) {
            return prefix(j) ^ prefix(i - 1);
        }

        // Go down through all of our subordinates and combine their values
        public int prefix(int i) {
            int ans = 0;
            // Something that doesn't contribute to
            // a final XOR answer
            while (i > 0) {
                ans ^= tree[i];
                i -= leastSignificantBit(i);
            }
            return ans;
        }
    }

    static int maxDepth;
    static int[] depth;
    static int[][] parent; // parent takes the exponent for the binary lift and the child node as the arguments
    static int[] firstParents; // the immediate parents of each node, for initializing of base cases for parent[][]
    static ArrayList<Integer>[] adjList;

    /*
    todo CRUCIAL NOTE: THIS IS SET UP WITH BEGINNING AT NODE 0
    todo DO NOT MESS THAT UP WHEN READING IN STUFF
     */

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

        // Must fill it up with -1's
        for (int p = 1; p < parent.length; p++) {
            for (int i = 0; i < N; i++) {
                parent[p][i] = -1;
            }
        }
        // p represents going up by 1 << p
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