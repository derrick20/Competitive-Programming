/*
 * @author derrick20
 * Ooh killed it one go. Basically 2-pointers, and minimize the max value
 * of the current range. One trick is to have a marker if we need to exit
 * since we ran out of food. Interesting idea is solution bag - if it fails
 * here and we can't add enough food, if we shrink our opportunity space,
 * we necessarily will be unable to solve it moving forward. With that marker
 * be sure to exit immediately to avoid out of bounds error!
 * Clearly, a seg tree was overkill. There were no UPDATES for crying out loud!
 * Just queries implies just a Heap structure - (balanced binary search tree) AKA
 * a TreeMap
 *
 * Lemme
 */
import java.io.*;
import java.util.*;
public class HayFeast {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name) throws Exception {
        sc = new FastScanner(new FileReader(problem_name + ".in"));
        out = new PrintWriter(new FileWriter(problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner(System.in);
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
        setupIO();
        setupIO("hayfeast");
        int N = sc.nextInt();
        long minFlavor = sc.nextLong();

        spice = new int[N];
        int[] flavor = new int[N];
        for (int i = 0; i < N; i++) {
            flavor[i] = sc.nextInt();
            spice[i] = sc.nextInt();
        }
        SegmentTree segTree = new SegmentTree(spice);
        size = (int) Math.sqrt(N) + 1;
        blockAns = new int[size];
        for (int i = 0; i < N; i++) {
            int block = i / size;
            if (i % size == 0) {
                blockAns[block] = spice[i];
            }
            else {
                blockAns[block] = Math.max(blockAns[block], spice[i]);
            }
        }

        int left = 0;
        int right = 0;
        long totalFlavor = 0;
        int minSpice = (int) 2e9;
        boolean ranOut = false;
        while (left < N) {
            while (totalFlavor < minFlavor) {
                if (right >= N) {
                    // If we can't get enough flavor from here
                    // all further lefts will be even LESS
                    // able to get flavor
                    ranOut = true;
                    break;
                }
                totalFlavor += flavor[right];
                right++;
            }
            if (ranOut) {
                break;
            }
            // Right will always be 1 beyond it's true endpoint (inclusive)
//            minSpice = Math.min(minSpice, segTree.query(left, right - 1));
            minSpice = Math.min(minSpice, sqrtQuery(left, right - 1));


            totalFlavor -= flavor[left];
            left++;
        }
        out.println(minSpice);

        out.close();
    }

    static int size;
    static int[] blockAns;
    static int[] spice;

    // Use our sqrt decomposition to query it from left to right inclusive!
    static int sqrtQuery(int left, int right) {
        int blockLeft = left / size;
        int blockRight = right / size;
        int ans = 0;
        // Go through the little extra hanging end on the left side. The left begins at 0 % size
        // so we go until the start of the first FULL block
        for (int i = left, end = (blockLeft + 1) * size - 1; i <= end; i++) {
            ans = Math.max(ans, spice[i]);
        }
        // Go through the meat of the query blocks
        for (int i = blockLeft + 1; i <= blockRight - 1; i++) {
            ans = Math.max(ans, blockAns[i]);
        }
        // Go through the little extra on the right side. The right begins at 0 % size
        for (int i = blockRight * size; i <= right; i++) {
            ans = Math.max(ans, spice[i]);
        }
        return ans;
    }

    // todo REMEMBER TO shift everything to be 0-indexed!!
    static class SegmentTree {
        int[] arr;
        int[] tree;
        int[] lo, hi;
        int N;
        // a value that doesn't affect an output (for min it's infinity)
        int NEUTRAL = 0;
        public int merge(int leftVal, int rightVal) {
            return Math.max(leftVal, rightVal);
        }

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
            } else {
                lo[node] = left;
                hi[node] = right;
                int mid = (left + right) / 2;
                constructSegmentTree(2 * node, left, mid);
                constructSegmentTree(2 * node + 1, mid + 1, right);
                tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
            }
        }

        public int query(int leftBound, int rightBound) {
            return queryHelper(1, leftBound, rightBound);
        }

        // Query from a fixed range left, right
        private int queryHelper(int node, int leftBound, int rightBound) {
            if (hi[node] < leftBound || rightBound < lo[node]) {
                // We are either too far left or too far right
                return NEUTRAL;
            } else if (leftBound <= lo[node] && hi[node] <= rightBound) {
                // Perfectly contained!
                return tree[node];
            } else {
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
            } else if (index == lo[node] && hi[node] == index) {
                // Found it!
                tree[node] = val;
            } else {
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