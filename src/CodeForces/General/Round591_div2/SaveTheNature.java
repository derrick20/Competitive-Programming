/**
 * @author derrick20
 * SHAFTTT YOU'RE CONCEPT OF MODULAR ARITHMETIC IS STILL FAULTY
 * When you are deciding to pick the combined parts, you must
 * do it by seeing if it divides a and divides b, not necessarily
 * the same as a*b. Really, you should be doing lcm, but this does
 * it implicitly! Then, later you ensure that you only do things
 * that weren't just accounted for by this double factor idea,
 * which will solve it!
 */
import java.io.*;
import java.util.*;

public class SaveTheNature {

    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int Q = sc.nextInt();
        while (Q-->0) {
            int N = sc.nextInt();
            price = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                price.add(sc.nextLong());
            }
            Collections.sort(price, Collections.reverseOrder());
            p1 = sc.nextLong();
            a = sc.nextInt();
            p2 = sc.nextLong();
            b = sc.nextInt();
            K = sc.nextLong();
            if (p1 < p2) {
                long temp = p1;
                p1 = p2;
                p2 = temp;
                int temp2 = b;
                b = a;
                a = temp2;
            }
//            // Now, it's a, p1 is more valuable than b, p2
//            long[] prefixAB = new long[N + 1];
//            long[] prefixA = new long[N + 1];
//            long[] prefixB = new long[N + 1];
//            int pos = 0;
//            boolean skipAB = (p1 + p2) * a > p1 * a * b;
//            if ((p1 + p2) * a > p1 * a * b) {
//                // prioritize grouped together
//                for (int i = 1; i <= N; i++) {
//                    prefixAB[i] = prefixAB[i - 1];
//                    if (i % (a * b) == 0) {
//                        prefixAB[i] += (p1 + p2) * price.get(pos) / 100;
//                        pos++;
//                    }
//                }
//            }
//            for (int i = 1; i <= N; i++) {
//                prefixA[i] = prefixA[i - 1];
//                if (i % (a * b) == 0 && skipAB) {
//                    continue;
//                }
//                if (i % a == 0) {
//                    // Every ath will get a
//                    prefixA[i] += p1 * price.get(pos) / 100;
//                    if (i % (a * b) == 0) {
//                        prefixA[i] += p2 * price.get(pos) / 100;
//                    }
//                    pos++;
//                }
//            }
//            for (int i = 1; i <= N; i++) {
//                prefixB[i] = prefixB[i - 1];
//                if (i % (a * b) == 0) {
//                    continue;
//                }
//                if (i % b == 0) {
//                    prefixB[i] += p2 * price.get(pos) / 100;
//                    pos++;
//                }
//            }
//            long[] prefix = new long[N + 1];
//            for (int i = 1; i <= N; i++) {
//                prefix[i] = prefixA[i] + prefixB[i] + prefixAB[i];
//            }

            int lo = 1;
            int hi = N;
            // Find the earliest 1 to satisfy
            // 0001111
            // latch on to the hi, let lo scrape up
            boolean poss = false;
            while (lo < hi) {
                int mid = (hi - lo) / 2 + lo;
                if (value(mid) >= K) {
                    poss = true;
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            if (value(hi) >= K) {
                poss = true;
            }
            out.println(poss ? hi : -1);
        }
        out.close();
    }

    static long p1, p2, K;
    static int a, b;
    static ArrayList<Long> price;

    static long value(int ct) {
        int pos = 0;
        long amt = 0;
        // prioritize grouped together
        for (int i = 1; i <= ct; i++) {
            if (i % a == 0 && i % b == 0) {
                amt += (p1 + p2) * price.get(pos);
                pos++;
            }
        }
        for (int i = 1; i <= ct; i++) {
            if (i % b != 0 && i % a == 0) {
                amt += (p1) * price.get(pos);
                pos++;
            }
        }
        for (int i = 1; i <= ct; i++) {
            if (i % a != 0 && i % b == 0) {
                amt += (p2) * price.get(pos);
                pos++;
            }
        }
        return amt / 100;
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