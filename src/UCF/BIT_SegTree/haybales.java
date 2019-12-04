/*
@author derrick20
@problem 2015 USACO Plat 3 Counting Haybales
Basic segment tree with lazy propagation
 */
import java.io.*;
import java.util.*;


public class haybales {
    static long inf = (long) 1e18;

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
//        Scanner sc = new Scanner(new FileReader("haybales.in"));//
//        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("haybales.out")));

        int N = sc.nextInt();
        int Q = sc.nextInt();

        long[] nums = new long[N];
        StringTokenizer st = new StringTokenizer(sc.nextLine());
        for (int i = 0; i < N; i++) {
            long value = Long.parseLong(st.nextToken());
            // have the sum and min be value for now
            // They'll expand
            nums[i] = value;
        }
        SegmentTree minSegTree = new SegmentTree(nums, "M");
        SegmentTree sumSegTree = new SegmentTree(nums, "S");


        for (int c = 0; c < Q; c++) {
            st = new StringTokenizer(sc.nextLine());
            String type = st.nextToken();
            int l = Integer.parseInt(st.nextToken()) - 1;
            int r = Integer.parseInt(st.nextToken()) - 1;
            long res = 0;
            if (type.equals("M")) {
                res = minSegTree.queryRange(l, r);
                out.println(res);
            }
            else if (type.equals("S")) {
                res = sumSegTree.queryRange(l, r);
                out.println(res);
            }
            else {
                long value = Long.parseLong(st.nextToken());
                minSegTree.updateRange(l, r, value);
                sumSegTree.updateRange(l, r, value);
            }

//            for (int i = 0; i < N; i++) {
//                System.out.print("M " + minSegTree.queryRange(i, i) + " ");
//            }
//            System.out.println();
//            for (int i = 0; i < N; i++) {
//            }
//            System.out.println();
        }
        out.close(); //*/
    }

    static class SegmentTree {
        long[] tree;
        long[] arr;
        long[] lazy;
        // Min type or sum type
        String MIN = "M";
        String SUM = "S";
        String type;
        int length;

        public SegmentTree(long[] nums, String type) {
            int k = (int) (Math.log((double) nums.length) / (Math.log(2.0))) + 1;
            arr = new long[(int) (Math.pow(2.0, k))];
            for (int i = 0; i < nums.length; i++) {
                arr[i] = nums[i];
            }
            length = arr.length;
            tree = new long[2 * length];
            lazy = new long[2 * length];
            this.type = type;
            buildTree(1, 0, length - 1); // This is top-down
        }

        public void buildTree(int node, int l, int r) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            // Kick this recursion off with the top node (1) which covers everything. Preorder formation
            if (l == r) {
                tree[node] = arr[l];
            } else {
                int mid = (l + r) / 2;
                buildTree(leftChild, l, mid); // Left node
                buildTree(rightChild, mid + 1, r); // Right node
                tree[node] = merge(tree[leftChild], tree[rightChild]);
            }
        }

        public void updateRange(int start, int end, long value) {
            updateRangeHelper(1, 0, length - 1, start, end, value);
        }

        private void updateRangeHelper(int node, int l, int r, int start, int end, long value) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;

            // disjoint cases or nonsensical cases
            if (l > r || l > end || r < start) {
                // don't do anything
                return;
            }
            else if (start <= l && r <= end) {
                lazy[node] += value;
                pushDown(node, l, r);
                return;
            }
            else {
                // it is partially contained, so need to update things below it since it hasn't been fixed before (lazy)
                pushDown(node, l, r);
                int mid = (l + r) / 2;
                // update left and right children, then combine it within
                // the parent
                updateRangeHelper(leftChild, l, mid, start, end, value);
                updateRangeHelper(rightChild, mid + 1, r, start, end, value);
                pullUp(node, l, r);
                // we might have updated one side but not the other, so
                // in the case of min/max, our parent's value might be wrong
            }
        }

        public void update(int pos, long value) {
            updateHelper(1, 0, length - 1, pos, value);
        }
        // Going top down
        // always call it with node = 1, l = 0, r = length - 1 (and then the pos and value desired
        private void updateHelper(int node, int l, int r, int pos, long value) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            if (l > pos || r < pos) {
                return;
            }
            else if (l == r) {
                tree[node] = value;
            }
            else {
                int mid = (l + r) / 2;
                updateHelper(leftChild, l, mid, pos, value);
                updateHelper(rightChild, mid + 1, r, pos, value);
                tree[node] = merge(tree[leftChild], tree[rightChild]);
            }
        }

        public long queryRange(int start, int end) {
            return queryRangeHelper(1, 0, length - 1, start, end); //start off with the biggest segment
        }

        private long evaluateNode(int node, int l, int r) {
            if (type.equals(SUM))
                return tree[node] + (r - l + 1) * lazy[node];
            else
                return tree[node] + lazy[node];
        }

        // Given some lazy value that has been added, you have to
        // push it into the lower children
        private void pushDown(int node, int l, int r) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            if (type.equals(SUM)) {
                tree[node] += (r - l + 1) * lazy[node]; // add that lazy value
            }
            else {
                tree[node] += lazy[node];
            }
            // if it isn't a leaf node, we can push it into the lower levels
            if (l != r) {
                lazy[leftChild] += lazy[node];
                lazy[rightChild] += lazy[node];
            }
            lazy[node] = 0; // don't double count how many times the lazy values can be added to it
        }

        // At a given node, look at each of its children and look at their values
        // (with the lazy information) and update our current value
        private void pullUp(int node, int l, int r) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            int mid = (l + r) / 2;
            if (type.equals(SUM)) {
                tree[node] = merge((mid - l + 1) * lazy[leftChild] + tree[leftChild], (r - (mid + 1) + 1) * lazy[rightChild] + tree[rightChild]);
            }
            else {
                tree[node] = merge(lazy[leftChild] + tree[leftChild], lazy[rightChild] + tree[rightChild]);
            }
        }

        private long queryRangeHelper(int node, int l, int r, int start, int end) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            // Completely outside of our segment's range. Right edge left of left edge, left edge right of right edge
            if (l > end || r < start) {
                // If outside the range, then return something that'll never contribute to the query answer
                if (type.equals(SUM)) {
                    return 0;
                }
                else {
                    return inf;
                }
            }
            else if (start <= l && r <= end) {
                // Segment is contained
                // but, we must use lazy information still!!
                return evaluateNode(node, l, r);
            }
            else {
                // it is partially covered, so push lazy values downward
                pushDown(node, l, r);
                int mid = (l + r) / 2;
                long right = queryRangeHelper(rightChild, mid + 1, r, start, end);
                long left = queryRangeHelper(leftChild, l, mid, start, end);
                long ret = merge(left, right);
                // Notice importantly that, after you'ved pushed to a portion of
                // the left child and right children z
                // pullUp(node, l, r); // NOTE THIS IS NOT NECESSARY
                // Since it is just a query, nothing below will change,
                // so there is no reason for the current node's value to be updated
                return ret;
            }
        }

        private long merge(long left, long right) {
            if (type.equals(SUM))
                return left + right; //Math.min(left, right);
            else
                return Math.min(left, right);
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

        String nextLine() throws IOException {
            return br.readLine();
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