/**
 * @author derrick20
 * Using Euler Tour, we can construct an array that
 * implicitly stores the path between nodes. Specifically,
 * since we are doing LCA, we want the min depthed vertex within
 * the euler tour, so we had to modify the segment tree to return
 * the vertex stored at a position in the euler tour.
 *
 * Logic: Consider the vertices visited during the dfs traversal.
 * If we just arbitrarily check the toured vertices between the
 * first positions of u and v, we will have a path that goes up
 * to their LCA, and potentially winds down into deeper subtrees.
 *
 * However, since we are querying the minimum depth along this
 * range of positions within the euler tour, we will need the auxiliary
 * array that stored the depth of any given vertex.
 *
 * Interesting mapping tidbit: the Euler Tour is size 2N, but all the
 * values are vertices, so it maps onto [0, N). The actual depth array
 * is size N, so it reads the outputs of the Euler Tour's array!
 */
import java.io.*;
import java.util.*;

public class LCAEulerTour {
    static FastScanner sc;
    static PrintWriter out;

    public static void main(String args[]) throws Exception {
        N = sc.nextInt();
        int K = sc.nextInt();
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
        parent = new int[N];
        depth = new int[N];
        eulerTour = new int[2 * N - 1];
        first = new int[N];
        // There will be 2 for each edge, then one more at the end when returning to root
        // Since it's not querying, we don't need a seg tree
        dfs(0, -1, 0); // Arbitrarily root at node 0
        SegmentTree segTree = new SegmentTree(depth, eulerTour);

        diffs = new int[N];
        for (int i = 0; i < K; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int uPos = first[u];
            int vPos = first[v];
            if (uPos > vPos) {
                int temp = uPos;
                uPos = vPos;
                vPos = temp;
            }
            int lca = segTree.query(uPos, vPos);
            diffs[u]++;
            diffs[v]++;
            diffs[lca]--;
            if (parent[lca] != -1) {
                diffs[parent[lca]]--;
            }
        }
        propagate(0, -1);
        out.println(ans);
        out.close();
    }

    static int N;
    static ArrayList<Integer>[] adjList;
    static int[] parent;
    static int[] eulerTour;
    static int time;
    static int[] depth, first;
    static int[] diffs;
    static int ans;

    /*
    This was used for prefix sums to propagate the difference array out. While
    we perform this dfs, I maximize the answer seen
     */
    static int propagate(int node, int par) {
        for (int adj : adjList[node]) {
            if (adj != par) {
                diffs[node] += propagate(adj, node);
            }
        }
        ans = Math.max(ans, diffs[node]);
        return diffs[node];
    }
    // The dfs will visit each edge, and increment time when going down and then
    // again when going up
    static void dfs(int node, int par, int d) {
        parent[node] = par;
        depth[node] = d;
        first[node] = time; // The entrance is where we first see this node.
        eulerTour[time++] = node;
        for (int adj : adjList[node]) {
            if (adj != par) {
                dfs(adj, node, d + 1);
                eulerTour[time++] = node;
            }
        }
    }

    /*
    We had to modify this so that it returns the index of the minimum depth of the range.

    I don't think there's actually a way to update a segment tree like this,
    it'll only work by constructing first
     */
    static class SegmentTree {
        int[] depth, tour;
        int[] minDepthVertex;
        int[] lo, hi;
        int N;

        public SegmentTree(int[] depth, int[] tour) {
            // depth is size N, arr is size 2N, but the values in arr are up to N - 1, so it works
            this.depth = depth;
            this.tour = tour;
            int N = tour.length;
            minDepthVertex = new int[4 * N + 1];
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
            // The original bounds of responsibility the root will be [0, N-1]
            constructSegmentTree(1, 0, N - 1);
        }

        // Construct a node which is responsible for the range specified: left to right inclusive!
        private void constructSegmentTree(int node, int left, int right) {
            if (left == right) {
                lo[node] = left;
                hi[node] = right;
                // Min index at single point is just the index stored in the DFS numbering
                minDepthVertex[node] = tour[left];
            } else {
                lo[node] = left;
                hi[node] = right;
                int mid = (left + right) / 2;
                constructSegmentTree(leftChild(node), left, mid);
                constructSegmentTree(rightChild(node), mid + 1, right);
                minDepthVertex[node] = depth[minDepthVertex[leftChild(node)]] < depth[minDepthVertex[rightChild(node)]] ?
                        minDepthVertex[leftChild(node)] : minDepthVertex[rightChild(node)];
            }
        }

        public int query(int leftBound, int rightBound) {
            return queryHelper(1, leftBound, rightBound);
        }

        // Query from a fixed range left, right
        /*
        We take as an input the earliest positions of u and v in the euler tour.
        (we guarantee that u will be to the "left" of v in the tree, based on how the dfs worked)

        We then output, over all vertices in the subsection of the euler tour between those
        two inputted positions, the vertex with minimum depth.

        As we move up the Segment Tree's tree, we will have nodes that store the vertices in the
        euler tour that have minimum depth. So, this means we can break down our query into just
        finding the few vertices that serve as leaders of subtrees in terms of being the least depth.
        This substructure allows us to query by just checking the few managers which have small depths,
        and taking the best out of them. If something is out of bounds, it will be ignored
         */
        private int queryHelper(int node, int leftBound, int rightBound) {
            if (hi[node] < leftBound || rightBound < lo[node]) {
                // We are either too far left or too far right
                return -1;
            } else if (leftBound <= lo[node] && hi[node] <= rightBound) {
                // Perfectly contained!
                return minDepthVertex[node];
            } else {
                // Partial covering
                int leftVal = queryHelper(leftChild(node), leftBound, rightBound);
                int rightVal = queryHelper(rightChild(node), leftBound, rightBound);
                // If either of them ended up being out of bounds, then we know it's the other half
                // It is impossible for it to have been both out of bounds, since we wouldn't have recursed
                // from a state already out of bounds. (we would've caught that earlier!)
                if (leftVal == -1) {
                    return rightVal;
                }
                else if (rightVal == -1) {
                    return leftVal;
                }
                else {
                    return depth[leftVal] < depth[rightVal] ? leftVal : rightVal;
                }
            }
        }

        private int leftChild(int node) {
            return 2 * node;
        }

        private int rightChild(int node) {
            return 2 * node + 1;
        }
    }

    static class FastScanner {
        public int BS = 1<<16;
        public char NC = (char)0;
        byte[] buf = new byte[BS];
        int bId = 0, size = 0;
        char c = NC;
        double cnt = 1;
        BufferedInputStream in;

        public FastScanner() {
            in = new BufferedInputStream(System.in, BS);
        }

        public FastScanner(String s) {
            try {
                in = new BufferedInputStream(new FileInputStream(new File(s)), BS);
            }
            catch (Exception e) {
                in = new BufferedInputStream(System.in, BS);
            }
        }

        private char getChar(){
            while(bId==size) {
                try {
                    size = in.read(buf);
                }catch(Exception e) {
                    return NC;
                }
                if(size==-1)return NC;
                bId=0;
            }
            return (char)buf[bId++];
        }

        public int nextInt() {
            return (int)nextLong();
        }

        public long nextLong() {
            cnt=1;
            boolean neg = false;
            if(c==NC)c=getChar();
            for(;(c<'0' || c>'9'); c = getChar()) {
                if(c=='-')neg=true;
            }
            long res = 0;
            for(; c>='0' && c <='9'; c=getChar()) {
                res = (res<<3)+(res<<1)+c-'0';
                cnt*=10;
            }
            return neg?-res:res;
        }

        public double nextDouble() {
            double cur = nextLong();
            return c!='.' ? cur:cur+nextLong()/cnt;
        }

        public String next() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=getChar();
            while(c>32) {
                res.append(c);
                c=getChar();
            }
            return res.toString();
        }

        public String nextLine() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=getChar();
            while(c!='\n') {
                res.append(c);
                c=getChar();
            }
            return res.toString();
        }

        public boolean hasNext() {
            if(c>32)return true;
            while(true) {
                c=getChar();
                if(c==NC)return false;
                else if(c>32)return true;
            }
        }
    }
}