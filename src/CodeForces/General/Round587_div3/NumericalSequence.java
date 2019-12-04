/**
 * @author derrick20
 * Ok, now this was a really tough one. Dealing with digits just isn't
 * fun in general... :(((
 * Had to do some sneaky tricks with the binary search, setting
 * the bound to be 2 *sqrt(N), just cause somehow it's a bit above sqrt(N)
 * THREE LAYERS DEEP of precision
 * I did two binary searches, one to find the N such that ending the sequence
 * through that N would lead to a sum less than the desired
 * Next, within the N+1th sequence would have to be the digit of interest
 * (essentially the remainder modulo length N-ish)
 *
 * So, we binary search again, this time using digit length formula, which is
 * different from sequence total length (which is using n*(n+1)/2 formula)
 * Here, we find the exact number out of the N+1th sequence it will be in
 *
 * One last level of fine-graining, we figure out where within that signle number
 * the digit will be so that finally each level of precision will get us to add
 * all the way up to K.
 */
import java.io.*;
import java.util.*;

public class NumericalSequence {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        //11212312341234512345612345671234567812345678912345678910
        //11212312341234512345612345671234567812345678912345678910
        int Q = sc.nextInt();
//        int i = 1;
        while (Q-->0) {
            long K = sc.nextLong();
            long N = binarySearchNumber(K);
            long delta = K - digits(N);
//            out.println(K);
//            out.println(N);
//            out.println(delta);
            if (delta == 0) {
                String s = N + "";
                out.println(s.charAt(s.length() - 1));
            }
            else {
                long num = binarySearchSmall(delta, N);
                long delta2 = delta - seqLength(num);
                // we are right before the number, just how far inside
                // the length of the number is needed!
                char dig;
                if (delta2 > 0) {
                    num++; // overflow into next number
                    String s = (num) + "";
                    dig = s.charAt((int) delta2 - 1);
                }
                else {
                    String s = (num) + "";
                    dig = s.charAt(s.length() - 1 - (int) delta2);
                }

                out.println(dig);
            }
        }
        out.close();
    }

    static long digits(long n) {
        long ans = n * (n + 1) / 2;
        long power = 10;
        while (power <= n) {
            ans += (n - power + 1) * (n - power + 2) / 2;
            power *= 10;
        }
        return ans;
    }

    // Find latest N, such that the digits function is less
    // than pos
    // 1111110000 -> 10
    static long binarySearchNumber(long pos) {
        long lo = 1;
        long hi = (long) Math.sqrt(pos) * 2;
        while (lo < hi) {
            long mid = (hi - lo) / 2 + lo + 1;
            if (digits(mid) <= pos) {
                lo = mid;
            }
            else {
                hi = mid - 1; // creep downward
            }
        }
        return lo;
    }

    static long seqLength(long n) {
        long ans = n;
        long power = 10;
        while (power <= n) {
            ans += (n - power + 1);
            power *= 10;
        }
        return ans;
    }

    // A certain delta above "last"
    // We have an upper bound of last + 1, but we climb up
    // until the number of digits works to equal delta!
    // Find the last number, such that counting all digits up
    // to and including is <= delta
    // 1111000
    static long binarySearchSmall(long delta, long last) {
        long lo = 1;
        long hi = last + 1;
        while (lo < hi) {
            long mid = (hi - lo) / 2 + lo + 1;
            if (seqLength(mid) <= delta) {
                lo = mid;
            }
            else {
                hi = mid - 1; // doesn't matter, since only 1 will work
            }
        }
        return lo;
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