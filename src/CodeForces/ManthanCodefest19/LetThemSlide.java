/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class LetThemSlide {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int W = sc.nextInt();
        long[] maxSum = new long[W];
        long[] delta = new long[W];
        for (int i = 0; i < N; i++) {
            int len = sc.nextInt();
            int[] arr = new int[len];

            for (int j = 0; j < len; j++) {
                arr[j] = sc.nextInt();
            }

//            int max = (int) -1e9 - 5;
//            for (int pos = 0; pos <= len - 1; pos++) {
//                max = Math.max(max, arr[pos]);
//                if (max < 0) {
////                    if (pos < )
//                }
//            }
//            /*
            TreeSet<Integer> bag = new TreeSet<>();
            int back = W - len; // inclusive, for the point to stop
            // when moving from the back
            for (int pos = 0; pos < Math.min(len - 1, back); pos++) {
                bag.add(arr[pos]);
                int val = bag.last();
                if (val < 0 && (len - 1 < pos || pos < back)) {
                    // we are in the region in which it's possible to
                    // either force the slider to the left or to the
                    // right, outside of our zone. So, we can skip it
                    continue;
                }
                else {
                    maxSum[pos] += bag.last();
                }
            }
            if (back > len - 1) {
                // This means there's a gap between the two less mobile
                // regions:
                // ___100000(-1)___
                // We will use prefix sums to fil that area
                // len - 1 is the first position where the bag is full.
                // From there all the way to back - 1, is where we update
                // So, we subtract the value at (back - 1) + 1 (range prefix trick)
                bag.add(arr[len - 1]);
                int val = bag.last();
                delta[len - 1] += val;
                delta[back] -= val;
            }
            bag = new TreeSet<>();
            // Clear it, and now we do it again, but go from the back
            for (int pos = W - 1; pos >= Math.max(len, back); pos--) {
                // Subtract off the pos to get the relative position
                bag.add(arr[len - 1 - (W - 1 - pos)]);
                int val = bag.last();
                if (val < 0 && (len - 1 < pos || pos < back)) {
                    // we are in the region in which it's possible to
                    // either force the slider to the left or to the
                    // right, outside of our zone. So, we can skip it
                    continue;
                }
                else {
                    maxSum[pos] += bag.last();
                }
            }
//
//            SegmentTree segTree = new SegmentTree(arr);
//            for (int pos = 0; pos < W; pos++) {
//                int right = Math.min(len - 1, pos);
//                int left = (len - 1) - Math.min(W - 1 - pos, len - 1);
////                System.out.println("Pos: " + pos + " Left: " + left + " Right: " + right);
//                int add = segTree.query(left, right);
//                if (add < 0) {
//                    if (pos > len - 1 || pos < W - len) {
//                        continue;
//                    }
//                }
//                maxSum[pos] += add;
//            }
//            */
//            System.out.println(Arrays.toString(maxSum));
        }
        for (int i = 1; i < W; i++) {
            delta[i] += delta[i - 1];
        }
        for (int i = 0; i < maxSum.length; i++) {
            out.print(maxSum[i] + delta[i] + " ");
        }
        out.close();
    }

    // todo REMEMBER TO shift everything to be 0-indexed!!
    static class SegmentTree {
        int[] arr;
        int[] tree;
        int[] lo, hi;
        int N;
        // a value that doesn't affect an output (for min it's infinity)
        int NEUTRAL = (int) -1e9 - 5;

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