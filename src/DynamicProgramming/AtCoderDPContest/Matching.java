/**
 * @author derrick20
 * Ok, lesson learned. For some reason you simply can't do the top
 */
import java.io.*;
import java.util.*;

public class Matching {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        compatible = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (sc.nextInt() == 1) {
                    compatible[i][j] = true;
                }
            }
        }
        // Top-Down Way
        memo = new long[1 << N];
//        BitSet initial = new BitSet();
        out.println(solve(0));

        /*
        // Iterative way
        // Build from 0 matched, and see how many ways to get up there
        long[] dp = new long[1 << N];
        dp[0] = 1; // 1
        for (int bit = 1; bit < 1 << N; bit++) {
            int man = Integer.bitCount(bit) - 1;
            for (int woman = 0; woman < N; woman++) {
                if (compatible[man][woman] && ((1 << woman) & (bit)) > 0) {
                    dp[bit] += dp[bit - (1 << woman)];
                    dp[bit] %= mod;
                }
            }
        }
        out.println(dp[(1 << N) - 1]);
        */
        out.close();
    }

    static int N;
    static long mod = (long) 1e9 + 7;
    static boolean[][] compatible;
//    static HashMap<Integer, Long> memo = new HashMap<>();
    static long[] memo;
    // Store for some set of remaining things to be paired,
    // how many ways that can be paired off

    static long addSelf(long x, long delta) {
        long ans = x + delta;
        if (ans >= mod) {
            ans -= mod;
        }
        return ans;
    }

    // Key Optimization: Let's have the boys be fixed and the girls be varying.
    // There should only be 1 degree of freedom since that still allows for
    // all combinations
    // So, let curr be the set of girls matched with SOME boy currently.
    // For organization solution bag, suppose they've matched with the first
    // boys possible
    // Each 1 represents a matched woman.
    static long solve(int curr) {
        if (Integer.bitCount(curr) == N) {
            return 1;
        }
        else if (memo[curr] != 0) {
            return memo[curr];
        }
        else {
            long ways = 0;
            int man = Integer.bitCount(curr);
            for (int i = 0; i < N; i++) {
                // If not set, and compatible with the man of interest, then try it
                // O(N transitions * 2^N states)
                if (((1 << i) & curr) == 0 && compatible[man][i]) {
                    // If both haven't been matched yet, do it
                    int next = curr | (1 << i);
                    ways = addSelf(ways, solve(next));
                }
            }
//            memo.put(curr, ways);
            return memo[curr] = ways;
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