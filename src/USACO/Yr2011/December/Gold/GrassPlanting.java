/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class GrassPlanting {
    static String PROBLEM_NAME = "grassplant";
    static boolean testing = false;

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        if (!testing) {
            sc = new Scanner(new FileReader(PROBLEM_NAME + ".in"));
            out = new PrintWriter(new FileWriter(PROBLEM_NAME + ".out"));
        }

        N = sc.nextInt();
        int Q = sc.nextInt();
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }

        costs = new int[N];
        for (int i = 0; i < N; i++) {
            costs[i] = sc.nextInt();
        }
        for (int i = 0; i < N - 1; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjList[u].add(v);
            adjList[v].add(u);
        }
        setupLCA();
        // An important thing is that, once we've finished setting up LCA
        // we won't need adjList again. =Thus, we can decompose the tree and
        // repeatedly delete nodes safely!

        // Our HLD uses the information globally available about the graph
        // (N, adjList, subTreeSize, edgeEnd, edgeIndex). Nothing to pass in!
        HeavyLightDecomposition HLD = new HeavyLightDecomposition();

        while (Q-->0) {
            int type = sc.nextInt();
//            System.out.println(HLD.segTree);
//            System.out.println(HLD);
            if (type == 1) {
                int node = sc.nextInt() - 1; // CRUCIAL -1!!
                int newCost = sc.nextInt();
                HLD.update(node, newCost);
            }
            else if (type == 2) {
                int a = sc.nextInt() - 1;
                int b = sc.nextInt() - 1;
                out.println(HLD.query(a, b));
            }
        }
        out.close();
    }

    static int N;
    static ArrayList<Integer>[] adjList;
    static int[] costs;

    // Luckily we can setup subtree sizes in the same dfs as the one for the LCA
    // preprocessing, so check down there to see this
    static int[] subTreeSize;
    /*todo-----------------Below is everything relevant to heavy-light decomposition------------------*/

    // In layman's terms, combining a cool decomposition with a big segment
    // tree that takes advantage of those nice, evenly split up pieces of the tree
    // which leads to two factors of log N for query -> O(log^2 N)
    static class HeavyLightDecomposition {
        // Each of these takes the node ID as an argument, pointing to the relevant information
        int[] chainIndex; // Stores the CHAIN NUMBER we are a part of - this is meaningless for the graph
        int[] chainHead; // Stores, for a given CHAIN NUMBER (from chainIndex), what the NODE ID of the head is
        // This IS meaningful for the graph
        int[] posInBase; // Stores, for a given node, where it actually lies in the chain organization numbering
        // for the purpose of SEGMENT TREE QUERIES. The idea is that all segments can be broken into the chains we made!

        int[] baseArray;
        SegmentTree segTree;
        // The seg tree will be built upon the baseArray, and will be used to
        // perform queries and updates!

        // We have extra number that will change as we build things!
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
            decomposeTree(0, -1, costs[0]);
            segTree = new SegmentTree(baseArray);
        }

        public String toString() {
//            return "(Node, Head, Chain, Pos)\n" + toStringHelper(0, -1,0);
            return toStringHelper(0, -1, 0);
        }

        private String toStringHelper(int node, int par, int level) {
            String toRet = "";
            int i = 0;
            for (; i < adjList[node].size() / 2; i++) {
                int adj = adjList[node].get(i);
                if (adj != par) {
                    toRet += toStringHelper(adj, node, level + 1); //recurse right
                }
            }
            // Put a bunch of spaces once half-way
            for (int k = 0; k < level; k++) {
                toRet += "\t\t\t\t\t\t";
            }
            toRet += node + ": " + "Head: " + chainHead[chainIndex[node]] + ", Chain: " + chainIndex[node] + ", Pos: " + posInBase[node] + "\n";
//            toRet += "(" + node + ", " + chainHead[node] + ", " + chainIndex[node] + ", " + posInBase[node] + ")" + "\n";

            for (; i < adjList[node].size(); i++) {
                int adj = adjList[node].get(i);
                if (adj != par) {
                    toRet += toStringHelper(adj, node, level + 1); //recurse right
                }
            }
            return toRet;
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
                        // By finding the maximum subTree child, we guarantee the rest have less than size n/2
                        // Suppose we picked largest and > n/2 is still remaining. This means we picked the wrong one!
                        // Even if best case and there was only one other candidate who possessed the rest of the
                        // tree, we'd always leave behind something < n/2!
                        heavyChild = adj;
                        adjCost = costs[adj];
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
                    decomposeTree(adj, node, costs[adj]);
                }
            }
        }

        public int query(int a, int b) {
            int lca = leastCommonAncestor(a, b);
            // It's necessary that LCA comes second in the arguments
            int left = queryUp(a, lca);
            int right = queryUp(b, lca);
            int fixLCA = queryUp(lca, lca);
            return left ^ right ^ fixLCA;
        }

        // Always goes from node to higher up ancestor
        // This requires node to be deeper or at same depth of ancestor
        public int queryUp(int node, int anc) {
            int ans = 0; // todo For max query, we need to use 0
            boolean finished = false;
            while (true) {
                int tail = posInBase[node];
                int head;
                int curChain = chainIndex[node]; // this represents the CHAIN ID, not meaningful for the graph!
                // If finally reached top chain, special case of a lower head
                if (curChain == chainIndex[anc]) {
                    head = posInBase[anc];
                    // Once we've checked all the chains, including the one they are a part of,
                    // mark that we are finished after this iteration!
                    finished = true;
                }
                else {
                    // Otherwise, we just keep jumping to our head as usual!
                    head = posInBase[chainHead[curChain]];
                }
                // head is going to be lower indexed than tail (earlier in dfs traversal)
                int potential = segTree.query(head, tail);

                // Keep maximizing the answer along the chains
                ans ^= potential;
                if (finished) {
                    break;
                }
                // Node hops up to become the immediate parent of its chain head
                // which represents moving to the next heavy chain and bridging a light edge
                node = parent[0][chainHead[curChain]];
            }
            return ans;
        }

        public void update(int node, int val) {
            // Use the bottom node which is 1-to-1 correspondence to the edge weights!
            // Update using the REAL index in the base array
            segTree.update(posInBase[node], val);
        }
    }

    /*todo-----------------Below is everything relevant to the segment tree------------------*/
    // Quick and simple Segment Tree for our decomp!

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

        public String toString() {
            return "Leaf Values: " + toStringHelper(1);
        }

        private String toStringHelper(int node) {
            if (lo[node] == hi[node]) {
                return tree[node] + " ";
            }
            else {
                return toStringHelper(2 * node) + toStringHelper(2 * node + 1);
            }
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
            return leftVal ^ rightVal;
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

    /*todo-----------------Below is everything relevant to least common ancestor------------------*/
    /*
    todo CRUCIAL NOTE: THIS IS SET UP WITH BEGINNING AT NODE 0
         DO NOT MESS THAT UP WHEN READING IN STUFF
     */
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
    // This is one POWERFUL DFS. It sets up the base case for
    // the dynamic programming in  the LCA stuffm it figures out their depths,
    // and the maxDepth for the sake of log bounding, and also the subtree
    // size of each node! WOW!
    static int dfs(int node) {
        int size = 1;
        for (int adj : adjList[node]) {
            if (adj != firstParents[node]) {
                firstParents[adj] = node;
                depth[adj] = depth[node] + 1;
                maxDepth = Math.max(maxDepth, depth[adj]);
                size += dfs(adj);
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

    static int distance(int a, int b) {
        return depth[a] + depth[b] - 2 * depth[leastCommonAncestor(a, b)];
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
