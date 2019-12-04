/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class XeniaBitOperation {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = 1 << sc.nextInt();
        int Q = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }
        int layers = Integer.numberOfTrailingZeros(Integer.highestOneBit(N));

        int type = 1 - (layers % 2);
        SegmentTree segTree = new SegmentTree(arr, type);


        // type 0 = odd power will have the OR top, then XOR, going down
        // if 2^1, then from bottom up will be OR -> OR top, so odd queries

        // type 1 = even power will have XOR top, then OR going down
        // If 2^2, then from bottom up will be OR, XOR -> XOR top, so even queries

        while (Q-->0) {
            int pos = sc.nextInt() - 1;
            int val = sc.nextInt();
            segTree.update(pos, val);
//            System.out.println(segTree);
            out.println(segTree.query(0, N - 1));
        }
        out.close();
    }

    static class SegmentTree {
        int[] arr;
        int[] tree;
        int[] lo, hi;
        int type;
        int N;
        // a value that doesn't affect an output (for min it's infinity)
        int NEUTRAL = 0;
        private int merge(int leftVal, int rightVal, int type) {
            if (type == 0) {
                // OR every other
                return leftVal | rightVal;
            }
            else {
                // XOR every other
                return leftVal ^ rightVal;
            }
        }

        public SegmentTree(int[] arr, int type) {
            this.arr = arr;
            this.type = type;
            N = arr.length;
            tree = new int[4 * N + 1];
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
            // The original bounds of responsibility the root will be [0, N-1]
            constructSegmentTree(1, 0, N - 1, type);
        }

        // Construct a node which is responsible for the range specified: left to right inclusive!
        private void constructSegmentTree(int node, int left, int right, int type) {
            if (left == right) {
                lo[node] = left;
                hi[node] = right;
                tree[node] = arr[left];
            } else {
                lo[node] = left;
                hi[node] = right;
                int mid = (left + right) / 2;
                constructSegmentTree(leftChild(node), left, mid, 1 - type);
                constructSegmentTree(rightChild(node), mid + 1, right, 1 - type);
                tree[node] = merge(tree[leftChild(node)], tree[rightChild(node)], type);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N; i++) {
                sb.append(query(i, i) + " ");
            }
            return sb.toString();
        }

        public int query(int leftBound, int rightBound) {
            return queryHelper(1, leftBound, rightBound, this.type);
        }

        // Query from a fixed range left, right
        private int queryHelper(int node, int leftBound, int rightBound, int type) {
            if (hi[node] < leftBound || rightBound < lo[node]) {
                // We are either too far left or too far right
                return NEUTRAL;
            } else if (leftBound <= lo[node] && hi[node] <= rightBound) {
                // Perfectly contained!
                return tree[node];
            } else {
                // Partial covering
                int leftVal = queryHelper(leftChild(node), leftBound, rightBound, 1 - type);
                int rightVal = queryHelper(rightChild(node), leftBound, rightBound, 1 - type);
                int ans = merge(leftVal, rightVal, type);
                return ans;
            }
        }

        public void update(int index, int val) {
            updateHelper(1, index, val, this.type);
        }

        private void updateHelper(int node, int index, int val, int type) {
            if (hi[node] < index || index < lo[node]) {
                // We are either too far left or too far right
                return;
            } else if (index == lo[node] && hi[node] == index) {
                // Found it!
                tree[node] = val;
            } else {
                // Not there yet!
                updateHelper(leftChild(node), index, val, 1 - type);
                updateHelper(rightChild(node), index, val, 1 - type);
                // Make sure to fix our values on the way back up!
                tree[node] = merge(tree[leftChild(node)], tree[rightChild(node)], type);
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