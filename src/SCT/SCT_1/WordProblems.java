/**
 * @author derrick20
 * The key idea of course is to recursively break it down by powers of
 * 2 each round.
 *
 * The idea is that there are two components within a string. It is
 * made up of the subproblem half the size, one power of 2 less.
 * f(k) = f(k-1) + f'(k-1) + f(k-1)[0]
 * If N * 2^(k-1) <= x < N * 2^k
 * this means the position is somewhere along a block,
 * and we can subtract the half block (k-1) which will take us to a
 * similar locatin, however, since everything is left shifted
 * as you go higher, we must RIGHT SHIFT when we recurse.
 * Then, we also might be too big, because that +1 might take us
 * just to N * 2^(k-1), when we should really be in the range:
 * [ N * 2^(k-2), N * 2^(k-1) )
 * So, we need to also mod by N * 2^(k-1), to ensure that we don't go too far.
 *
 * That mod will then send us to 0, which, makes sense, because the tail end
 * of any subpart will always be the first/0th index in the original string,
 * since our pattern is left shift, then move the front to the back.
 *
 * What this means is, starting at a given position, we will escalate back eventually
 * reaching the tail end (N * 2^i - 1), which is right at the end, and +1
 * will take us to 0th position, so we can jump far.
 * Example: N = 3, x = 23.
 * Subtracting 3 * 2^2 = 12 gets to 11. +1 gets 12, then mod (3 * 2^2) = 0,
 * so we skipped a few steps. If we start in the middle of a subproblem,
 * each round, we get closer to the end of the subproblem (the distance is halved
 * and also we step up by 1)
 */
import java.io.*;
import java.util.*;

public class WordProblems {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        arr = sc.next().toCharArray();
        N = arr.length;
        long x = sc.nextLong() - 1;
        for (int i = 0; i <= x; i++) {
            out.print(solve(i) + " ");
        }
        out.println(solve(x));
        out.close();
    }

    static char[] arr;
    static long N;

    static char solve(long x) {
        long i = 0;
        // N*2^(i-1) <= x < N*2^i
        while ((N << i) <= x) {
            i++;
        }
        if (i == 0) {
            return arr[(int) x];
        }
        else {
            // Subtract out the amount beneath us
            long next = x - (N << (i - 1));
            next--;
            next += (N << (i - 1));
            next %= (N << (i - 1));
            return solve(next);
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