/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class UniquenessRedo2 {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }

        int l = 0;
        int r = 0;
        int best = N;
        while (l < N) {
            // Invariant: [l, r], once deleted, will lead to a unique
            // set of numbers
            r = Math.max(r, l);
            while (r < N && !doable(l, r)) {
                r++;
            }
            // KEY ISSUE: 2-Pointers relies on the hidden assumption
            // that it will be valid once we've moved to this point,
            // kinda like in string hashing, just do ONE MORE CHECK to
            // ensure that you are actually real
            if (doable(l, r)) {
                best = Math.min(best, r - l + 1);
            }
            l++;
        }
        if (doable(N, N)) {
            best = 0;
        }
        out.println(best);
        out.close();
    }

    static int N;
    static int[] arr;

    static boolean doable(int l, int r) {
        HashSet<Integer> used = new HashSet<>();
        for (int i = 0; i < l; i++) {
            if (used.contains(arr[i])) {
                return false;
            }
            used.add(arr[i]);
        }
        for (int i = r + 1; i < N; i++) {
            if (used.contains(arr[i])) {
                return false;
            }
            used.add(arr[i]);
        }
        return true;
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