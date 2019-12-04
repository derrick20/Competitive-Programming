/**
 * @author derrick20
 * Key Concept: Don't always think in the bottom-up manner.
 * The main idea is think about what isn't important.
 * Try base cases: single thing has 0 cost. Two have cost a + b.
 * If we can break it into pieces of different sizes, then we'll have the
 * answer. By combining! Divide and conquer at all possible break points!
 * It does help to recall that a tree's vertices can represent all of the
 * children. This intuition comes in handy through and through with Euler
 * Tours and such !
 */
import java.io.*;
import java.util.*;

public class Slimes {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        slime = new long[N];
        // prefixSum[i] stores sum of the first i slimes
        prefixSum = new long[N + 1];
        for (int i = 0; i < slime.length; i++) {
            slime[i] = sc.nextLong();
            prefixSum[i + 1] += prefixSum[i] + slime[i];
        }

        // Iterative way also makes sense, since it's visiting all states
        // The intuition is we go from most restricted (the fundamental
        // base cases), then rely on old answers as we widen our range
        // So, left begins very far right, which limits the range.
        // Also, right begins close to the current left bound, then gradually
        // opens up.
        // So, at each center point, we build up the sums of answers from left
        // to the split sequentially.
        long[][] dp = new long[N][N];
        for (int L = N - 1; L >= 0; L--) {
            for (int R = L; R < N; R++) {
                if (L == R) {
                    dp[L][R] = 0;
                }
                else {
                    // Make it so we go up to including split, then break the rest off
                    dp[L][R] = oo;
                    for (int split = L; split < R; split++) {
                        long mergeCost = prefixSum[R + 1] - prefixSum[L];
                        // the whole range will be added
                        dp[L][R] = Math.min(dp[L][R], mergeCost + dp[L][split] + dp[split + 1][R]);
                    }
                }
            }
        }
//        memo = new long[N][N];
//        out.println(solve(0, N - 1));
        out.println(dp[0][N - 1]);
        out.close();
    }

    static int N;
    static long[] slime, prefixSum;
    static long[][] memo;
    static long oo = (long) 1e18;

    // Find the optimal merging scheme for interval [left, right] on the array
    // O(N) split points. O(1) merge time
    // O(N^2) states. Let's memoize!
    static long solve(int left, int right) {
        if (left == right) {
            // We've reached no merge, so no cost!
            return 0;
        }
        else if (memo[left][right] != 0) {
            return memo[left][right];
        }
        else {
            // Make it so we go up to including split, then break the rest off
            long ans = oo;
            for (int split = left; split < right; split++) {
                long mergeCost = prefixSum[right + 1] - prefixSum[left];
                // the whole range will be added
                ans = Math.min(ans, mergeCost + solve(left, split) + solve(split + 1, right));
            }
            return memo[left][right] = ans;
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