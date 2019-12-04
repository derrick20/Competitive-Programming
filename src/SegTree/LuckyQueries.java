/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class LuckyQueries {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int Q = sc.nextInt();
        int[] arr = new int[N];
        String s = sc.next();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = s.charAt(i) == '4' ? 0 : 1;
        }
        SegmentTree segTree = new SegmentTree(arr);

        while (Q-->0) {
            String type = sc.next();
            if (type.equals("count")) {
                out.println(segTree.queryRange(0, N - 1));
            }
            else if (type.equals("switch")) {
                int l = sc.nextInt() - 1;
                int r = sc.nextInt() - 1;
                segTree.updateRange(l, r, true);
            }
        }
        out.close();
    }

    static class SegmentTree {
        // keep track of the lo and hi bounds controlled of a certain node
        int[] lo, hi;
        int[] arr;
        long[] treeIncr, treeDecr;
        boolean[] lazy;
        long[] treeZero, treeOne;
        long NEUTRAL = 0;
        // todo MAKE SURE NEUTRAL IS CORRECT
        int N;

        // Depending on the use case, the segment tree may merge
        // the values of two nodes via adding, xor, etc.
        public long merge(long leftVal, long rightVal) {
            return Math.max(leftVal, rightVal);
        }

        public SegmentTree(int[] arr) {
            N = arr.length;
            this.arr = arr;
            // Oh maybe the reason for 4N is because the delta values
            // won't need to be pushed for the lowest layer of the tree
            // because we won't need to query below them
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
            treeIncr = new long[4 * N + 1];
            treeDecr = new long[4 * N + 1];
            treeZero = new long[4 * N + 1];
            treeOne = new long[4 * N + 1];
            lazy = new boolean[4 * N + 1];
            // we had to give up that 0th cell of memory
            // because there's no easy way to go down from it
            buildTree(1, 0, N - 1);
        }

        private void buildTree(int node, int l, int r) {
            if (l == r) {
                treeIncr[node] = 1;
                treeDecr[node] = 1;
                if (arr[l] == 0) {
                    treeZero[node] = 1;
                }
                else if (arr[l] == 1) {
                    treeOne[node] = 1;
                }
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

        // The key insight for modifying this is to have a FAST way
        // of updating aggregate information using the deltas.
        // update a node with its children's new information, as a result
        // of potential changes due to recursion down in each subtree
        // Must use the lazy information
        private void pullUp(int node) {
            int l = leftChild(node);
            int r = rightChild(node);

            // Easy to update these
            treeOne[node] = (lazy[l] ? treeZero[l] : treeOne[l]) + (lazy[r] ? treeZero[r] : treeOne[r]);
            treeZero[node] = (lazy[l] ? treeOne[l] : treeZero[l]) + (lazy[r] ? treeOne[r] : treeZero[r]);

            long leftVal = lazy[l] ? treeDecr[l] : treeIncr[l];
            long rightVal = lazy[r] ? treeDecr[r] : treeIncr[r];
            // However, we could also make a SUBSEQUENCE that spans the
            // two
            // So, we take the 0s in left, and combine with seq of 01 in right. We add 0's from left if lazy is false
            // Also flipping if there are lazy values.
            long spanAddLeft = (lazy[l] ? treeOne[l] : treeZero[l]) + (lazy[r] ? treeDecr[r] : treeIncr[r]);

            // Take 1's from right, combine with seq of 01 in the left. We add 1's from right if lazy is false
            long spanAddRight = (lazy[l] ? treeDecr[l] : treeIncr[l]) + (lazy[r] ? treeZero[r] : treeOne[r]);

            // todo sum: leftVal = tree[l] + (hi[l] - lo[l] + 1) * lazy[l]
            treeIncr[node] = merge(merge(spanAddLeft, spanAddRight), merge(leftVal, rightVal));

            // Repeat the process, but everything inverted.
            long leftVal2 = !lazy[l] ? treeDecr[l] : treeIncr[l];
            long rightVal2 = !lazy[r] ? treeDecr[r] : treeIncr[r];
            long spanAddLeft2 = (!lazy[l] ? treeOne[l] : treeZero[l]) + (!lazy[r] ? treeDecr[r] : treeIncr[r]);
            long spanAddRight2 = (!lazy[l] ? treeDecr[l] : treeIncr[l]) + (!lazy[r] ? treeZero[r] : treeOne[r]);
            treeDecr[node] = merge(merge(spanAddLeft2, spanAddRight2), merge(leftVal2, rightVal2));

        }

        // transfer the lazy information stored at a node down to its children
        private void pushDown(int node) {
            if (lazy[node]) {
                lazy[leftChild(node)] ^= lazy[node];
                lazy[rightChild(node)] ^= lazy[node];
                lazy[node] = false;
            }
        }

        // Always begin the updating with the root of the tree
        public void updateRange(int l, int r, boolean val) {
            updateRangeHelper(1, l, r, val);
        }

        // Update a range [l, r] by val
        // Recursively walk down to our children:
        // Either we are completely covered, disjoint, or partially covered
        // KEY POINT: We don't use a mid to break apart the range,
        // since we need to preserve the information about the range.
        // What we ARE splitting is our node into its children, and
        // we'll individually decide which of those children are useful.
        private void updateRangeHelper(int node, int l, int r, boolean val) {
            if (r < lo[node] || hi[node] < l) {
                // we are completely disjoint with the goal range
                return;
            }
            else if (l <= lo[node] && hi[node] <= r) {
                // Complete covering, let's be lazy
                // val is obviously true, but it's aight...
                lazy[node] ^= val;
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
                // If lazy is true, we return decreasing seq instead.
                return lazy[node] ? treeDecr[node] : treeIncr[node];
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