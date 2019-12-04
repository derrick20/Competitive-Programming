/**
 * @author derrick20
 * solved 1:18
 */
import java.io.*;
import java.util.*;

public class PinCodes {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        while (T-->0) {
            int N = sc.nextInt();
            int[][] codes = new int[N][4];
            for (int i = 0; i < N; i++) {
                String s = sc.next();
                for (int j = 0; j < 4; j++) {
                    codes[i][j] = s.charAt(j) - '0';
                }
            }
            int res = 0;
            for (int i = 0; i < N; i++) {
                boolean ok = false;
                boolean diff = false;
                int pos = 0;
                int ct = 0;
                while (!ok) {
                    boolean match = false;
                    for (int j = 0; j < N; j++) {
                        if (j == i) continue;
                        if (Arrays.equals(codes[i],codes[j])) {
                            match = true;
                            diff = true;
                        }
                    }
                    if (!match) {
                        ok = true;
                    }
                    else {
                        codes[i][pos] = (codes[i][pos] + 1) % 10;
                        ct++;
                        if (ct == 10) {
                            pos++;
                            ct = 0;
                        }
                    }
                }
                if (diff) {
                    res++;
                }
            }
            out.println(res);
            for (int i = 0; i < N; i++) {
                StringBuilder s = new StringBuilder();
                for (int j = 0; j < 4; j++) {
                    s.append(codes[i][j]);
                }
                out.println(s);
            }
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