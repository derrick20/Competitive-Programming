/**
 ID: d3rrickl
 LANG: JAVA
 PROG: nocows
 */
import java.io.*;
import java.util.*;

public class nocowsREDO {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean testing) throws Exception {
        String prefix = testing ? "/Users/derrick/IntelliJProjects/src/USACO/" : "";
        sc = new FastScanner(prefix + problem_name + ".in");
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner();
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
        setupIO();
//      setupIO("nocows", false);

        int N = sc.nextInt();
        int K = sc.nextInt();
        int mod = 9901;
        int[][] dp = new int[K + 1][N + 1];
        int[][] smaller = new int[K + 1][N + 1];
        // dp[k][n] stores the number of pedigrees with n nodes that have
        // height <= k (at least one node of depth k).
        // The problem with just being = to k, is that we would only allow pedigrees
        // where both children subtrees are height k - 1, when only one of them needs to be.

        // So, we extend it to include any VALID subtree with this number of nodes, <= the
        // desired height.

        // Clever modification is to store these in two separate components, with
        // one part being the trees <=, and the other being strictly the answer
        dp[1][1] = 1;
        System.out.println("Height: " + 1 + " " + Arrays.toString(dp[1]));

        for (int height = 2; height <= K; height++) {
            int maxNodes =  N;
            for (int total = 1; total <= maxNodes; total += 2) {
                int available = total - 1;
                for (int left = 1; left <= available; left += 2) {
                    int right  = available - left;
                    dp[height][total] += dp[height - 1][left] * dp[height - 1][right];
                    dp[height][total] += smaller[height - 2][left] * dp[height - 1][right];
                    dp[height][total] += dp[height - 1][left] * smaller[height - 2][right];

                    dp[height][total] %= mod;
                }
            }

            // Now back up a bit and update all the smaller trees dp table
            // The intuition is we need to keep the cascading layer of
            // less than trees right on our tail, so we can use it. We can't update
            // it until until the prevheight is done.
            for (int total = 1; total <= maxNodes; total += 2) {
                int less = smaller[height - 2][total];
                int equal = dp[height - 1][total];
                smaller[height - 1][total] = (less + equal) % mod;
            }
//            System.out.println("Less : " + height + " " + Arrays.toString(smaller[height - 1]));
//            System.out.println("Equal: " + height + " " + Arrays.toString(dp[height - 1]));
            System.out.println();
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