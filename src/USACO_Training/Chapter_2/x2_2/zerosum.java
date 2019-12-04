/**
 ID: d3rrickl
 LANG: JAVA
 PROG: zerosum
 */
import java.io.*;
import java.util.*;

public class zerosum {
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
      setupIO("zerosum", false);

        int N = sc.nextInt();

        ArrayList<String> ans = new ArrayList<>();
        int max = (int) Math.pow(3, N - 1) - 1;
        // Try all possibilities for each position. 3^(N-1)
        // There are N-2 positions, so
        // 202010
        // 010202
        // 111111

        for (int mask = 0; mask <= max; mask++) {
            int val = 0;
            int power = 1;
            // Power will go from the 1st index to the N-1th index.
            // The 0th index is already done, (+1 ... concatenate with the
            // following stuff)
            int curr = mask;
            // Implicit + in the fronot
            int toAdd = 1;
            int sign = 1;
            StringBuilder sb = new StringBuilder("1");
            while (power < N) {
                // Maintain invariant:
                // the concatenated value is done, and
                // a plus or minus sign is next.
                while (power < N && curr % 3 == 0) {
                    toAdd *= 10;
                    toAdd += (power + 1);
                    sb.append(" " + (power + 1));
                    curr /= 3;
                    power++;
                }
                // Now, we use the old sign to update the value
                val += sign * toAdd;
                if (power == N) {
                    // Don't try to kick off a new phase
                    // NEEDED SPECIAL CASE BECAUSE you can either
                    // end with a concatenated string or with
                    // a singlet. Singlet needs to be added, else not
                    toAdd = 0;
                    break;
                }
                // Now, replace the sign, begin the toAdd chain, and move on
                toAdd = (power + 1);
                if (curr % 3 == 1) {
                    sign = 1;
                    sb.append("+" + (power + 1));
                }
                if (curr % 3 == 2) {
                    sign = -1;
                    sb.append("-" + (power + 1));

                }
                curr /= 3;
                power++;
            }
            if (toAdd != 0) {
                // Add whatever was being kicked off
                val += sign * toAdd;
            }
            if (val == 0) {
                ans.add(sb.toString());
            }
        }
        Collections.sort(ans);
        for (String s : ans) {
            out.println(s);
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