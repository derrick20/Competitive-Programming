/*
 * @author derrick20
 * Tested on Code Forces: CircularRMQ
 * A few things that were tricky:
 * There's a major difference between the buildTree function and the
 * updateRange and queryRange (which are together very similar).
 * The slightly different motivation is that building the tree, the thing
 * we are changing is the range over which a certain node rules, and
 * we aren't looking to perform some query/update over a fixed range. Rather,
 * we want to build up our segTree until we reach the leaves (where left == right bound)
 *
 * On the other hand, the update and query methods are aiming to look for the answer
 * or perform some action over a FIXED range. That is given in the parameter
 * arguments as l and r. Those DO NOT CHANGE, rather, the node (and therefore the
 * range associated which it controls) is what does change. As we break down
 * to our children, we shrink our range controlled, which allows an increasingly fine
 * grained view of whether our range helps our cause (that is, it is completely contained)
 * or if it is useless (meaning it is disjoint). If we can't decide this for certain yet,
 * because it is a partial covering, we must keep refining into smaller children (and smaller ranges)
 * until we CAN decide, essentially until we reach only base cases and can end our efforts!
 */
import java.io.*;
import java.util.*;

public class betterLazyPropSegmentTree {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] arr =  new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }

        SegmentTree segTree = new SegmentTree(arr);

        int Q = sc.nextInt();
        while (Q-->0) {
            String line = sc.nextLine();
            String[] info = line.split(" ");
            int l = Integer.parseInt(info[0]);
            int r = Integer.parseInt(info[1]);
//            System.out.println(segTree.display(1, 0));
            if (l <= r) {
                if (info.length == 3) {
                    int val = Integer.parseInt(info[2]);
                    segTree.updateRange(l, r, val);
                } else {
                    long ans = segTree.queryRange(l, r);
                    out.println(ans);
                }
            }
            else {
                // circular time!
                int l1 = 0;
                int r1 = r;
                int l2 = l;
                int r2 = arr.length - 1;
                if (info.length == 3) {
                    int val = Integer.parseInt(info[2]);
                    segTree.updateRange(l1, r1, val);
                    segTree.updateRange(l2, r2, val);
                }
                else {
                    long ans1 = segTree.queryRange(l1, r1);
                    long ans2 = segTree.queryRange(l2, r2);
                    out.println(Math.min(ans1, ans2));
                }
            }
        }
        out.close();
    }

    static int inf = (int) 1e9;

    static class SegmentTree {
        // keep track of the lo and hi bounds controlled of a certain node
        int[] lo, hi;
        int[] arr;
        long[] tree, lazy;
        long NEUTRAL = (long) 1e18;
        // todo MAKE SURE NEUTRAL IS CORRECT
        int N;

        // Depending on the use case, the segment tree may merge
        // the values of two nodes via adding, xor, etc.
        public long merge(long leftVal, long rightVal) {
            return Math.min(leftVal, rightVal);
        }

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
            long leftVal = tree[l] + lazy[l];
            long rightVal = tree[r] + lazy[r];
            // todo sum: leftVal = tree[l] + (hi[l] - lo[l] + 1) * lazy[l]
            tree[node] = merge(leftVal, rightVal);
        }

        // transfer the lazy information stored at a node down to its children
        private void pushDown(int node) {
            lazy[leftChild(node)] += lazy[node];
            lazy[rightChild(node)] += lazy[node];
            lazy[node] = 0;
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
                // min:
                return tree[node] + lazy[node];
                // sum:
                // todo return tree[node] + (hi[node] - lo[node] + 1) * lazy[node]
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

    static interface QueryInterface {
        public void updateRange(int l, int r, int val);
        public long queryRange(int l, int r);
    }

    // Brute-force class to test against
    static class SlowRangeQuery implements QueryInterface {
        long[] arr;

        public SlowRangeQuery(long[] arr) {
            this.arr = arr;
        }

        public void updateRange(int l, int r, int val) {
            for (int i = l; i <= r; i++) {
                arr[i] += val;
            }
        }

        public long queryRange(int l, int r) {
            long min = arr[l];
            for (int i = l; i <= r; i++) {
                min = Math.min(min, arr[i]);
            }
            return min;
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

        String nextLine() throws IOException {
            return br.readLine();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}