/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class LISSegTree {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }
        coordinateCompress(arr);

        // For each value, store the LONGEST subseq that ends on that value
        // Then, when we are transitioning to a new value in the array,
        // we rely on the current solution bag and use the biggest sequence
        // with an end value strictly less than us (max query of dp from 1 to val - 1)
        int maxValue = 0;
        for (int value : arr) {
            maxValue = Math.max(maxValue, value);
        }
//        int[] dp = new int[maxValue + 1];
        SegmentTree dp = new SegmentTree(new int[maxValue + 1]);
        // Bottom-up DP time!
        for (int i = 0; i < arr.length; i++) {
            int value = arr[i];
            // It's a given that we can just start ourselves as a sequence
            int bestLength = 1;
            // Go through all previously constructed increasing subsequences!
            int buildOn = dp.query(0, value - 1) + 1;
            // Two choices: either start anew, or build on top of old subsequences
            bestLength = Math.max(bestLength, buildOn);
            // Update our segTree, for future queries!
            dp.update(value, bestLength);
        }

        int ans = dp.query(0, maxValue);
        // Ta-da!
        out.println(ans);
        out.close();
    }

    static void coordinateCompress(int[] arr) {
        TreeSet<Integer> values = new TreeSet<>();
        // Get rid of duplicate values
        for (int i = 0; i < arr.length; i++) {
            values.add(arr[i]);
        }
        // Map those onto their index in the sorted order
        HashMap<Integer, Integer> valToIndex = new HashMap<>();
        for (int value : values) {
            valToIndex.put(value, valToIndex.size());
        }
        // Apply that map on the original array to compress it
        for (int i = 0; i < arr.length; i++) {
            arr[i] = valToIndex.get(arr[i]);
        }
    }

    static class SegmentTree {
        int[] arr;
        int[] tree;
        int[] lo, hi;
        int N;
        // a value that doesn't affect an output (for max it's 0)
        int NEUTRAL = 0;

        public SegmentTree(int[] arr) {
            this.arr = arr;
            int N = arr.length;
            tree = new int[4 * N + 1];
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
            // The original bounds of responsibility the root will be [0, N-1]
            constructSegmentTree(1, 0, N - 1);
        }

        // Construct a node which is responsible for the range specified: left to right inclusive!
        public void constructSegmentTree(int node, int left, int right) {
            if (left == right) {
                lo[node] = left;
                hi[node] = right;
                tree[node] = arr[left];
            }
            else {
                lo[node] = left;
                hi[node] = right;
                int mid = (left + right) / 2;
                constructSegmentTree(2 * node, left, mid);
                constructSegmentTree(2 * node + 1, mid + 1, right);
                tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
            }
        }

        public int merge(int leftVal, int rightVal) {
            return Math.max(leftVal, rightVal);
        }

        public int query(int leftBound, int rightBound) {
            return queryHelper(1, leftBound, rightBound);
        }

        // Query from a fixed range left, right
        private int queryHelper(int node, int leftBound, int rightBound) {
            if (hi[node] < leftBound || rightBound < lo[node]) {
                // We are either too far left or too far right
                return NEUTRAL;
            }
            else if (leftBound <= lo[node] && hi[node] <= rightBound) {
                // Perfectly contained!
                return tree[node];
            }
            else {
                // Partial covering
                int leftVal = queryHelper(2 * node, leftBound, rightBound);
                int rightVal = queryHelper(2 * node + 1, leftBound, rightBound);
                int ans = merge(leftVal, rightVal);
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
            }
            else if (index == lo[node] && hi[node] == index) {
                // Found it!
                tree[node] = val;
            }
            else {
                // Not there yet!
                updateHelper(2 * node, index, val);
                updateHelper(2 * node + 1, index, val);
                // Make sure to fix our values on the way back up!
                tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
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
