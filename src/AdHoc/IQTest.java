/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class IQTest {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        long N = sc.nextLong();
        dq = new ArrayDeque<>();
        dp = new HashSet<>();
        solve(N);
        while (dq.size() > 0) {
            Pair p = dq.pollFirst();
            out.println(p);
        }
        out.close();
    }

    static ArrayDeque<Pair> dq;
    // Find first 1 that's squared >= value
    // 0011
    static HashSet<Long> dp;
    static void solve(long v) {
        if (v <= 2) {
            return;
        }
        else if (dp.contains(v)) {
            return;
        }
        else {
            long lo = 0;
            long hi = (long) 1e9;
            while (lo < hi) {
                long mid = (lo + hi) / 2;
                if (mid * mid >= v) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            long x = lo;
            long y = lo * lo - v;

            solve(x);
            solve(y);
            // Key idea: we need to add things to our answers in POST-order
            // The reason is that the answers must goo from the leaves of
            // the DP to the root.
            dq.addLast(new Pair(x, y));
            dp.add(v);
        }
    }

    static class Pair {
        long x, y;
        public Pair(long x, long y) {
            this.x = x; this.y = y;
        }
        public String toString() {
            return x + " " + y;
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