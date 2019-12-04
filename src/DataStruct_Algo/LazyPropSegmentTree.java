/**
 * @author derrick20
 * Tested very rigorously on 1263, E from round 603
 * Realize that leftChild(node) is slow, just use 2 * node
 * since you won't have to type it too much. Also,
 * if you ever really need to type it for some reason, just
 * store it in a variable
 *
 * Right now, it has min and max, so you can get rid of those easily
 */
import java.io.*;
import java.util.*;

public class LazyPropSegmentTree {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.readInt();
        char[] steps = sc.readString(N).toCharArray();
        int size = 1;
        for (char c : steps) {
            if (c == 'R') {
                size++;
            }
        }
        SegmentTree segTree = new SegmentTree(new int[size]);

        int pos = 0;
        char[] text = new char[size];
        int pair = 0;
        int ans = 0;
        for (char c : steps) {
            if (c == 'R') {
                pos++;
            }
            else if (c == 'L') {
                if (pos > 0) {
                    pos--;
                }
            }
            else {
                // lose what was there
                int add = 0;
                if (text[pos] == '(') {
                    add -= 1;
                    // lost an opening
                }
                else if (text[pos] == ')') {
                    add += 1;
                }

                // Gain what is added
                if (c == '(') {
                    add += 1;
                }
                else if (c == ')') {
                    add -= 1;
                }

                pair += add;
                segTree.updateRange(pos, size - 1, add);
                text[pos] = c;
                ans = -1;
                // Very clever idea: take a step back and realize that
                // ALL OF THESE QUERIES are just asking what the max/min
                // of the whole structure are, so just grab it directly
                // instead of querying and wasting log(N) factor !!
                // todo I COMMENTED BELOW OUT SO DON'T TRY RUNNING IT and submitting to the actual problem
                // todo called "EDITOR"
//                if (pair == 0 && segTree.min[1] + segTree.delta[1] >= 0) {
//                    // Then it's balanced parens
//                    ans = (int) (segTree.max[1] + segTree.delta[1]);
//                }
            }
            out.print(ans + " ");
        }
        out.close();
    }

    static class SegmentTree {
        int[] lo, hi;
        int[] arr;
        long[] max, delta;
        long NEUTRAL = (long) -9e18;
        int N;

        public SegmentTree(int[] arr) {
            N = arr.length;
            this.arr = arr;
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
            max = new long[4 * N + 1];
            delta = new long[4 * N + 1];
            buildTree(1, 0, N - 1);
        }

        private void buildTree(int node, int l, int r) {
            if (l == r) {
                max[node] = arr[l];

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

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N; i++) {
                sb.append(queryMax(i, i) + " ");
            }
            return sb.toString();
        }

        /**
         * Parts you need to modify !
         * 1. How do you apply the aggregate lazy/delta value?
         *      - Fix this by changing the eval() function(s)
         *      - Min and max, just add delta. Sum, add (hi[node] - lo[node] + 1) * delta
         * 2. How do you merge the two values you get from your children?
         *      - Fix this by changing the pullUp() function
         *      - Maximize, minimize, sum, GCD, XOR, etc.
         * 3. What are the out-of-bounds values?
         *      - Fix this by changing the NEUTRAL VALUE
         */

//        private long evaluateSum(int node) {
//            return sum[node] + (hi[node] - lo[node] + 1) * delta[node];
//        }

        private long evalMax(int node) {
            return max[node] + delta[node];
        }

        private void pullUp(int node) {
            max[node] = Math.max(evalMax(2 * node), evalMax(2 * node + 1));
        }

        private void pushDown(int node) {
            delta[2 * node] += delta[node];
            delta[2 * node + 1] += delta[node];
            delta[node] = 0;
        }

        public long queryMax(int l, int r) {
            return queryMaxHelper(1, l, r);
        }

        private long queryMaxHelper(int node, int l, int r) {
            if (r < lo[node] || hi[node] < l) {
                return NEUTRAL;
            }
            else if (l <= lo[node] && hi[node] <= r) {
                return evalMax(node);
            }
            else {
                pushDown(node);
                long leftResult = queryMaxHelper(2 * node, l, r);
                long rightResult = queryMaxHelper(2 * node + 1, l, r);
                pullUp(node);
                return Math.max(leftResult, rightResult);
            }
        }

        public void updateRange(int l, int r, int val) {
            updateRangeHelper(1, l, r, val);
        }

        private void updateRangeHelper(int node, int l, int r, int val) {
            if (r < lo[node] || hi[node] < l) {
                return;
            }
            else if (l <= lo[node] && hi[node] <= r) {
                delta[node] += val;
            }
            else {
                pushDown(node);
                updateRangeHelper(2 * node, l, r, val);
                updateRangeHelper(2 * node + 1, l, r, val);
                pullUp(node);
            }
        }
    }

    static final class FastScanner {
        private final InputStream stream;
        private final byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public FastScanner() {
            this.stream = System.in;
        }

        private int read() throws IOException {
            if (curChar >= numChars) {
                curChar = 0;
                numChars = stream.read(buf);
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public final String readString() throws IOException {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.append((char) c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public final String readString(int L) throws IOException {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder(L);
            do {
                res.append((char) c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public final int readInt() throws IOException {
            int c = read();
            boolean neg = false;
            while (isSpaceChar(c)) {
                c = read();
            }
            char d = (char) c;
            // log("d:"+d);
            if (d == '-') {
                neg = true;
                c = read();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            // log("res:"+res);
            if (neg)
                return -res;
            return res;

        }

        public final long readLong() throws IOException {
            int c = read();
            boolean neg = false;
            while (isSpaceChar(c)) {
                c = read();
            }
            char d = (char) c;
            // log("d:"+d);
            if (d == '-') {
                neg = true;
                c = read();
            }
            long res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            // log("res:"+res);
            if (neg)
                return -res;
            return res;

        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }
    }
}