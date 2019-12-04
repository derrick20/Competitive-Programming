/**
 * @author derrick20
 * Ok this DP was kind of advanced,
 * basically the idea is that we store the optimum cost, so to speak,
 * up to this point.
 * Then, our transition will be either using the previous cost right before,
 * or relying on some router that is within K of us (radius). This means we
 * will then take the cost of the earliest point NOT covered by the nearest router
 * This is represnted by max(next - K - 1, 1), and with that we're done.
 *
 * The only slightly irksome detail in my mind that this isn't actually the
 * optimum sometimes because we could just pick a router ahead of us by just 1,
 * which means we skip the costs behind us, but should also skip the costs
 * ahead of us by K. However, we can resolve this by saying that as we move forward,
 * we would keep relying on this same router, UNTIL we reach that edge point
 * ahead of us earlier.
 * -M----|-R-----|
 * So first we are right next to one, but later we are right on the brink of
 * its forward radius, then we would take dp[M - 1], where M = R - K!
 * So, it's fine that the cost isn't perfect, because later we will move to
 * achieve even better optima!
 *
 * It feels a bit contrived and it just works out i guess
 */
import java.io.*;
import java.util.*;

public class WiFiREDO {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int K = sc.nextInt();
        boolean[] hasRouter = new boolean[N + 1];
        String s = sc.next();
        for (int i = 1; i < hasRouter.length; i++) {
            hasRouter[i] = s.charAt(i - 1) == '1';
        }
        int[] nextRouter = new int[N + 1];
        // Store the nearest position away for a router
        nextRouter[N] = hasRouter[N] ? N : -1;
        for (int i = N - 1; i >= 1; i--) {
            nextRouter[i] = hasRouter[i] ? i : nextRouter[i + 1];
        }
        long[] dp = new long[N + 1];
        long oo = (long) 1e18;
        // Store the min cost having covered the first i routers
        for (int i = 1; i <= N; i++) {
            long noRouter = dp[i - 1] + i;
            long useRouter = oo;
            // If we jump back the radius, anything router in that range will be
            // usable for us. So, if we use that router, then our cost will be
            // at MOST the cost of everything outside of that router's zone, +
            // that router's index. However, since we move forward, along the
            // rooms, we will keep using that same router until we're outside of
            // the radius.
            int next = nextRouter[Math.max(1, i - K)];
            if (next != -1 && next <= i + K) {
                useRouter = dp[Math.max(1, next - K) - 1] + next;
            }
            dp[i] = Math.min(noRouter, useRouter);
        }
        out.println(Arrays.toString(dp));
        out.println(dp[N]);
//        out.println(Arrays.toString(painted));
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