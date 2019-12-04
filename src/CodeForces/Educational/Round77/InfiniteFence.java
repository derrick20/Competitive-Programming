/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class InfiniteFence {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        while (T-->0) {
            long r = sc.nextLong();
            long b = sc.nextLong();
            if (r < b) {
                long temp = r;
                r = b;
                b = temp;
            }
            long g = gcd(r, b);
            r /= g;
            b /= g;
            // r is bigger
            long k = sc.nextLong();
//            ArrayList<Long> test = new ArrayList<>();
//            int ct = 0;
//            for (int i = 0; i <= r * b; i++) {
//                if (i % r == 0) {
//                    test.add(r);
//                    ct = 0;
////                    System.out.println(ct);
//
//                }
//                else if (i % b == 0) {
//                    test.add(b);
//                    ct++;
//                    if (ct == k) {
//                        System.out.println("BAAD");
//                    }
////                    System.out.println(ct);
//
//                }
//            }


            boolean poss = true;
            //exclude the overlap at end as a ball
            long space = (long) Math.ceil(1.0 * (r - 1) / b);

            if (space >= k) {
                poss = false;
            }
            System.out.println(poss ? "OBEY" : "REBEL");
        }

        out.close();
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
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