/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class RadioContact {
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
//        setupIO();
      setupIO("radio", false);

        int N = sc.nextInt();
        int M = sc.nextInt();
        fx = new long[N + 1];
        fy = new long[N + 1];
        bx = new long[M + 1];
        by = new long[M + 1];
        // Each of these stores the specific position after the first
        // i moves.
        // Doesn't have to be longs, but just to be safe!
        fx[0] = sc.nextLong();
        fy[0] = sc.nextLong();
        bx[0] = sc.nextLong();
        by[0] = sc.nextLong();
        // Precomp the positions at certain points
        String FPath = "_" + sc.next();
        for (int i = 1; i <= N; i++) {
            if (FPath.charAt(i) == 'N') {
                fx[i] = fx[i - 1];
                fy[i] = fy[i - 1] + 1;
            }
            else if (FPath.charAt(i) == 'S') {
                fx[i] = fx[i - 1];
                fy[i] = fy[i - 1] - 1;
            }
            else if (FPath.charAt(i) == 'E') {
                fx[i] = fx[i - 1] + 1;
                fy[i] = fy[i - 1];
            }
            else if (FPath.charAt(i) == 'W') {
                fx[i] = fx[i - 1] - 1;
                fy[i] = fy[i - 1];
            }
        }
        String BPath = "_" + sc.next();
        for (int i = 1; i <= M; i++) {
            if (BPath.charAt(i) == 'N') {
                bx[i] = bx[i - 1];
                by[i] = by[i - 1] + 1;
            }
            else if (BPath.charAt(i) == 'S') {
                bx[i] = bx[i - 1];
                by[i] = by[i - 1] - 1;
            }
            else if (BPath.charAt(i) == 'E') {
                bx[i] = bx[i - 1] + 1;
                by[i] = by[i - 1];
            }
            else if (BPath.charAt(i) == 'W') {
                bx[i] = bx[i - 1] - 1;
                by[i] = by[i - 1];
            }
        }

        long[][] dp = new long[N + 1][M + 1];
        // dp[i][j] stores the minimum cost having done the first i and
        // j directions of F's and B's paths respectively.
        long oo = (long) 1e18;
        for (int i = 0; i <= N; i++) {
            Arrays.fill(dp[i], oo);
        }
        dp[0][0] = 0;
        // Transition. We use the cost before, either i - 1 and/or j - 1,
        // then add the cost of being here at i, j
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                 if (i > 0) {
                     dp[i][j] = Math.min(dp[i][j], dist(i, j) + dp[i - 1][j]);
                 }
                 if (j > 0) {
                     dp[i][j] = Math.min(dp[i][j], dist(i, j) + dp[i][j - 1]);
                 }
                 if (i > 0 && j > 0) {
                     dp[i][j] = Math.min(dp[i][j], dist(i, j) + dp[i - 1][j - 1]);
                 }
            }
//            System.out.println(Arrays.toString(dp[i]));
        }
        out.println(dp[N][M]);
        out.close();
    }

    static long[] fx, fy, bx, by;
    static long dist(int i, int j) {
        return (fx[i] - bx[j]) * (fx[i] - bx[j]) + (fy[i] - by[j]) * (fy[i] - by[j]);
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