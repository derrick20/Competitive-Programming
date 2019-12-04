/**
 * @author derrick20
 */
import java.io.*;
import java.math.BigInteger;
import java.util.*;

// REPLACE NAME WITH "Solution"
public class Cryptopangrams {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        int c = 1;
        while (T-->0) {
            String s = sc.next();
            int L = sc.nextInt();
            BigInteger[] products = new BigInteger[L];
            for (int i = 0; i < L; i++) {
                products[i] = new BigInteger(sc.next());
            }
            BigInteger[] original = new BigInteger[L + 1];
            // products[0] = original[0] * original[1]
            // products[1] = original[1] * original[2]
            // original[1] = gcd(products[0], products[1]

            for (int i = 1; i < original.length - 1; i++) {
                original[i] = products[i - 1].gcd(products[i]);
            }
/*
1
20 8
15 15 15 15 9 15 15 15

3 5 3 5 3 3 5 3 5
 */
            // Go through forward, and sweep backwards to clean up any 1's in the array
            for (int i = 1; i < original.length - 1; i++) {
                if (products[i].compareTo(products[i - 1]) != 0) {
                    // This means anything before us that is 1's can now be
                    // REVEALED by us.
//                    System.out.println(products[i].gcd(products[i - 1]));
                    int pos = i - 1;
                    while (pos >= 1 && products[pos].compareTo(products[pos - 1]) == 0) {
                        original[pos] = products[pos].divide(original[pos + 1]);
                        pos--;
                    }
                    original[pos] = products[pos].divide(original[pos + 1]);
                }
            }

            for (int i = L - 1; i >= 1; i--) {
                if (products[i].compareTo(products[i - 1]) != 0) {
                    // This means anything before us that is 1's can now be
                    // REVEALED by us.
                    int pos = i + 1;
                    while (pos <= L - 1 && products[pos].compareTo(products[pos - 1]) == 0) {
                        original[pos] = products[pos - 1].divide(original[pos - 1]);
                        pos++;
                    }
                    original[pos] = products[pos - 1].divide(original[pos - 1]);
                }
            }
//            original[0] = products[0].divide(original[1]);
//            original[L] = products[L - 1].divide(original[L - 1]);

            TreeSet<BigInteger> set = new TreeSet<>();
            for (BigInteger val : original) {
                set.add(val);
            }

            HashMap<BigInteger, Character> map = new HashMap<>();
            for (BigInteger val : set) {
                map.put(val, (char) ('A' + map.size()));
            }
            StringBuilder sb = new StringBuilder();
            for (BigInteger key : original) {
                sb.append(map.get(key));
            }
            out.printf("Case #%d: %s\n", c++, sb);
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