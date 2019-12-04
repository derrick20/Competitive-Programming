/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class DigitSum {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        String K = sc.next();
        int D = sc.nextInt();
        long[][] dpPrev = new long[2][D];
        // Store, for the case of either we have are below (0) or at the value (1)
        // for this position and sum mod D, what is the number of ways to reach it
        // 5555
        // D = 6
        // store all remainders for the previous index
        // 5 4 3 2 1
        // 5 1 (5 -> 0)
        // 1 2 3 4 5
        // 4 (4 -> 0)
        // Base case
        int val = Character.getNumericValue(K.charAt(0));
        // CRUCIAL !!! Cannot START with ZERO, however, we must allow
        // for it initially, because we might be adding something else later
        // There is ONLY 1 way for it to ALL be 0's, so subtract 1 at the end
        for (int i = 0; i < val; i++) {
            dpPrev[0][i % D]++;
        }
        dpPrev[1][val % D]++;
//        for (int i = 0; i < 2; i++) {
//            System.out.println(Arrays.toString(dpPrev[i]));
//        }
        for (int pos = 1; pos < K.length(); pos++) {
            long[][] dpNext = new long[2][D];
            val = Character.getNumericValue(K.charAt(pos));
            for (int sum = 0; sum < D; sum++) {
                for (int digit = 0; digit < 10; digit++) {
                    // If we were below, then we can add any of 10 digits
                    int prevSum = (sum - digit) % D;
                    while (prevSum < 0) {
                        prevSum += D;
                    }
                    dpNext[0][sum] = addSelf(dpNext[0][sum], dpPrev[0][prevSum]);
                    // Or, if we were at it, then this will be the one to bring
                    // us below, so we use any digit less than the val
                    if (digit < val) {
                        dpNext[0][sum] = addSelf(dpNext[0][sum], dpPrev[1][prevSum]);
                    }

                    if (digit == val) {
                        // Or, we could lastly stay at that val
                        dpNext[1][sum] = addSelf(dpNext[1][sum], dpPrev[1][prevSum]);
                    }

//                    System.out.println("Sum: " + sum + " Digit: " + digit);
//                    for (int i = 0; i < 2; i++) {
//                        System.out.println(Arrays.toString(dpNext[i]));
//                    }
                }
            }
//
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < D; j++) {
                    dpPrev[i][j] = dpNext[i][j];
                }
//                System.out.println(Arrays.toString(dpPrev[i]));
            }
        }
        long ans = (dpPrev[0][0] + dpPrev[1][0] - 1 + mod) % mod;
        out.println(ans);
        out.close();
    }

    static long mod = (long) 1e9 + 7;

    static long addSelf(long val, long add) {
        val += add;
        if (val >= mod) {
            val -= mod;
        }
        return val;
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