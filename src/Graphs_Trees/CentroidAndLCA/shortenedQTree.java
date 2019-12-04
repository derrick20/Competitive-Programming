/*
 * @author derrick20
 * SPOJ Query on a tree
 * Hmm TLE GAHHH
 */
import java.io.*;
import java.util.*;

public class shortenedQTree {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        while (T-->0) {
            N = sc.nextInt();
            adjList = new ArrayList[N];
            for (int i = 0; i < N; i++) {
                adjList[i] = new ArrayList<>();
            }

            costs = new int[N][N];
            edgeIndex = new int[N][N];
            for (int i = 0; i < N - 1; i++) {
                int u = sc.nextInt() - 1;
                int v = sc.nextInt() - 1;
                int c = sc.nextInt();
                adjList[u].add(v);
                adjList[v].add(u);
                edgeIndex[u][v] = edgeIndex[v][u] = i;
                costs[u][v] = costs[v][u] = c;
            }
            edgeEnd = new int[N]; // This is important to map weights of edges to the bottom node of each edge
            setupLCA();
            HeavyLightDecomposition HLD = new HeavyLightDecomposition();

            boolean finished = false;
            while (!finished) {
                String str = sc.next();
//                System.out.println(HLD.segTree);
//                System.out.println(HLD);
                if (str.equals("CHANGE")) {
                    int node = sc.nextInt() - 1; // CRUCIAL -1!!
                    int newCost = sc.nextInt();
                    HLD.update(node, newCost);
                } else if (str.equals("QUERY")) {
                    int a = sc.nextInt() - 1;
                    int b = sc.nextInt() - 1;
                    out.println(HLD.query(a, b));
                } else if (str.equals("DONE")) {
                    finished = true;
                }
            }
        }
        out.close();
    }

    static int N;
    static ArrayList<Integer>[] adjList;
    static int[][] costs;
    static int[][] edgeIndex; // necessary for THIS problem to change edge weights
    static int[] edgeEnd; // at the end of each edge is the actual node that will change
    static int[] subTreeSize;
    static class HeavyLightDecomposition {
        // Each of these takes the node ID as an argument, pointing to the relevant information
        // Store the index of a node's chain, the head of it, and the MAPPED position of node for the seg tree
        static int[] chainIndex, chainHead, posInBase;

        static int[] baseArray;
        static SegmentTree segTree;
        static int pos, chainNum;

        public HeavyLightDecomposition() {
            // All of these are used in the decompose method
            chainHead = new int[N];
            Arrays.fill(chainHead, -1);

            chainIndex = new int[N];
            posInBase = new int[N];
            baseArray = new int[N];
            pos = 0;
            chainNum = 0;
            decomposeTree(0, -1, 0);
            segTree = new SegmentTree(baseArray);
        }

        public void decomposeTree(int node, int par, int cost) {
            if (chainHead[chainNum] == -1) {
                // Mark this as the head of the current chain
                chainHead[chainNum] = node;
            }
            // Mark that we are part of this chain
            chainIndex[node] = chainNum;
            // This node maps to this position
            posInBase[node] = pos;
            // The current position takes this node's cost
            baseArray[pos] = cost;
            pos++;

            int heavyChild = -1;
            int adjCost = 0;
            for (int adj : adjList[node]) {
                if (adj != par) {
                    if (heavyChild == -1 || subTreeSize[adj] > subTreeSize[heavyChild]) {
                        heavyChild = adj;
                        adjCost = costs[node][adj];
                    }
                }
            }

            if (heavyChild != -1) {
                // Go down and keep building the chain.
                decomposeTree(heavyChild, node, adjCost);
            }
            // Now, we can start a new chain
            for (int adj : adjList[node]) {
                if (adj != par && adj != heavyChild) {
                    chainNum++; // Increment once per new chain. However, for pos, we increment very frequently
                    // (since each node is updated with this
                    decomposeTree(adj, node, costs[node][adj]);
                }
            }
        }

        public int query(int a, int b) {
            int lca = leastCommonAncestor(a, b);
            // It's necessary that LCA comes second in the arguments
            int left = queryUp(a, lca);
            int right = queryUp(b, lca);
            return Math.max(left, right);
        }
        // This requires node to be deeper or at same depth of ancestor
        public int queryUp(int node, int anc) {
            int ans = 0; // todo For max query, we need to use 0
            boolean finished = false;
            while (true) {
                int tail = posInBase[node];
                int head;
                int curChain = chainIndex[node];
                // If finally reached top chain, special case of a lower head
                if (curChain == chainIndex[anc]) {
                    head = posInBase[anc];
                    finished = true;
                }
                else {
                    // Otherwise, we just keep jumping to our head as usual!
                    head = posInBase[chainHead[curChain]];
                }
                int potential = segTree.query(head, tail);

                // Keep maximizing the answer along the chains
                ans = Math.max(ans, potential);
                if (finished) {
                    break;
                }
                node = parent[0][chainHead[curChain]];
            }
            return ans;
        }

        public void update(int node, int val) {
            // Use the bottom node which is 1-to-1 correspondence to the edge weights!
            int end = edgeEnd[node];
            // Update using the REAL index in the base array
            segTree.update(posInBase[end], val);
        }
    }
    static class SegmentTree {
        static int[] arr;
        static int[] tree;
        static int[] lo, hi;
        // a value that doesn't affect an output
        int NEUTRAL = 0;

        public SegmentTree(int[] arr) {
            this.arr = arr;
            tree = new int[4 * N + 1];
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
            // The original bounds of responsibility the root will be [0, N-1]
            constructSegmentTree(1, 0, N - 1);
        }

        // Construct a node which is responsible for the range specified: left to right inclusive!
        public void constructSegmentTree(int node, int left, int right) {
            if (left == right) {
                lo[node] = left;
                hi[node] = right;
                tree[node] = arr[left];
            } else {
                lo[node] = left;
                hi[node] = right;
                int mid = (left + right) / 2;
                constructSegmentTree(2 * node, left, mid);
                constructSegmentTree(2 * node + 1, mid + 1, right);
                tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
            }
        }

        public int merge(int leftVal, int rightVal) {
            return Math.max(leftVal, rightVal);
        }

        public int query(int leftBound, int rightBound) {
            return queryHelper(1, leftBound, rightBound);
        }

        // Query from a fixed range left, right
        private int queryHelper(int node, int leftBound, int rightBound) {
            if (hi[node] < leftBound || rightBound < lo[node]) {
                // We are either too far left or too far right
                return NEUTRAL;
            } else if (leftBound <= lo[node] && hi[node] <= rightBound) {
                // Perfectly contained!
                return tree[node];
            } else {
                // Partial covering
                int leftVal = queryHelper(2 * node, leftBound, rightBound);
                int rightVal = queryHelper(2 * node + 1, leftBound, rightBound);
                int ans = merge(leftVal, rightVal);
                return ans;
            }
        }

        public void update(int index, int val) {
            updateHelper(1, index, val);
        }

        private void updateHelper(int node, int index, int val) {
            if (hi[node] < index || index < lo[node]) {
                // We are either too far left or too far right
                return;
            } else if (index == lo[node] && hi[node] == index) {
                // Found it!
                tree[node] = val;
            } else {
                // Not there yet!
                updateHelper(2 * node, index, val);
                updateHelper(2 * node + 1, index, val);
                // Make sure to fix our values on the way back up!
                tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
            }
        }
    }
    static int maxDepth;
    static int[] depth;
    // parent takes the exponent for the binary lift and the child node as the arguments
    static int[][] parent;
    // the immediate parents of each node, for initializing of base cases for parent[][]
    static int[] firstParents;

    static void setupLCA() {
        maxDepth = 0;
        depth = new int[N]; // store the depth of each node
        firstParents = new int[N]; // the immediate parent
        subTreeSize = new int[N]; // size of subtree of a given node (todo this is FOR HLD, but DO IT HERE FOR EASE!!!)
        dfs(0);
        int power = 1;
        while (1 << power <= maxDepth) {
            power++;
        }
        parent = new int[power][N];
        for (int node = 0; node < N; node++) {
            parent[0][node] = firstParents[node];
        }
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
    static int dfs(int node) {
        int size = 1;
        for (int adj : adjList[node]) {
            if (adj != firstParents[node]) {
                firstParents[adj] = node;
                depth[adj] = depth[node] + 1;
                maxDepth = Math.max(maxDepth, depth[adj]);
                size += dfs(adj);
                int index = edgeIndex[node][adj];
                edgeEnd[index] = adj; // Since we start from the root, it will never represent an edge
            }
        }
        return subTreeSize[node] = size;
    }
    static int leastCommonAncestor(int a, int b) {
        if (depth[b] > depth[a]) {
            // make the bigger depth be node a
            int c = a;
            a = b;
            b = c;
        }
        int dist = depth[a] - depth[b];
        while (dist > 0) {
            // level out the depths
            int power = (int) (Math.log(dist) / Math.log(2));
            a = parent[power][a];
            dist -= 1 << power;
        }
        if (a == b) {
            // return early
            return a;
        }
        for (int j = parent.length - 1; j >= 0; j--) {
            if (parent[j][a] != parent[j][b]) {
                // Jump until right below lca
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
