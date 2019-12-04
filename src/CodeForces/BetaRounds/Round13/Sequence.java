/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Sequence {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        ArrayList<Integer> arr = new ArrayList<>();
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            arr.add(sc.nextInt());
            set.add(arr.get(i));
        }
        ArrayList<Integer> sorted = new ArrayList<>(set);

        long[] dpCurr = new long[N + 1];
        long[] dpNext = new long[N + 1];
//        long[][] dp = new long[sorted.size() + 1][N + 1];
        long oo = (long) 9e18;
        // Base case: it's impossible to not use any of the sorted values
        Arrays.fill(dpCurr, oo);
        // dp[i][j] stores the minimum cost to make the prefix of first j vals all be
        // non-decreasing and with max value of sorted[i]
        for (int s = 1; s <= sorted.size(); s++) {
            // LOL they actually required memory efficiency !
            // Keep a dpCurr which has the current sortedIdx dp values.
            // Each time, we have a frontier that pushes forward, and we inch
            // outward. At the end, our dpCurr will be right at the cusp and the
            // dpNext will be nothing
            for (int i = 1; i <= N; i++) {
                long prev = Math.min(dpCurr[i - 1], dpNext[i - 1]);
                dpNext[i] = Math.min(dpCurr[i], prev + Math.abs(sorted.get(s - 1) - arr.get(i - 1)));
            }
//            System.out.println(sorted.get(s - 1) + " " + Arrays.toString(dpNext));
            dpCurr = dpNext;
            dpNext = new long[N + 1];
        }
        out.println(dpCurr[N]);
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