/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class TilePainting {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        long N = sc.nextLong();
        boolean[] sieve = new boolean[(int) Math.sqrt(N) + 1 + 1];
        ArrayList<Long> factors = new ArrayList<>();
        factors.add(1L);
        for (int i = 2; i < sieve.length; i++) {
            if (!sieve[i] && N % i == 0) {
                sieve[i] = true; // mark it as true if it hasn't been
                factors.add((long) i);
                while (N % i == 0) {
                    N /= i;
                }
                for (int j = 2 * i; j < sieve.length; j += i) {
                    sieve[j] = true;
                }
            }
        }
        if (N != 1) {
            factors.add(N);
        }
        // The issue is that we've removed all factors that
        // Could be raised to a power greater than 2, so anything
        // that remains must be a singular prime.
//        System.out.println(factors);
        long ans = 0;
        if (factors.size() >= 3) {
            ans = 1; // All will be linked, it has one and more than 2 factors
        }
        else if (factors.size() == 2) {
            ans = factors.get(1);
        }
        else {
            ans = N;
        }
        out.println(ans);
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