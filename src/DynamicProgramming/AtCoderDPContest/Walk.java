/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Walk {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        long K = sc.nextLong();
        long[][] adjMat = new long[N][N];
        for (int i = 0; i < adjMat.length; i++) {
            for (int j = 0; j < adjMat.length; j++) {
                adjMat[i][j] = sc.nextInt();
            }
        }

        long[][] res = fastMatrixExponentiation(adjMat, K);
        long sum = 0;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                sum = addSelf(sum, res[row][col]);
            }
        }
        out.println(sum);
        out.close();
    }

    static int N;
    static long mod = (long) 1e9 + 7;
    // Must be square, same dimensions!
    static long[][] multiplyMatrix(long[][] a, long[][] b) {
        long[][] res = new long[N][N];
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                long strip = 0;
                for (int pos = 0; pos < N; pos++) {
                    strip = addSelf(strip, (a[row][pos] * b[pos][col]) % mod);
                }
                res[row][col] = strip;
            }
        }
        return res;
    }

    static long[][] identity() {
        long[][] res = new long[N][N];
        for (int i = 0; i < N; i++) {
            res[i][i] = 1L;
        }
        return res;
    }

    static long[][] fastMatrixExponentiation(long[][] matrix, long power) {
        if (power == 0) {
            return identity();
        }
        else if (power == 1) {
            return matrix;
        }
        else {
            long[][] sqrt = fastMatrixExponentiation(matrix, power / 2);
            long[][] res = multiplyMatrix(sqrt, sqrt);
            if (power % 2 == 1) {
                return multiplyMatrix(res, matrix);
            }
            else {
                return res;
            }
        }
    }

    static long addSelf(long val, long delta) {
        val += delta;
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