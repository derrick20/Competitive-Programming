/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class AngryCowsSilver {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean testing) throws Exception {
        String prefix = testing ? "/Users/derrick/IntelliJProjects/src/USACO/" : "";
        sc = new FastScanner(prefix + problem_name + ".in");
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner();
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
//        setupIO();
      setupIO("angry", false);

        N = sc.nextInt();
        K = sc.nextInt();
        bales = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            bales.add(sc.nextInt());
        }
        out.println(binarySearch());
        out.close();
    }

    static TreeSet<Integer> bales;
    static int N, K;

    static boolean doable(int R) {
        int curr = bales.first();
        int end = bales.last();
        for (int group = 0; group < K; group++) {
            // Floor returns the biggest thing up to and including this,
            // whereas lower is biggest up to but excluding this
            if (curr + 2 * R >= end) {
                return true;
            }
            else {
                curr = bales.higher(curr + 2 * R);
            }
        }
        return false;
    }

    // Find the least radius that works
    // 00111
    // hook onto the hi, then let lo creep up
    static int binarySearch() {
       int lo = 0;
       int hi = (int) 1e9;
       while (lo < hi) {
           int mid = (lo + hi) / 2;
           if (doable(mid)) {
               hi = mid;
           }
           else {
               lo = mid + 1;
           }
       }
       return hi;
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