/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class PermutationConcatenation {
    static long mod = 998244353;

    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        fact = new long[N + 1];
        invFact = new long[N + 1];
        fact[0] = 1;
        invFact[0] = 1;
        for (int i = 1; i <= N; i++) {
            fact[i] = (fact[i - 1] * (long) i % mod);
            invFact[i] = modInverse(fact[i]);
        }
        long ans = fact[N];
        // Non-spanning first, now we do spanning perms
        for (int left = N - 1; left >= 1; left--) {
            long delta = (choose(N, left) * (fact[left] - 1)) % mod;
            delta = (delta * fact[N - left]) % mod; // arrange the rest of the left side,
            // not the part being spanned
            ans = (ans + delta) % mod;
        }
        out.println(ans);
        out.close();
    }

    static long[] fact, invFact;

    static long choose(int N, int K) {
        long ans = (fact[N] * invFact[K]) % mod;
        return (ans * invFact[N - K]) % mod;
    }

    static long modInverse(long x) {
        return fastExpo(x, mod - 2);
    }

    static long fastExpo(long x, long k) {
        if (k == 0) {
            return 1;
        }
        else if (k == 1) {
            return x;
        }
        else {
            long root = fastExpo(x, k / 2);
            long ans = (root * root) % mod;
            if (k % 2 == 1) {
                return (ans * x) % mod;
            }
            else {
                return ans;
            }
        }
    }

    static class Pair implements Comparable<Pair> {
        int x, y;
        public Pair(int xx, int yy) {
            x = xx; y = yy;
        }

        public int compareTo(Pair p2) {
            return x - p2.x == 0 ? y - p2.y : x - p2.x;
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