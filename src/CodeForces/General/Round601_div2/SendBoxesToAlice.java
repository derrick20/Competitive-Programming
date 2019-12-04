/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class SendBoxesToAlice {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        long[] arr = sc.nextLongs(N);
        long sum = 0;
        for (long v : arr) {
            sum += v;
        }

        ArrayList<Long> factors = new ArrayList<>();
        boolean[] visited = new boolean[(int) Math.sqrt(sum) + 1 + 1];
        for (int i = 2; i < visited.length; i++) {
            if (!visited[i] && sum % i == 0) {
                visited[i] = true;
                factors.add((long) i);
                while (sum % i == 0) {
                    sum /= i;
                }
                for (int j = i + i; j < visited.length; j += i) {
                    visited[j] = true;
                }
            }
        }
        if (sum > 1) {
            // the last one
            factors.add(sum);
        }

        long min = (long) 1e18;
        for (long factor : factors) {
            min = Math.min(min, cost(arr, factor));
        }
        out.println(factors.size() >= 1 ? min : -1);
        out.close();
    }

    static long cost(long[] arr, long mod) {
        long delta = 0;
        // Delta is carried over from previous people.
        // The moment we can unload, we do so
        long cost = 0;
        for (long val : arr) {
            long realVal = val % mod;
            cost += Math.abs(delta);
            // Immediately unload or absorb whatever what was needed
            // previously
            if (delta > 0) {
                // We unload at most the amount we carry. However,
                // we are limited by the remaining space
                long unloaded = Math.min(delta, mod - realVal);
                delta -= unloaded;
                realVal += unloaded;
            }
            else if (delta < 0) {
                // The delta scoops up at most the needed amount.
                // It takes what it can from the available
                long absorbed = Math.min(-delta, realVal);
                delta += absorbed;
                realVal -= absorbed;
            }
            // Now that that's done, the realVal might have tipped
            // over the midpoint, so now, we need to create the delta
            // for this person's need now
            if (2 * realVal >= mod) {
                long space = mod - realVal;
                // If space > delta, then we now are deficit and
                // must retrieve some from elsewhere
                // Otherwise, then we will have spillover
                delta -= space;
            }
            else if (2 * realVal < mod) {
                delta += realVal; // need to carry this stuff out
            }
        }
        return cost;
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