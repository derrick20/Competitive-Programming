/**
 * @author derrick20
 * KEY ISSUES:
 * Binary
 */
import java.io.*;
import java.util.*;

public class Pillars {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        long d = sc.nextLong();

        long[] height = new long[N];
        sortedHeights = new Pair[N];
        for (int i = 0; i < height.length; i++) {
            height[i] = sc.nextLong();
            sortedHeights[i] = new Pair(height[i], i);
        }
        // With this sorted list, we can perform queries
        // of things outside of a given range (split it into 0...l and r...N)
        Arrays.sort(sortedHeights);
        // However, we need a way to maneuver from a given index in the
        // real position of the heights into the sorted position in the
        // sortedHeights array
        int[] mapToSorted = new int[N];
        for (int i = 0; i < N; i++) {
            // A given position in the actual array, points to the
            // position in the sorted array. This means when we update
            // a certain value for a real position, it is updated at the
            // correct place in the sorted array.
            // In this way, we can query in a sorted structure, even though
            // we are traversing in a non-sorted fashion
            int realPos = sortedHeights[i].idx;
            mapToSorted[realPos] = i;
        }

        SegmentTree dpSegTree = new SegmentTree(new int[N]);
        long[] dp = new long[N];
        // Need this to retrieve the solution
        // The 1st position can always be a 1-long sequence
        dpSegTree.update(mapToSorted[0], 1);
        dp[0] = 1;
        long best = 1;
        int last = 0;

        for (int realPos = 1; realPos < N; realPos++) {
            // Now we need to split the query into two components
            long prevBest = 0;
            long currHeight = height[realPos];

            int left = binarySearchLast(currHeight - d);
            if (left != -1) {
                prevBest = Math.max(prevBest, dpSegTree.queryRange(0, left));
            }
            int right = binarySearchFirst(currHeight + d);
            if (right != -1) {
                prevBest = Math.max(prevBest, dpSegTree.queryRange(right, N - 1));
            }
            dpSegTree.update(mapToSorted[realPos], prevBest + 1);
            dp[realPos] = prevBest + 1;
            if (prevBest + 1 > best) {
                best = prevBest + 1;
                last = realPos;
            }
        }

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        // Now, we traverse backwards and greedily generate the sequence
        // (There's bound to be AT LEAST ONE that fits what was found)
        stack.push(last);
            search: for (int prev = last - 1; prev >= 0; prev--) {
                if (dp[prev] + 1 == dp[last] && Math.abs(height[prev] - height[last]) >= d) {
                    last = prev;
                    stack.push(last);
//                    break search;
                }
            }
// 1 3 5 8 9 10 13 18 19
        out.println(stack.size());
        while (stack.size() > 0) {
            out.print((stack.pop() + 1) + " ");
        }

        out.close();
    }

    static int N;
    static Pair[] sortedHeights;

    // find last position where something <= height occurs,
    // 1111000
    // 10
    static int binarySearchLast(long height) {
        int lo = 0;
        int hi = N - 1;
//        int ans = -1;
        while (lo < hi) {
            int mid = (hi - lo) / 2 + lo + 1; // We must overshoot, and scrape down
            if (sortedHeights[mid].h <= height) {
                // latch onto something that works
                lo = mid;
//                ans = lo;
            }
            else {
                // Scrape downwards
                hi = mid - 1;
            }
        }
        // 2 bad cases:
        // 1111 - the lo = hi = N - 1. So it'll return correctly.
        // 0000 - lo = 0, but really it didn't find anything. So, we track
        // ans to make sure that it truly is 0 and isn't just returning that
        if (sortedHeights[lo].h <= height) {
            return lo;
        }
        else {
            return -1;
        }
    }

    // Find the first occurrence of a value >= height
    // 0011
    // 01 - we need mid to undershoot to detect failures. Scrape upward
    static int binarySearchFirst(long height) {
        int lo = 0;
        int hi = N - 1;
        while (lo < hi) {
            int mid = (hi - lo) / 2 + lo; // Undershoot
            if (sortedHeights[mid].h >= height) {
                hi = mid; // For sure this works, so latch on
//                ans = hi;
            }
            else {
                // Then we need to scrape up (everything including
                // mid is bad)
                lo = mid + 1;
            }
        }
        // If everything was < height, 0000, then ans would stay -1.
        // If everything >= height, 1111, we would rightfully return 0
        if (sortedHeights[lo].h >= height) {
            return lo;
        }
        else {
            return -1;
        }
    }

    static class Pair implements Comparable<Pair> {
        int idx;
        long h;
        public Pair(long height, int index) {
            idx = index; h = height;
        }
        public int compareTo(Pair p2) {
            // Cool signum!!
            return h - p2.h != 0 ? Long.signum(h - p2.h) : idx - p2.idx;
        }
    }

    // Segment tree only updates/changes value. Can't add to a range! (need lazy)
    static class SegmentTree {
        int[] arr;
        long[] tree;
        int[] lo, hi;
        int N;
        // a value that doesn't affect an output (for min it's infinity)
        long NEUTRAL = 0;
        private long merge(long leftVal, long rightVal) {
            return Math.max(leftVal, rightVal);
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

        public void update(int index, long val) {
            updateHelper(1, index, val);
        }

        private void updateHelper(int node, int index, long val) {
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