/**
 * @author derrick20
 * Key Insight:
 * Euler Tour Trees flatten the tree, and make it so each node appears twice.
 * A subtree is represented by the range of inTime and outTime for a given node
 * Toggling the segment tree is the tricky part:
 * We can store a lazy value that represents whether a segment needs to be toggled,
 * so this allows us to have a notion of pushing down a pending update (the children
 * each will now possess a pending toggling update). Then, we also note that
 * if we are actually grabbing the value from a toggle, we just take the
 * range - currValue to get the toggled value (complement of the whole tree)
 * where range = hi - lo + 1
 *
 * A key subtlety is that the toggle update only occurs if it's true -> otherwise
 * there's nothing to be updated (pullUp)
 *
 * Lastly, the EulerTourTree DOUBLES every value, so we just stay consistent
 * across and put in duplicates for everything. At the end, we divide by 2 to
 * get the clean answer!
 */
import java.io.*;
import java.util.*;

public class DanilPartTimeJob {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int v = 1; v <= N - 1; v++) {
            int u = sc.nextInt() - 1;
            adjList[u].add(v);
            adjList[v].add(u);
        }
        int[] light = new int[N];
        for (int i = 0; i < N; i++) {
            light[i] = sc.nextInt();
        }

        inTime = new int[N];
        outTime = new int[N];
        dfs(0, -1);

        int[] eulerTourTree = new int[2 * N];
        for (int i = 0; i < N; i++) {
            int left = inTime[i];
            int right = outTime[i];
            // So everything will be doubled
            eulerTourTree[left] = light[i];
            eulerTourTree[right] = light[i];
        }
        SegmentTree segTree = new SegmentTree(eulerTourTree);

        int Q = sc.nextInt();
        while (Q-->0) {
            String type = sc.next();
            int node = sc.nextInt() - 1;
            int left = inTime[node];
            int right = outTime[node];
            if (type.equals("get")) {
                long ans = segTree.queryRange(left, right) / 2;
                out.println(ans);
            }
            else if (type.equals("pow")) {
                segTree.updateRange(left, right);
            }
        }
        out.close();
    }

    static ArrayList<Integer>[] adjList;
    static int[] inTime, outTime;
    static int time;

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
        long[] tree;
        boolean[] lazy;

        public SegmentTree(int[] arr) {
            int N = arr.length;
            this.arr = arr;
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
            tree = new long[4 * N + 1];
            lazy = new boolean[4 * N + 1];
            // we had to give up that 0th cell of memory
            // because there's no easy way to go down from it
            buildTree(1, 0, N - 1);
        }

        public void buildTree(int node, int l, int r) {
            if (l == r) {
                tree[node] = arr[l];
                lo[node] = l;
                hi[node] = r;
            }
            else {
                lo[node] = l;
                hi[node] = r;
                int mid = (l + r) / 2;
                buildTree(2 * node, l, mid);
                buildTree(2 * node + 1, mid + 1, r);
                pullUp(node);
            }
        }

        public String display(int node, int level) {
            String toRet = "";
            if (node >= tree.length)
                return "";
            toRet += display(2 * node + 1, level + 1); //recurse right
            for(int k = 0; k < level; k++)
                toRet += "\t\t\t\t";
            toRet += node + ": " + "(" + tree[node] + ", " + lo[node] + ", " + hi[node]  + "), " + lazy[node] + "\n";
            toRet += display(2 * node, level + 1); //recurse left
            return toRet;
        }

        public long getVal(int node) {
            if (lazy[node]) {
                // It is to be toggled, so do complement
                return (hi[node] - lo[node] + 1) - tree[node];
            }
            else {
                return tree[node];
            }
        }

        // update a node with its children's new information, as a result
        // of potential changes due to recursion down in each subtree
        // Must use the lazy information
        public void pullUp(int node) {
            long leftVal = getVal(2 * node);
            long rightVal = getVal(2 * node + 1);
            tree[node] = merge(leftVal, rightVal);
        }

        // transfer the lazy information stored at a node down to its children
        public void pushDown(int node) {
            if (lazy[node]) {
                lazy[2 * node] = !lazy[2 * node];
                lazy[2 * node + 1] = !lazy[2 * node + 1];
                lazy[node] = false; // now we toggled, so no pending toggling
            }
        }

        // Depending on the use case, the segment tree may merge
        // the values of two nodes via adding, xor, etc.
        public long merge(long leftVal, long rightVal) {
            return leftVal + rightVal;
        }

        // Always begin the updating with the root of the tree
        public void updateRange(int l, int r) {
            updateRangeHelper(1, l, r);
        }

        // Update a range [l, r] by val
        // Recursively walk down to our children:
        // Either we are completely covered, disjoint, or partially covered
        // KEY POINT: We don't use a mid to break apart the range,
        // since we need to preserve the information about the range.
        // What we ARE splitting is our node into its children, and
        // we'll individually decide which of those children are useful.
        public void updateRangeHelper(int node, int l, int r) {
            if (r < lo[node] || hi[node] < l) {
                // we are completely disjoint with the goal range
                return;
            }
            else if (l <= lo[node] && hi[node] <= r) {
                // Complete covering, let's be lazy
                lazy[node] = !lazy[node]; // Toggle it
            }
            else {
                // Partial covering, forced to recurse down.
                // We may have lazy values from previous updates, so
                // we must propagate it downward
                pushDown(node);

                // Recurse on children
                updateRangeHelper(2 * node, l, r);
                updateRangeHelper(2 * node + 1, l, r);

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
        public long queryRangeHelper(int node, int l, int r) {
            if (r < lo[node] || hi[node] < l) {
                // It's disjoint, so return a value that will not affect our
                // ultimate answer
                return 0;
            }
            else if (l <= lo[node] && hi[node] <= r) {
                // We must apply any lazy values that had been placed here earlier
                return getVal(node); //
            }
            else {
                // First, propagate any pending lazy values
                pushDown(node);

                // Partial covering, so we must recurse
                // and gather information from the depths
                long leftResult = queryRangeHelper(2 * node, l, r);
                long rightResult = queryRangeHelper(2 * node + 1, l, r);

                // Technically, this pullUp can be called IMMEDIATELY after
                // pushing down, since the values aren't actually changing,
                // it's just that we lost our lazy value, (and our value no longer
                // reflects the true rangeQuery value that node represents)
                pullUp(node);

                // The lazy values will have been included already
                return merge(leftResult, rightResult);
            }
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