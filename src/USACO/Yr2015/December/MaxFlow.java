/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class MaxFlow {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean testing) throws Exception {
        String prefix = testing ? "/Users/derrick/IntelliJProjects/src/USACO/" : "";
        sc = new FastScanner(prefix + problem_name + ".in");
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner();
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
//        setupIO();
      setupIO("maxflow", false);

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
//    static int[] inTime, outTime;
    static int[] parent;
    static int[] eulerTour;
    static int time;
    static int[] depth, first;
    static int[] diffs;
    static int ans;

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
    Need to modify this so that it returns the index of the minimum depth of the range.

    I don't think there's actually a way to update a segment tree like this,
    it'll only work by constructing first
     */
    static class SegmentTree {
        int[] depth, tour;
        int[] minIndex;
        int[] lo, hi;
        int N;
        // a value that doesn't affect an output (for min it's infinity)
        int NEUTRAL = (int) 1e9;
        private int merge(int leftVal, int rightVal) {
            return Math.min(leftVal, rightVal);
        }

        public SegmentTree(int[] depth, int[] tour) {
            // depth is size N, arr is size 2N, but the values in arr are up to N - 1, so it works
            this.depth = depth;
            this.tour = tour;
            int N = tour.length;
            minIndex = new int[4 * N + 1];
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
                minIndex[node] = tour[left];
            } else {
                lo[node] = left;
                hi[node] = right;
                int mid = (left + right) / 2;
                constructSegmentTree(leftChild(node), left, mid);
                constructSegmentTree(rightChild(node), mid + 1, right);
                minIndex[node] = depth[minIndex[leftChild(node)]] < depth[minIndex[rightChild(node)]] ? minIndex[leftChild(node)] : minIndex[rightChild(node)];
            }
        }

        public int query(int leftBound, int rightBound) {
            return queryHelper(1, leftBound, rightBound);
        }

        // Query from a fixed range left, right
        private int queryHelper(int node, int leftBound, int rightBound) {
            if (hi[node] < leftBound || rightBound < lo[node]) {
                // We are either too far left or too far right
                return -1;
            } else if (leftBound <= lo[node] && hi[node] <= rightBound) {
                // Perfectly contained!
                return minIndex[node];
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