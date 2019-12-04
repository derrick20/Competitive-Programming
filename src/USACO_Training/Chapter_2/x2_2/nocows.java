/**
ID: d3rrickl
LANG: JAVA
PROG: nocows
 */

import java.io.*;
import java.util.*;

public class nocows {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean isTesting) throws Exception {
        String prefix = isTesting ? "/Users/derrick/IntelliJProjects/src/USACO_Training/Chapter_2/x2_2/" : "";
        sc = new FastScanner(prefix + problem_name + ".in");
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner();
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
        setupIO();;
//        setupIO("nocows", false);

        int N = sc.nextInt();
        int K = sc.nextInt();
        int mod = 9901;
        // Key insight. If we are given N and K, to represent the ways
        // to build the tree, then that should similarly be our state.

        // Then, we should think about how can we deviate N and K, and
        // then merge answers from those smaller problems?
        // Well, the two cases we can do is, we increase height, or decrease it
        // I tried to increase height, but that leads to our state getting
        // weird, with the nodes being wedged between nodes of old state.

        // Rather, we should consider decreasing height. That means, we
        // split into the left and right subtrees. With this, we have
        // two cases of K - 1, and then also x and N - X - 1, as our
        // two subbranches. This therefore suggests a splitting dp, over heights
        // which is (2^K - 1) * K. Since the subtrees can have up to (2^K-1)
        // nodes for height K.
        // We also need to split these nodes stepwise, starting from the
        // minimum possible amount of nodes, then working up to the biggest possible
        // But actually, since they give at most N = 200 nodes, we have
        // a much lower upper bound of N nodes, so we can do O(N^2K)
        int[][] dp = new int[K + 1][N + 1];
        dp[0][0] = 1;
        dp[1][0] = 1;
        dp[1][1] = 1;
        for (int height = 2; height <= K; height++) {
            dp[height][0] = 1;
            // Subtree size must be an odd number.
            int subHeight = height - 1;
            for (int nodes = 1; nodes <= Math.min((1 << height) - 1, N); nodes += 2) {
                // We can't have one side empty, so left must always leave at least 2 of the nodes for the right
                for (int left = 1; left <= Math.min((1 << subHeight) - 1, nodes - 2); left += 2) {
                    int right = nodes - 1 - left;
                    // Left ways * right ways
                    dp[height][nodes] += (dp[subHeight][left] * dp[subHeight][right]) % mod;
                    dp[height][nodes] %= mod;
                }
            }
        }
        out.println(dp[K][N]);
        out.close();
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