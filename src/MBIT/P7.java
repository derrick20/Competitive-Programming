/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class P7 {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int K = sc.nextInt();
        double oo = Double.MAX_VALUE;
        double[] val = new double[N];
        double[] preSum = new double[N + 1];
        double[] preSq = new double[N + 1];
        for (int i = 0; i < N; i++) {
            val[i] = sc.nextDouble();
            preSum[i + 1] += preSum[i] + val[i];
            preSq[i + 1] += preSq[i] + (val[i] * val[i]);
        }
        /*
        9 4
        4 9 6 5 11 25 18 20 16
         */

        double[] dp = new double[N + 1];
        // dp[i] stores the best partitioning having used the first i values
        Arrays.fill(dp, oo);
        dp[0] = 0;

        for (int end = K; end <= N; end++) {
            // There must be at least an area of K members from start to end inclusive
            for (int start = 0; start <= end - K; start++) {
                // Perform function from start to end inclusive
                double C = preSq[end] - preSq[start];
                double B = -2 * (preSum[end] - preSum[start]);
                double A = (end - start);
                double minCost = - (B * B) / (4.0 * A) + C;
//                System.out.println("Cost: " + minCost + " " + C + " " + B + " " + A);

                dp[end] = Math.min(dp[end], dp[start] + minCost);
            }
        }
        out.println((long) dp[N]);
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