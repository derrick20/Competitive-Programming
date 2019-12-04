/*
@author derrick20
@problem 2015 USACO Plat 3 Counting Haybales
Basic segment tree with lazy propagation
 */
import java.io.*;
import java.util.*;


public class haybalesTEST {
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
            int value = Integer.parseInt(st.nextToken());
            // have the sum and min be value for now
            // They'll expand
            nums[i] = value;
        }
        SegmentTree minSegTree = new SegmentTree(nums, "M");
        SegmentTree sumSegTree = new SegmentTree(nums, "S");
//        for (int i = 0; i < N; i++) {
//            out.print(sumSegTree.queryRange(i, i) + " ");
//        }
//        out.println();

        for (int i = 0; i < Q; i++) {
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

        public String toString() {
            String str = "";
            for (int i = 0; i < tree.length; i++) {
                str += i + " " + tree[i] + "\n";
            }
            return str;
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

            // it is partially contained, so need to update things below it since it hasn't been fixed before (lazy)
            if (lazy[node] != 0) {
                if (type.equals(SUM))
                    tree[node] += (r - l + 1) * value;
                else
                    tree[node] += value;

                // this is how many nodes over which that lazy value would've been propagated
                if (l != r) {
                    // that is, it is not a leaf
                    tree[leftChild] += lazy[node];
                    tree[rightChild] += lazy[node];
                }
                lazy[node] = 0; // We've pushed it down, so remove our laziness
            }
            if (l > r || l > end || r < start) {
                return;
            }

            if (l >= start && r <= end) {
                // full contained within range
                // update sum and min
                if (type.equals(SUM))
                    tree[node] += (r - l + 1) * value;
                else
                    tree[node] += value;
                // not a leaf, then we can give its children lazy values
                // to update later
                if (l != r) {
                    lazy[leftChild] += value;
                    lazy[rightChild] += value;
                }
            }
            else {
                int mid = (l + r) / 2;
                // update left and right children, then combine it within
                // the parent
                updateRangeHelper(leftChild, l, mid, start, end, value);
                updateRangeHelper(rightChild, mid + 1, r, start, end, value);
                if (type.equals(SUM)) {
                    tree[node] = merge((mid - l + 1) * lazy[leftChild] + tree[leftChild], (r - (mid + 1) + 1) * lazy[rightChild] + tree[rightChild]);
                }
                else {
                    tree[node] = merge(lazy[leftChild] + tree[leftChild], lazy[rightChild] + tree[rightChild]);
                }
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
            } else if (l == r) {
                tree[node] = value;
            } else {
                int mid = (l + r) / 2;
                updateHelper(leftChild, l, mid, pos, value);
                updateHelper(rightChild, mid + 1, r, pos, value);
                tree[node] = merge(tree[leftChild], tree[rightChild]);
            }
        }

        public long queryRange(int start, int end) {
            return queryRangeHelper(1, 0, length - 1, start, end); //start off with the biggest segment
        }

        private long queryRangeHelper(int node, int l, int r, int start, int end) {
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;

            // Completely outside of our segment's range. Right edge left of left edge, left edge right of right edge
            if (l > end || r < start) {
                if (type.equals(SUM))
                    return 0;
                else
                    return inf;
                // TODO MIGHT CHANGE FOR MIN VS SUM
            }

            // Segment is contained,

            // it is partially covered, so push lazy values downward
            if (lazy[node] != 0) {
                if (type.equals(SUM))
                    tree[node] += (r - l + 1) * lazy[node];
                else
                    tree[node] += lazy[node];
                // we need to be sure to update things
                if (l != r) {
                    lazy[leftChild] += lazy[node];
                    lazy[rightChild] += lazy[node];
                }
                lazy[node] = 0;
            }
            if (start <= l && r <= end) {
                return tree[node];
            }

            int mid = (l + r) / 2;
            long right = queryRangeHelper(rightChild, mid + 1, r, start, end);
            long left = queryRangeHelper(leftChild, l, mid, start, end);
            long ret = merge(left, right);
            return ret;
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