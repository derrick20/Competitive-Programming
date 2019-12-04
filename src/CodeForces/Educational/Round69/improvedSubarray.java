/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class improvedSubarray {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        long[] arr = new long[N];
        long[] prefix = new long[N + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
            prefix[i + 1] += prefix[i] + arr[i];
        }

        long[] dp = new long[N + 1];
        // Key idea: make arrays parallely defined to make indexing easier
        // Here, prefixes are representing TAKING the first i items. Do the same for
        // DP, taking up to the first i items!
        long bestSum = 0;
        for (int poss = 1; poss <= N; poss++) {
            // We try taking all small pieces of size from 1 to M - 1, since these all
            // require a cost of K still. These will serve as a similar style of block
            // decomposition as sqrt decomp, which enable us to fill the gaps for future
            // large blocks
            for (int taken = 1; taken < M && poss >= taken; taken++) {
                // All of the small pieces ending at this point will incur a cost of K. However,
                // what we store is the best out of all these options!
                dp[poss] = Math.max(dp[poss], prefix[poss] - prefix[poss - taken] - K);
            }
            // Here is the crucial part where we are building on top of previous pieces,
            // and we are taking a big piece of size M. It's possible that the best option with
            // this given ending point is to simply build a small piece of size less than M,
            // but the important thing is that this extends recursively, so blocks of size M could
            // repeatedly be attached until the remainder is added on at the end (for the optimal subarray)
            if (poss > M) {
                // This represents using a subproblem from M pieces earlier, then adding the M pieces at the end,
                // which then forces on a cost of K.
                dp[poss] = Math.max(dp[poss], dp[poss - M] + prefix[poss] - prefix[poss - M] - K);
            }
            bestSum = Math.max(bestSum, dp[poss]);
        }
//        for (int end = 0; end < N; end++) {
//            for (int back = 0; back < M && end - back >= 0; back++) {
//                dp[end] = Math.max(dp[end], prefix[end + 1] - prefix[end - back] - K);
//            }
//            if (end >= M) {
//                dp[end] = Math.max(dp[end], dp[end - M] + prefix[end + 1] - prefix[end - M + 1] - K);
//            }
//            bestSum = Math.max(bestSum, dp[end]);
//        }
        out.println(bestSum);
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
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException {
            return br.readLine();
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
