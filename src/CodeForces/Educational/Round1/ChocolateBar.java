/**
 * @author derrick20
 * Key mistakes:
 * Not initializing the DP base states correctly.
 * I detected this relatively quickly by observing for small cases 2x2
 * Another key fault was analyzing the time complexity incorrectly
 * N^5 can definitely work for something like 30-50. This just gives around
 * 10^7 operations, and all of them are really simple, so nothing to fear!
 */
import java.io.*;
import java.util.*;

public class ChocolateBar {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();

        int oo = (int) 1e9;
        int[][][] dp = new int[31][31][51];
        // dp[w][h][k] = minimum cost to break into parts that sum to k starting
        // from a bar w x h.
        for (int w = 0; w <= 30; w++) {
            for (int h = 0; h <= 30; h++) {
                // Everything else is impossible
                // If w * h < k, we can't possibly fill it
                // If w * h > k, we need to go to subproblems with smaller w, h
                // If w * h == k, then we are DONE
                Arrays.fill(dp[w][h], oo);

                // we can always give you 0 for ZERO cost
                dp[w][h][0] = 0;
                for (int k = 0; k <= 50; k++) {
                    if (w * h == k) {
                        // Base cases
                        dp[w][h][k] = 0;
                    }
                }
            }
        }
        for (int k = 0; k <= 50; k++) {
            for (int w = 0; w <= 30; w++) {
                for (int h = 0; h <= 30; h++) {
                    // From dp[w][h][k], we want to split in any way along the
                    // horizontal: dp[x][h][k] + dp[w - x][h][k] + h * h
                    // Or, along the vertical: dp[w][x][k] + dp[w][h - x][k] + w * w
                    for (int w2 = 1; w2 < w; w2++) {
                        for (int k2 = 0; k2 <= k; k2++) {
                            // Once we've split down a certain w, we can ask each side to give
                            // some amount of k. So we split here from asking none to asking for all

                            // In total, we will have iterated over all possible combinations, since
                            // for each size split on the left side for example, we are asking it
                            // to give all possible amounts from 0 to k. So, this doesn't double count!
                            int subAnswer = dp[w2][h][k2] + dp[w - w2][h][k - k2] + h * h; // cost = h * h !!
                            dp[w][h][k] = Math.min(dp[w][h][k], subAnswer);
                        }
                    }

                    for (int h2 = 1; h2 < h; h2++) {
                        for (int k2 = 0; k2 <= k; k2++) {
                            // Once we've split down a certain h, we can ask each side to give
                            // some amount of k. So we split here from asking none to asking for all
                            int subAnswer = dp[w][h2][k2] + dp[w][h - h2][k - k2] + w * w; // cost = w * w !!
                            dp[w][h][k] = Math.min(dp[w][h][k], subAnswer);
                        }
                    }
                }
            }
        }

//        for (int w = 0; w <= 30; w++) {
//            for (int h = 0; h <= 30; h++) {
//                System.out.print(dp[w][h][1] + " ");
//            }
//            System.out.println();
//        }
        while (T-->0) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int k = sc.nextInt();
            out.println(dp[n][m][k]);
        }

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

        public int[] nextInts(int N) {
            int[] res = new int[N];
            for (int i = 0; i < N; i++) {
                res[i] = (int) nextLong();
            }
            return res;
        }

        public long[] nextLongs(int N) {
            long[] res = new long[N];
            for (int i = 0; i < N; i++) {
                res[i] = nextLong();
            }
            return res;
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