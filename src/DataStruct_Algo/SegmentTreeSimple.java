/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;
public class SegmentTreeSimple {

    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int N = sc.nextInt();
        int Q = sc.nextInt();

        int[] nums = new int[N];
        for (int i = 0; i < N; i++) {
            nums[i] = sc.nextInt();
        }
        SegmentTree segTree = new SegmentTree(nums);

        for (int i = 0; i < Q; i++) {
            String s = sc.next();
            if (s.equals("q")) {
                int l = sc.nextInt() - 1;
                int r = sc.nextInt() - 1;
                out.println(segTree.queryRange(l, r));
            }
            else if (s.equals("u")) {
                int node = sc.nextInt() - 1;
                int val = sc.nextInt();
                segTree.update(node, val);
            }
        }
        out.close();
    }

    // todo REMEMBER TO shift everything to be 0-indexed!!
    static class SegmentTree {
        int[] arr;
        long[] tree;
        int[] lo, hi;
        int N;
        // a value that doesn't affect an output (for min it's infinity)
        long NEUTRAL = (long) 1e18;
        private long merge(long leftVal, long rightVal) {
            return Math.min(leftVal, rightVal);
        }

        public SegmentTree(int[] arr) {
            this.arr = arr;
            N = arr.length;
            tree = new long[4 * N + 1];
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
            // The original bounds of responsibility the root will be [0, N-1]
            constructSegmentTree(1, 0, N - 1);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N; i++) {
                sb.append(queryRange(i, i) + " ");
            }
            return sb.toString();
        }

        // Construct a node which is responsible for the range specified: left to right inclusive!
        private void constructSegmentTree(int node, int left, int right) {
            if (left == right) {
                lo[node] = left;
                hi[node] = right;
                tree[node] = arr[left];
            } else {
                lo[node] = left;
                hi[node] = right;
                int mid = (left + right) / 2;
                constructSegmentTree(leftChild(node), left, mid);
                constructSegmentTree(rightChild(node), mid + 1, right);
                tree[node] = merge(tree[leftChild(node)], tree[rightChild(node)]);
            }
        }

        public long queryRange(int leftBound, int rightBound) {
            return queryRangeHelper(1, leftBound, rightBound);
        }

        // Query from a fixed range left, right
        private long queryRangeHelper(int node, int leftBound, int rightBound) {
            if (hi[node] < leftBound || rightBound < lo[node]) {
                // We are either too far left or too far right
                return NEUTRAL;
            } else if (leftBound <= lo[node] && hi[node] <= rightBound) {
                // Perfectly contained!
                return tree[node];
            } else {
                // Partial covering
                long leftVal = queryRangeHelper(leftChild(node), leftBound, rightBound);
                long rightVal = queryRangeHelper(rightChild(node), leftBound, rightBound);
                long ans = merge(leftVal, rightVal);
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
                updateHelper(leftChild(node), index, val);
                updateHelper(rightChild(node), index, val);
                // Make sure to fix our values on the way back up!
                tree[node] = merge(tree[leftChild(node)], tree[rightChild(node)]);
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