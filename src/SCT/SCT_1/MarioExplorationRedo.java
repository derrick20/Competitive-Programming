/**
 * @author derrick20
 * Struggled with this due to misreading the question.
 * When we update a subtree, we don't just update the node, but also the values of
 * everything else within the subtree. So, that means we need lazy propagation to update
 * a range (represented by the flattened tree).
 *
 * Once you've flattened the tree using the inTime and outTime, we can place
 * the brightnesses of each node/room at BOTH the start point and end poinot.
 * One subtlety, is that you don't want to be modding and alsoo dividing by 2 because
 * that causes errors (you need to have mod inverse or something to do that correctly)
 * Rather, it'd be safer to jsut add the full on values to the nodes.
 * So, when we update a range, we add to everything in the subtree, TWICE.
 * So, when we query a range, we need to do the sum, then divide by TWO.
 *
 * The key errors I was having were that you need to make sure the PULL UP and
 * QUERY methods are APPLYING THE LAZY VALUES correctly.
 *
 * Other than that, it's a simple problem...
 */
import java.io.*;
import java.util.*;

public class MarioExplorationRedo {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int Q = sc.nextInt();

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

        val = new int[N];
        for (int i = 0; i < N; i++) {
            val[i] = sc.nextInt();
        }

        inTime = new int[N];
        outTime = new int[N];
        time = 0;
        dfs(root, -1);

        int[] flattened = new int[2 * N];
        // The flattened tree will be able to query subtrees
        // by checking the range from intime to outtime of a node
        // It will use a bit too add all the values stored at
        // the entrance time positions within the flattened tree
        for (int i = 0; i < N; i++) {
            int lo = inTime[i];
            int hi = outTime[i];
            flattened[lo] = val[i];
            flattened[hi] = val[i];
            // let the lower part store the values
        }

//        BIT bit = new BIT(flattened);
        SegmentTree segTree = new SegmentTree(flattened);


        while (Q-->0) {
            int type = sc.nextInt();
            int room = sc.nextInt() - 1;
            if (type == 1) {
                long ans = (segTree.queryRange(inTime[room], outTime[room]) / 2) % mod;
                while (ans < 0) {
                    ans += mod;
                }
                out.println(ans);
            }
            else if (type == 2) {
                // Update that subtree
                int brightness = sc.nextInt();
                // Maybe negative issue?
                segTree.updateRange(inTime[room], outTime[room], brightness);
            }
        }

        out.close();
    }

    static int[] inTime, outTime;
    static int[] val;
    static ArrayList<Integer>[] adjList;
    static int time;
    static long mod = (long) 1e9 + 7;

    static void dfs(int node, int par) {
        inTime[node] = time++;
        for (int adj : adjList[node]) {
            if (adj != par) {
                dfs(adj, node);
            }
        }
        outTime[node] = time++;
    }

    static class SegmentTree {
        // keep track of the lo and hi bounds controlled of a certain node
        int[] lo, hi;
        int[] arr;
        long[] tree, lazy;
        long NEUTRAL = 0;
        int N;

        public SegmentTree(int[] arr) {
            N = arr.length;
            this.arr = arr;
            // Oh maybe the reason for 4N is because the delta values
            // won't need to be pushed for the lowest layer of the tree
            // because we won't need to query below them
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
            tree = new long[4 * N + 1];
            lazy = new long[4 * N + 1];
            // we had to give up that 0th cell of memory
            // because there's no easy way to go down from it
            buildTree(1, 0, N - 1);
        }

        private void buildTree(int node, int l, int r) {
            if (l == r) {
                tree[node] = arr[l];
                lo[node] = l;
                hi[node] = r;
            }
            else {
                lo[node] = l;
                hi[node] = r;
                int mid = (l + r) / 2;
                buildTree(leftChild(node), l, mid);
                buildTree(rightChild(node), mid + 1, r);
                pullUp(node);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N; i++) {
                sb.append(queryRange(i, i) + " ");
            }
            return sb.toString();
        }

        public String display(int node, int level) {
            String toRet = "";
            if (node >= tree.length)
                return "";
            toRet += display(rightChild(node), level + 1); //recurse right
            for(int k = 0; k < level; k++)
                toRet += "\t\t\t\t";
            toRet += node + ": " + "(" + tree[node] + ", " + lo[node] + ", " + hi[node]  + "), " + lazy[node] + "\n";
            toRet += display(leftChild(node), level + 1); //recurse left
            return toRet;
        }

        // The key insight for modifying this is to have a FAST way
        // of updating aggregate information using the deltas.
        // update a node with its children's new information, as a result
        // of potential changes due to recursion down in each subtree
        // Must use the lazy information
        private void pullUp(int node) {
            int l = leftChild(node);
            int r = rightChild(node);
            long leftVal = tree[l] + (hi[l] - lo[l] + 1) * lazy[l];
            long rightVal = tree[r] + (hi[r] - lo[r] + 1) * lazy[r];
            tree[node] = merge(leftVal, rightVal);
        }

        // transfer the lazy information stored at a node down to its children
        private void pushDown(int node) {
            lazy[leftChild(node)] += lazy[node];
            lazy[rightChild(node)] += lazy[node];
//            tree[node] += (hi[node] - lo[node] + 1) * lazy[node];
            lazy[node] = 0;
        }

        // Depending on the use case, the segment tree may merge
        // the values of two nodes via adding, xor, etc.
        public long merge(long leftVal, long rightVal) {
            return (leftVal + rightVal);
        }

        // Always begin the updating with the root of the tree
        public void updateRange(int l, int r, int val) {
            updateRangeHelper(1, l, r, val);
        }

        // Update a range [l, r] by val
        // Recursively walk down to our children:
        // Either we are completely covered, disjoint, or partially covered
        // KEY POINT: We don't use a mid to break apart the range,
        // since we need to preserve the information about the range.
        // What we ARE splitting is our node into its children, and
        // we'll individually decide which of those children are useful.
        private void updateRangeHelper(int node, int l, int r, int val) {
            if (r < lo[node] || hi[node] < l) {
                // we are completely disjoint with the goal range
                return;
            }
            else if (l <= lo[node] && hi[node] <= r) {
                // Complete covering, let's be lazy
                lazy[node] += val;
            }
            else {
                // Partial covering, forced to recurse down.
                // We may have lazy values from previous updates, so
                // we must propagate it downward
                pushDown(node);

                // Recurse on children
                updateRangeHelper(leftChild(node), l, r, val);
                updateRangeHelper(rightChild(node), l, r, val);

                // After recursion, we may have pending updates from children
                // affecting this node
                pullUp(node);
            }
        }

        public long queryRange(int l, int r) {
            return queryRangeHelper(1, l, r);
        }

        // Query the value along a range [l, r]
        // Where we are currently at a certain node and will
        // recurse downward - either being disjoint, contained, or partially
        // covered.
        private long queryRangeHelper(int node, int l, int r) {
            if (r < lo[node] || hi[node] < l) {
                // It's disjoint, so return a value that will not affect our
                // ultimate answer
                return NEUTRAL;
            }
            else if (l <= lo[node] && hi[node] <= r) {
                // We must apply any lazy values that had been placed here earlier
                // GAHHAHAHH
                return tree[node] + (hi[node] - lo[node] + 1) * lazy[node];
            }
            else {
                // First, propagate any pending lazy values
                pushDown(node);

                // Partial covering, so we must recurse
                // and gather information from the depths
                long leftResult = queryRangeHelper(leftChild(node), l, r);
                long rightResult = queryRangeHelper(rightChild(node), l, r);

                // Technically, this pullUp can be called IMMEDIATELY after
                // pushing down, since the values aren't actually changing,
                // it's just that we lost our lazy value, (and our value no longer
                // reflects the true rangeQuery value that node represents)
                pullUp(node);

                // The lazy values will have been included already
                return merge(leftResult, rightResult);
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