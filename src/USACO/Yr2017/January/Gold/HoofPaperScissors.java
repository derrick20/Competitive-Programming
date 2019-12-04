/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class HoofPaperScissors {
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
      setupIO("hps", false);

        int N = sc.nextInt();
        int K = sc.nextInt();
        int[] arr = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            String c = sc.next();
            System.out.println(c);
            if (c.charAt(0) == 'H') {
                arr[i] = 0;
            }
            else if (c.charAt(0) == 'P') {
                arr[i] = 1;
            }
            else if (c.charAt(0) == 'S') {
                arr[i] = 2;
            }
        }
        int[][] win = {
                {0, 0, 1},
                {1, 0, 0},
                {0, 1, 0}
        };
        int[][][] dp = new int[3][K + 1][N + 1];
        // dp[type][k][n] stores the max # of wins after switching k times,
        // over the first n parts, inclusive, when the end of the last
        // segment was type "type"
        // O(N*K*9) = 120*10^5
//        System.out.println(Arrays.toString(arr));
        for (int pos = 1; pos <= N; pos++) {
            for (int used = K; used >= 0; used--) {
                for (int last = 0; last <= 2; last++) {
                    for (int curr = 0; curr <= 2; curr++) {
                        // Two possibilities. The last to curr is a change, or the same
                        if (curr != last) {
                            // If we change, there is potential index out of bounds
                            if (used + 1 <= K) {
                                dp[curr][used + 1][pos] = Math.max(dp[curr][used + 1][pos], dp[last][used][pos - 1]  + win[curr][arr[pos]]);
                            }
                        }
                        else if (curr == last) {
                            dp[curr][used][pos] = Math.max(dp[curr][used][pos], dp[last][used][pos - 1] + win[curr][arr[pos]]);
                        }
                    }
                }
//                System.out.println("Pos: " + pos + " Used: " + used);
//                System.out.println(Arrays.toString(dp[0][used]));
//                System.out.println(Arrays.toString(dp[1][used]));
//                System.out.println(Arrays.toString(dp[2][used]));

            }
        }
        int best = 0;
        for (int used = 0; used <= K; used++) {
            best = Math.max(dp[0][used][N], Math.max(dp[1][used][N], dp[2][used][N]));
        }
        out.println(best);
//        out.println(Math.max(dp[0][K][N], Math.max(dp[1][K][N], dp[2][K][N])));
        out.close();
    }

    static class FastScanner {
        public int BS = 1<<16;
        public char NC = (char)0;
        byte[] buf = new byte[BS];
        int bId = 0, size = 0 ;
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