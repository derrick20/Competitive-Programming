/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class welfareState {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] money = new int[N];
        for (int i = 0; i < money.length; i++) {
            money[i] = sc.nextInt();
        }
        int Q = sc.nextInt();
        SegmentTree segTree = new SegmentTree(money);
        for (int i = 0; i < Q; i++) {
            int type = sc.nextInt();
            if (type == 1) {
                int p = sc.nextInt() - 1;
                int x = sc.nextInt();
                segTree.updateRange(p, p, x, 1);
            }
            else {
                int x = sc.nextInt();
                segTree.updateRange(0, N - 1, x, 2);
            }
//            System.out.println(segTree.display(1, 0));
        }
        for (int i = 0; i < money.length; i++) {
            out.print(segTree.queryRange(i, i) + " ");
        }
        out.println();
        out.close();
    }
    static int inf = (int) 1e9;
    static class SegmentTree {
        // keep track of the lo and hi bounds controlled of a certain node
        int[] lo, hi;
        int[] arr;
        long[] tree, lazy;

        public SegmentTree(int[] arr) {
            int N = arr.length;
            this.arr = arr;
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
            tree = new long[4 * N + 1];
            lazy = new long[4 * N + 1];
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
            toRet += node + ": " + "(" + tree[node] + ", " + lazy[node] + "), " + "\n";
            toRet += display(2 * node, level + 1); //recurse left
            return toRet;
        }

        // update a node with its children's new information, as a result
        // of potential changes due to recursion down in each subtree
        // Must use the lazy information
        public void pullUp(int node) {
            long leftVal = tree[2 * node] + lazy[2 * node];
            long rightVal = tree[2 * node + 1] + lazy[2 * node + 1];
            tree[node] = merge(leftVal, rightVal);
        }

        // transfer the lazy information stored at a node down to its children
        public void pushDown(int node) {
            lazy[2 * node] = Math.max(lazy[2 * node], lazy[node]);
            lazy[2 * node + 1] = Math.max(lazy[2 * node + 1], lazy[node]);
            if (tree[node] < lazy[node]) {
                tree[node] = lazy[node];
            }
            lazy[node] = 0;
        }

        // Depending on the use case, the segment tree may merge
        // the values of two nodes via adding, xor, etc.
        public long merge(long leftVal, long rightVal) {
            return Math.min(leftVal, rightVal);
        }

        // Always begin the updating with the root of the tree
        public void updateRange(int l, int r, int val, int type) {
            updateRangeHelper(1, l, r, val, type);
        }

        // Type 1 is expense, 2 is payoff
        public void updateRangeHelper(int node, int l, int r, int val, int type) {
            if (r < lo[node] || hi[node] < l) {
                // we are completely disjoint with the goal range
                return;
            }
            else if (l <= lo[node] && hi[node] <= r) {
                // Complete covering, let's be lazy
                if (type == 2) {
                    lazy[node] = Math.max(lazy[node], val);
                }
                else {
                    // had to pay it, but we update it real quick
                    if (tree[node] < lazy[node]) {
                        tree[node] = lazy[node];
                    }
                    // we can not use that lazy amount anymore
                    lazy[node] = 0;
                    tree[node] = val;
                }
            }
            else {
                // Partial covering, forced to recurse down.
                // We may have lazy values from previous updates, so
                // we must propagate it downward
//                if (type == 2) {
                pushDown(node);
//                }

                // Recurse on children
                updateRangeHelper(2 * node, l, r, val, type);
                updateRangeHelper(2 * node + 1, l, r, val, type);

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
                return inf;
            }
            else if (l <= lo[node] && hi[node] <= r) {
                // We must apply any lazy values that had been placed here earlier
                if (tree[node] < lazy[node]) {
                    return lazy[node];
                }
                else {
                    return tree[node];
                }
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
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        FastScanner(FileReader s) {
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

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
