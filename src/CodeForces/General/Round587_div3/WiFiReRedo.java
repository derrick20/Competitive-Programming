/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class WiFiReRedo {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int K = sc.nextInt();
        boolean[] hasRouter = new boolean[N + 1];
        String s = "0" + sc.next();
        for (int i = 0; i < hasRouter.length; i++) {
            hasRouter[i] = s.charAt(i) == '1';
        }
        ArrayDeque<Pair> queue = new ArrayDeque<>();
        queue.add(new Pair(0, 0));
        long[] dp = new long[N + K + 1];
        long best = (long) 1e18;
        for (int i = 1; i <= N + K; i++) {
            System.out.println(queue);
            // get rid of stuff out of range
            while (i - 2 * K - 1 > queue.getFirst().i) {
                queue.pollFirst();
            }
            dp[i] = i + dp[i - 1];
            if (i > K && hasRouter[i - K]) {
                dp[i] = Math.min(dp[i], i - K + queue.getFirst().v);
            }
            if (i >= N) {
                best = Math.min(best, dp[i]);
            }
            // Get rid of stuff that's clearly useless
            while (queue.size() > 0 && queue.getLast().v >= dp[i]) {
                queue.pollLast();
            }
            queue.addLast(new Pair(i, dp[i]));
        }
        out.println(best);
        out.close();
    }

    static class Pair {
        int i;
        long v;
        public Pair(int ii, long vv) {
            i = ii; v = vv;
        }

        public String toString() {
            return "i: " + i + ", v: " + v + "\n";
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