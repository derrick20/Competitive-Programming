/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class BalancedPlaylist {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] arr = new int[3*N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
            arr[i + N] = arr[i];
            arr[i + 2*N] = arr[i];
        }
        int[] ans = new int[N];

        // Two-pointers, but we need a structure to maintain the current max
        // This needs the following properties:
        // Add new values and update max
        // Remove a value. This is a treemap
        // Don't overcomplicate it, use the invariant:
        // if you've solved something so far, then try moving up, it
        // will only be at LEAST as hard to find another point
        TreeMap<Integer, Integer> freq = new TreeMap<>();
        int r = 0;
        freq.merge(arr[r], 1, Integer::sum);
        for (int l = 0; l < N; l++) {
            // update as 1, or add 1 to it
            // 2 -> 1. 3 -> 2
            while (r < 3 * N - 1 && (freq.size() == 0 || 2 * arr[r + 1] >= freq.lastKey())) {
                freq.merge(arr[r + 1], 1, Integer::sum);
                r++;
            }
            if (r == 3 * N - 1) {
                ans[l] = -1;
                continue;
            }

            freq.merge(arr[l], -1, Integer::sum);
            if (freq.get(arr[l]) == 0) {
                freq.remove(arr[l]);
            }
            ans[l] = r - l + 1;
        }

        for (int a : ans) {
            out.print(a + " ");
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