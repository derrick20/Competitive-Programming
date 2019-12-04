/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Towers {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        Block[] blocks = new Block[N];
        int maxStrength = 0;
        for (int i = 0; i < N; i++) {
            int w = sc.nextInt();
            int s = sc.nextInt();
            int v = sc.nextInt();
            blocks[i] = new Block(w, s, v);
            maxStrength = Math.max(maxStrength, s);
        }
        Arrays.sort(blocks);

        long[] dpCurr = new long[maxStrength + 1];
        // Base case, we can always use the first block at least
        dpCurr[blocks[0].strength] = blocks[0].value;
        // dp[s] represents, using the first ith prefix (since we only need
        // a sliding window, size 2):
        // what is the maximum value possible, with s strength remaining
        for (int i = 1; i < N; i++) {
            long[] dpNext = new long[maxStrength + 1];
            // Either start from scratch here...
            dpNext[blocks[i].strength] = blocks[i].value;
            // ... or jump from some solution bag
            int w = blocks[i].weight;
            for (int s = 0; s <= maxStrength; s++) {
                // The entire subproblem can be condensed into a single block,
                // with the conglomerate strength s, now reduced by this block's
                // weight, w. The min strength is either this, or the next block's
                // strength

                // Either we use the block, reducing our strength, our just take the
                // answer from previous state, skipping this block
                int nextStrength = Math.min(s - w, blocks[i].strength);
                if (nextStrength >= 0) {
                    // If weight ever EXCEEDS strength, it collapses.
                    // Residual strength must be at least 0
                    dpNext[nextStrength] = Math.max(dpNext[nextStrength], dpCurr[s] + blocks[i].value);
                }
                dpNext[s] = Math.max(dpNext[s], dpCurr[s]);
            }
            dpCurr = dpNext;
        }
        long best = 0;
        for (int i = 0; i <= maxStrength; i++) {
            best = Math.max(best, dpCurr[i]);
        }
        out.println(best);
        out.close();
    }

    static class Block implements Comparable<Block> {
        long value;
        int weight, strength;
        public Block(int w, int s, long v) {
            weight = w; strength = s; value = v;
        }
        public int compareTo(Block b2) {
            // Decreasing order of strength
            // Essentially if we have two blocks, where one orientation
            // is allowed, but swapping isn't, then do that order:
            // s1 > w2
            // s2 < w1
            // s1 + w1 > s2 + w2 must hold!
            // if there's some weird relationships, it won't matter, because
            // the DP will still work correctly. We only need to sort it
            // such that we don't start with something that precludes future
            // states early on.
            return -((strength + weight) - (b2.strength + b2.weight));
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