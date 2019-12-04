/**
 * @author derrick20
 * Simple knapsack dp, then make it into prefix sums.
 * The key issue you should ALWAYS DO is make prefix sums
 * represent how many elements you've added as the index,
 * and pre[0] = 0 always.
 * Somehow that caused index out of bounds error, hmm.
 *
 */
import java.io.*;
import java.util.*;

public class NapsackQueries {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int Q = sc.nextInt();
        int P = sc.nextInt();

        int[] pillows = new int[P];
        for (int i = 0; i < pillows.length; i++) {
            pillows[i] = sc.nextInt();
        }
        long[] dp = new long[200_001];
        dp[0] = 1;
        for (int fluff = 1; fluff < dp.length; fluff++) {
            for (int pillow : pillows) {
                if (fluff >= pillow) {
                    dp[fluff] += dp[fluff - pillow];
                    dp[fluff] %= mod;
                }
            }
        }
        /*
1 3
1 3 5
100000 1
         */
        // dp[sum] = ways to achieve sum
        long[] prefix = new long[200_001 + 1];
        // prefix[x] stores the sum of the first x dp values
        for (int i = 1; i < prefix.length; i++) {
            prefix[i] = (prefix[i - 1] + dp[i - 1]) % mod;
        }

//        for (int i = 0; i < 10; i++) {
//            System.out.println(dp[i]);
//        }
        while (Q-->0) {
            int f = sc.nextInt() + 1; // from 0 to f
            int d = sc.nextInt();
            // 0 to f + d
            // 0 to f - d - 1
            // what remains is sum(dp) from f - d to f + d
            long ans = (prefix[f + d] - prefix[Math.max(0, f - d - 1)] + mod) % mod;
            out.println(ans);
        }

        out.close();
    }

    static long mod = 1_000_000_007;

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