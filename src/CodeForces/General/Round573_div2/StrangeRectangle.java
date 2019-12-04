/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class StrangeRectangle {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        ArrayList<Point> points = new ArrayList<>();
        TreeSet<Integer> xVals = new TreeSet();
        for (int i = 0; i < N; i++) {
            Point p = new Point(sc.nextInt(), sc.nextInt());
            points.add(p);
            xVals.add(p.x);
        }
        HashMap<Integer, Integer> xMap = new HashMap<>();
        for (int x : xVals) {
            xMap.put(x, xMap.size());
        }

        // Coordinate Compress it
        for (Point p : points) {
            p.x = xMap.get(p.x);
        }

        Collections.sort(points, (o1, o2) ->
                -(o1.y - o2.y) != 0 ? -(o1.y - o2.y) : o1.x - o2.x);
        // sorted in decr y, incr x.

        // Ranges from 0 to xMap.size() - 1
        SegmentTree segTree = new SegmentTree(new int[xMap.size()]);

        int i = 0;
        int max = xMap.size() - 1;
        long ans = 0;
        while (i < N) {
            Point curr = points.get(i);
            ArrayList<Integer> xCenters = new ArrayList<>();
            xCenters.add(curr.x);
            // Whether or not there was a valid point there, there will be now!
            segTree.update(curr.x, 1);

            // Keep adding anything that is the same y.
            int centers = 1;
            while (i < N - 1 && points.get(i + 1).y == curr.y) {
                curr = points.get(i + 1);
                xCenters.add(curr.x);
                segTree.update(curr.x, 1);
                i++;
                centers++;
            }

            // From each center, see how many ways we could extend
            // the left and right borders.
            // We must add some sort of uniqueness to the way
            // we count to prevent over counting.
            // Let each center be the LEFTMOST center used among them.
            // Then, when we count, we can only go to the previous x, and anything above
            int rem = centers - 1;
            for (int j = 0; j < centers; j++) {
                int xC = xCenters.get(j);
                int prevC = 0;
                if (j > 0) {
                    prevC = xCenters.get(j - 1) + 1;
                }
                int left = segTree.query(prevC, xC);
                int right = segTree.query(xC, max);
                // Don't allow use of the centers on the right side
                ans += (long) (left) * (right);
            }
//            ans += (long) centers * (centers - 1) / 2 + centers;

            // Finished with this batch, so step up
            i++;
        }
        // Can't have
        out.println(ans);
        out.close();
    }

    static int oo = (int) 2e9;

    static class Point {
        int x, y;
        public Point(int myX, int myY) {
            x = myX; y = myY;
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    static class SegmentTree {
        int[] lo, hi;
        int[] tree;
        int[] arr;
        int N;
        int NEUTRAL = 0; // for sum!

        SegmentTree(int[] arr) {
            N = arr.length;
            this.arr = arr;
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
            tree = new int[4 * N + 1];
            buildTree(1, 0, N - 1);
        }

        void buildTree(int node, int l, int r) {
            lo[node] = l;
            hi[node] = r;
            if (l == r) {
                tree[node] = arr[l];
            }
            else {
                int mid = (l + r) / 2;
                int leftChild = 2 * node;
                int rightChild = 2 * node + 1;
                buildTree(leftChild, l, mid);
                buildTree(rightChild, mid + 1, r);
                tree[node] = merge(tree[leftChild], tree[rightChild]);
            }
        }

        void update(int index, int value) {
            updateHelper(1, index, value);
        }

        void updateHelper(int node, int index, int value) {
            if (hi[node] < index || index < lo[node]) {
                return;
            }
            else if (lo[node] == index && index == hi[node]) {
                tree[node] = value;
            }
            else {
                int leftChild = 2 * node;
                int rightChild = 2 * node + 1;
                updateHelper(leftChild, index, value);
                updateHelper(rightChild, index, value);
                tree[node] = merge(tree[leftChild], tree[rightChild]);
            }
        }

        int query(int l, int r) {
            return queryHelper(1, l, r);
        }

        int queryHelper(int node, int l, int r) {
            if (hi[node] < l || r < lo[node]) {
                return NEUTRAL;
            }
            else if (l <= lo[node] && hi[node] <= r) {
                return tree[node];
            }
            else {
                int leftChild = 2 * node;
                int rightChild = 2 * node + 1;
                int leftVal = queryHelper(leftChild, l, r);
                int rightVal = queryHelper(rightChild, l, r);
                return merge(leftVal, rightVal);
            }
        }

        // sum!
        int merge(int leftVal, int rightVal) {
            return leftVal + rightVal;
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