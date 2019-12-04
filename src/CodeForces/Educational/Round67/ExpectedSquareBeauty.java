/**
 * @author derrick20
 * Soooo hard but finally figured it out.
 */
import java.io.*;
import java.util.*;
/*
Equal: 500000005
P1^2 - P2: 125000002
Indep: 0
Dep: 1
 */

public class ExpectedSquareBeauty {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        L = new long[N];
        R = new long[N];
        range = new long[N];
        for (int i = 0; i < N; i++) {
            L[i] = sc.nextLong();
        }
        for (int i = 0; i < N; i++) {
            R[i] = sc.nextLong();
        }
        for (int i = 0; i < N; i++) {
            range[i] = R[i] - L[i] + 1;
        }
        borderProb = new long[N];
        long P2 = 0; // Sum of second power of each term
        long equalComponent = 0;
        for (int i = 1; i < N; i++) {
            borderProb[i] = probDiff(i);
            P2 = addSelf(P2, multiply(borderProb[i], borderProb[i]));
//            System.out.println(borderProb[i]);
            equalComponent = addSelf(equalComponent, borderProb[i]);
        }
//        System.out.println("Equal: " + equalComponent);

        long P1 = equalComponent; // First power of each term

        // I don't know why this line is necessary
        // todo COME BACK TO THIS!!!!
        equalComponent = (3 * equalComponent + 1) % mod;

        long independentComponent = subSelf(multiply(P1, P1), P2);
//        System.out.println("P1^2 - P2: " + independentComponent);
        for (int i = 1; i < N; i++) {
            if (i < N - 1) {
                independentComponent = subSelf(independentComponent, multiply(2, multiply(borderProb[i], borderProb[i + 1])));
//                System.out.println("Indep: " + independentComponent);
            }
        }

        long dependentComponent = 0;
        for (int i = 1; i <= N - 2; i++) {
            dependentComponent = addSelf(dependentComponent, multiply(2, probTripleDiff(i)));
        }
//        System.out.println("Dep: " + dependentComponent);

        long ans = equalComponent;
        ans = addSelf(ans, independentComponent);
        ans = addSelf(ans, dependentComponent);
        out.println(ans);
        out.close();
    }

    static long mod = (long) 1e9 + 7;
    static long[] L, R;
    static long[] range;
    static long[] borderProb;

    // Find probability of this part having a contribution, meaning
    // x_i-1 != x_i.
    static long probDiff(int i) {
        // If the common range is screwed up,
        // like L  R   L2 R2, then they CAN'T OVERLAP, so 0!
        long commonRange = Math.max(0, Math.min(R[i], R[i - 1]) - Math.max(L[i], L[i - 1]) + 1);
        long probSame = multiply(commonRange, modInverse(multiply(range[i], range[i - 1])));
        long probDiff = (1 - probSame + mod) % mod;
        return probDiff;
    }

    // Let's say the parameter will be the middle i value.
    // Find probability of all three of these being different. The reason
    // we do this is because the contribution of E[i]*E[j] will only yield
    // anything if x_i-1 != x_i AND x_j-1 != x_j
    // Normally, those will be independent events, so they may be multiplied,
    // but in thise case, if j = i + 1, we must remove overcounting
    // Specifically, we are asking P(x_i-1 != x_i != x_i+1)
    // This is saying 1 - P(x_i-1 != x_i OR x_i != x_i+1)
    // Then, we can use inclusion-exclusion to get that OR venn-diagram
    // statement part. Basically, this simplifies to become:
    // Let p_i = P(x_i != x_i-1). (We will store this in borderProb[])
    // p_i + p_i+1 - (1 - P(the three area all equal))
    static long probTripleDiff(int i) {
        long res = addSelf(borderProb[i], borderProb[i + 1]);
        long commonRange = Math.max(0, Math.min(R[i + 1], Math.min(R[i], R[i - 1])) - Math.max(L[i + 1], Math.max(L[i], L[i - 1])) + 1);
        long denominator = modInverse(multiply(range[i - 1], multiply(range[i], range[i + 1])));
        long probAllEqual = multiply(commonRange, denominator);
        res = subSelf(res, 1 - probAllEqual);
        return res;
    }

    static long multiply(long a, long b) {
        return (a * b) % mod;
    }

    static long subSelf(long val, long sub) {
        val -= sub;
        if (val < 0) {
            val += mod;
        }
        return val;
    }

    static long addSelf(long val, long add) {
        val += add;
        if (val >= mod) {
            val -= mod;
        }
        return val;
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
                ans = (ans * x) % mod;
            }
            return ans;
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