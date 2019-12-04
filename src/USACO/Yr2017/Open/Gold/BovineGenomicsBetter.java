/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class BovineGenomicsBetter {
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

    static long mod = (long) 1e9 + 7; // rolling hash mod

    static long multiplySelf(long x, long y) {
        return (x * y) % mod;
    }

    static long addSelf(long x, long y) {
        x += y;
        if (x >= mod) {
            x -= mod;
        }
        return x;
    }

    static long subtractSelf(long x, long y) {
        x -= y;
        if (x < 0) {
            x += mod;
        }
        return x;
    }

    public static void main(String args[]) throws Exception {
//        setupIO();
        setupIO("cownomics", false);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] seq = new int[2 * N][M];
        for (int i = 0; i < 2 * N; i++) {
            String s = sc.next();
            for (int j = 0; j < M; j++) {
                char c = s.charAt(j);
                if (c == 'A') {
                    seq[i][j] = 0;
                }
                if (c == 'T') {
                    seq[i][j] = 1;
                }
                if (c == 'G') {
                    seq[i][j] = 2;
                }
                if (c == 'C') {
                    seq[i][j] = 3;
                }
            }
        }

        long[] power = new long[M + 1];
        power[0] = 1;
        for (int i = 1; i <= M; i++) {
            power[i] = (power[i - 1]  * 4) % mod;
        }

        int min = 1000; // Bigger than 500 maximum
        int l = 0;
        int r = 0;
        long[] hash = new long[2 * N];
/*
1 6
ATTAAT
TTATAA
 */
        outer: while (l < M) {
            search: while (true) {
                if (r == M) {
                    break outer;
                    // if this happens, we will never be able to get enough
                    // since from here we will only have decreasing access to the numbers
                }
                // Check at the beginning, because we might immediately be able to!
                HashSet<Long> seen = new HashSet<>();
                boolean poss = true;
                for (int cow = 0; cow < 2 * N; cow++) {
                    if (cow < N) {
                        seen.add(hash[cow]);
                    }
                    else {
                        if (seen.contains(hash[cow])) {
                            poss = false;
                        }
                    }
                }
                if (poss) {
                    // Found the right edge such that we are all unique
                    break search;
                }
                for (int cow = 0; cow < 2 * N; cow++) {
                    hash[cow] = multiplySelf(hash[cow], 4);
                    hash[cow] = addSelf(hash[cow], seq[cow][r]);
                    // create new hash to replace it
                }
                r++;
                // r moves so that it is 1 outside the end of the range, so r - l is the
                // exact length of the ID sequence.
            }
//            System.out.println(l + " " + r);
//            System.out.println(Arrays.toString(hash));

            min = Math.min(min, r - l);
            for (int cow = 0; cow < 2 * N; cow++) {
                // However, since the length is r - l exactly, the power of
                // the highest degree is r - l - 1!!!
                long subtract = multiplySelf(seq[cow][l], power[r - l - 1]);
                hash[cow] = subtractSelf(hash[cow], subtract);
            }
            l++;
        }
        out.println(min);
        out.close();
    }

    static void updateMap(HashMap<Long, Integer> freq, long val) {
        if (freq.containsKey(val)) {
            freq.put(val, freq.get(val) + 1);
        }
        else {
            freq.put(val, 1);
        }
    }

    static void decreaseKey(HashMap<Long, Integer> freq, long val) {
        freq.put(val, freq.get(val) - 1); // decrease frequency
        if (freq.get(val) == 0) {
            freq.remove(val);
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