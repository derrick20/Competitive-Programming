import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class MakeThemSimilar implements Runnable {
    public static void main (String[] args) {new Thread(null, new MakeThemSimilar(), "_cf", 1 << 28).start();}

    public void run() {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        System.err.println("");

        int n = fs.nextInt();
        int[] a = fs.nextIntArray(n);

        setHash(n);

        int[] left = new int[n];
        for(int fBit = 0; fBit <= 30; fBit++) {
            HashMap<Long, Integer> map = new HashMap<>();

            for(int mask = 0; mask < (1 << 15); mask++) {
                boolean noGood = false;
                int XOR = mask << 15;
                for(int i = 0; i < n; i++) {
                    int num = a[i] ^ XOR;
                    int bit = Integer.bitCount(num>>15);
                    if(bit > fBit) noGood = true;
                    left[i] = bit;
                }
                long hash;

                if(noGood) hash = Long.MAX_VALUE;
                else hash = getHash(left);

                if(!map.containsKey(hash)) map.put(hash, mask);

            }

            int AND = (1<<15)-1;
            for(int mask = 0; mask < (1 << 15); mask++) {
                boolean noGood = false;
                int XOR = mask;
                for(int i = 0; i < n; i++) {
                    int num = a[i] ^ XOR; // Reveal only the right half
                    int bit = Integer.bitCount(num & AND); //
                    if(bit > fBit) noGood = true;
                    left[i] = fBit-bit;
                }
                if(noGood) continue;

                long hash = getHash(left);
                if(!map.containsKey(hash)) continue;
                int val = map.get(hash);

                int res = (val << 15) | mask;
                System.out.println(res);

				for(int i = 0; i < n; i++) System.err.println(Integer.bitCount(a[i]^res));

                return;
            }

        }

        System.out.println(-1);

        out.close();
    }

    Random rnd = new Random();
    int BASE = 151;
    int MOD1 = BigInteger.valueOf(1000000000+rnd.nextInt(10000000)).nextProbablePrime().intValue();
    int MOD2 = BigInteger.valueOf(1000000000+rnd.nextInt(10000000)).nextProbablePrime().intValue();
    int[] pow1, pow2;

    long getHash(int[] a) {
        long h1 = 0, h2 = 0;
        for(int i = 0; i < a.length; i++) {
            h1 = (h1 + a[i] * pow1[i]);
            h2 = (h2 + a[i] * pow2[i]);
        }
        h1 &= (1L<<32)-1;
        h2 &= (1L<<32)-1;
        return h1 | (h2 << 32);
    }

    void setHash(int n) {
        n += 10;
        pow1 = new int[n];
        pow2 = new int[n];
        int p1 = 1, p2 = 1;
        pow1[0] = pow2[0] = 1;
        for(int i = 1; i < n; i++) {
            p1 = mult(p1, BASE, MOD1);
            p2 = mult(p2, BASE, MOD2);
            pow1[i] = p1;
            pow2[i] = p2;
        }
    }

    int mult(long a, long b, long M) {
        a *= b;
        a %= M;
        return (int)a;
    }

    class FastScanner {
        public int BS = 1<<16;
        public char NC = (char)0;
        byte[] buf = new byte[BS];
        int bId = 0, size = 0;
        char c = NC;
        double num = 1;
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

        public char nextChar(){
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
            num=1;
            boolean neg = false;
            if(c==NC)c=nextChar();
            for(;(c<'0' || c>'9'); c = nextChar()) {
                if(c=='-')neg=true;
            }
            long res = 0;
            for(; c>='0' && c <='9'; c=nextChar()) {
                res = (res<<3)+(res<<1)+c-'0';
                num*=10;
            }
            return neg?-res:res;
        }

        public double nextDouble() {
            double cur = nextLong();
            return c!='.' ? cur:cur+nextLong()/num;
        }

        public String next() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=nextChar();
            while(c>32) {
                res.append(c);
                c=nextChar();
            }
            return res.toString();
        }

        public String nextLine() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=nextChar();
            while(c!='\n') {
                res.append(c);
                c=nextChar();
            }
            return res.toString();
        }

        public boolean hasNext() {
            if(c>32)return true;
            while(true) {
                c=nextChar();
                if(c==NC)return false;
                else if(c>32)return true;
            }
        }

        public int[] nextIntArray(int n) {
            int[] res = new int[n];
            for(int i = 0; i < n; i++) res[i] = nextInt();
            return res;
        }

    }

}