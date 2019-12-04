/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Flowers {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] height = new int[N + 1];
        int[] beauty = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            height[i] = sc.nextInt();
        }
        for (int i = 1; i <= N; i++) {
            beauty[i] = sc.nextInt();
        }
        long ans = 0;
        long[] dp = new long[N + 1];
        // dp[x] stores, with how far we've progressed in the array
        // with the maximum removed item x, what our current beauty cost is


        /**
        What does a given state need? Once we've figured out the minimum cost
         for the smaller subarray, and we are trying to get a new answer for a bigger
         array. We need to know what things were removed previously, which was
         all determined by what the
            __    /---
          /   \  /     \
         /     \/      \
         */


        out.close();
    }

    static class SegmentTree {
        long[] tree;
        long[] arr;
        int[] lo, hi;
        int N;

        public SegmentTree(long[] arr) {
            N = arr.length;
            tree = new long[4 * N + 1];
            lo = new int[4 * N + 1];
            hi = new int[4 * N + 1];
        }

        public long merge(int leftChild, int rightChild) {
            return Math.max(tree[leftChild], tree[rightChild]);
        }

        public void buildTree(int node, int l, int r) {
            if (l == r) {
                tree[node] = arr[l];
            }
            else {
                int mid = (l + r) / 2;
                buildTree(2 * node, l, mid);
                buildTree(2 * node + 1, mid + 1, r);
                tree[node] = merge(2 * node, 2 * node + 1);
            }
        }

        public void update(int index, int val) {
            updateHelper(1, index, val);
        }

        public void updateHelper(int node, int index, long val) {
            if (hi[node] < index || index < lo[node]) {
                return;
            }
            else if (lo[node] == index && index == hi[node]) {
                tree[node] = val;
            }
            else {
                updateHelper(2 * node, index, val);
                updateHelper(2 * node + 1, index, val);
                tree[node] = merge(2 * node, 2 * node + 1);
            }
        }

//        public long queryHelper(int node, int leftBound, int rightBound) {
//
//        }
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