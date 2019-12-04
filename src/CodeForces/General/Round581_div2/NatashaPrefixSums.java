/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class NatashaPrefixSums {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int size = Math.max(N, M) + 1;
        long[][] dpZeroes = new long[size][size];
//         dpZeroes[i][j] stores how many ways we can have a maximum prefix
//         sum of 0 with i 1's and j -1's.
//
//         Base case is 0,0, which has 1 way
//         This transition direction relies on the fact that, if we've
//         found a prefix whose maximum sum is 0, then if we add a 1
//         or a -1, if the total count of n <= m, the max sum is still 0.
//         This is because there are 2 cases for what the prefix, onto which
//         we are appending something was:
//         It could be that some front prefix of the subproblem, that doesn't
//         reach the end, had the max sum of 0. In that case, the only way we
//         could affect the max sum is by then taking the whole augmented
//         array, which would have a sum of n - m <= 0. It'd still be 0.
//
//         In the other case, the subproblem we're appending to had a sum of
//         0 because it just summed the whole thing. In that case, adding
//         a -1 won't help certainly, but also adding 1 won't help since
//         the sum is still n - m <= 0. Basically, our claim is that, if the
//         previous MAXIMUM was 0, meaning it started with -1, then the general
//         pattern of the -1's and 1's makes it so that it is nonpositive sum
//         throughout. If n > m however, we can necessarily make a sum positive
//         thus, those are all not worth exploring when going up.
        long mod = 998244853;
        dpZeroes[0][0] = 1;
        for (int m = 1; m < size; m++) {
            for (int n = 0; n <= m; n++) {
                if (n > 0)
                    dpZeroes[n][m] += dpZeroes[n - 1][m];
                dpZeroes[n][m] += dpZeroes[n][m - 1];
                dpZeroes[n][m] %= mod;
            }

        }
//        for (int i = 0; i < size; i++) {
//            System.out.println(Arrays.toString(dpZeroes[i]));
//        }
        long ans = 0;
        // Try all n > m
        for (int n = 0; n <= N; n++) {
            for (int m = 0; m < n; m++) {
                // ways consists of finding a front with sum n - m,
                // then fill the rest with zero
                if (m > M) continue;
//                long ways = (dpZeroes[m][n] * dpZeroes[N - n][M - m - 1]) % mod;
                long ways = (dpZeroes[m][n - 1] * dpZeroes[N - n][M - m]) % mod;
                // Each has sum n - m, so we get more of those as the sum
                ans += ((n - m) * ways) % mod;
//                System.out.println(ways + " " + ways2);
//                System.out.println("n = " + n + " m = " + m + " Sum ways: " +  dpZeroes[m][n - 1]  + " Zero ways: " + dpZeroes[N - n][M - m]);
                ans %= mod;
            }
        }
        out.println(ans);
        // Now, the reason why we found all the ways to make prefix sums of 0
        // is that we can apply the idea of concatenating two problems together
        // All max sums consist of creating some max value to a certain point,
        // then the rest of the prefix sum array is only decreasing, like a hill
        // Thus, we see subproblems here, by making a sum of 1, then adding
        // the ways to make 1 TRULY the max possible. By placing a dpZero
        // solution behind it, it cannot possibly exceed 1. Now we move on
        // to the sum k = n - m, and then follow by placing the rest as 0

        // Ok this is genius. All things we computed earlier have a sum of 0.
        // But really, we can decompose that sum into a prefix of 0, then
        // some number of 1's and -1's, n and m, which have a negative sum
        // Suppose we take the reverse complement.
        // Now, there is a suffix of sum 0, which NECESSARILY
        // begins with a -1. The front now has a POSITIVE sum (n - m).
        // Proof: the prefix was made up of 1's, -1's.
        // Basically, each time we add a -1, we are able to add one more 1.
        // However, that 1 decreases our "buffer" of -1's. The endpoint of
        // this zero region must be a 1. Assume it isn't, and that it ends
        // with a -1. Our buffer MUST be at least 1, meaning we actually
        // can add another 1 to the end of this prefix. It couldn't have been
        // that adding a final -1 to something caused the buffer to reach 0,
        // since that implies there was a negative buffer before. Thus,
        // the buffer must be closed off to 0 by some number of 1's.
        // Since the prefix ends in a 1, the suffix begins with a -1.
        // Now, here's the kicker. If we plotted the running total of # of
        // -1's and # of 1's while traversing  the prefix from left to right,
        // the -1 line would strictly be greater than the 1 line.
        // Now, if we went backwards, and swapped -1's and 1's, then the graph
        // would still hold the invariant, the -1 line above the 1 line.
        // Let a(t) be -1 line originally, and b(t) be 1 line originally.
        // a2(t) becomes the 1 line after, and b2(t) becomes the -1 line
        // What it is after is a2(t) = X - b(T - t), b2(t) = X - a(T - t)
        // What this transformation does if make a2 be above b2 again,
        // if you draw it out it makes more sense. Thus, the invariant holds,
        // Thus what happened is our suffix consists of this zero group at
        // the tail, such that we have no reason to move into it as it'll
        // constantly only add 0 to us. Thus, there are k 1's and -1's there
        // There are n - k 1's and m - k -1's in the front of the sequence,
        // meaning the sum of the front is n - m still.
        // Thus, we have proved that dp[m][n], where n > m has a 1-to-1
        // correspondence with the number of ways to produce a sum of
        // n - m with n 1's and m -1's.

        out.close();
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        FastScanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}