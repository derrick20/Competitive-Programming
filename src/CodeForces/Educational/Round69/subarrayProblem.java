/*
 * @author derrick20
 * Hmm tricky one
 * Key insights needed: Given the m constraint being less than 10,
 * we should immediately jump to the idea of grouping things based on that
 * Key observation: We'd naturally try to make sub problems based on the index of the final
 * part of a certain sub array. From that, we notice that the start point of the
 * subarray can be reused: if we found a good start point (the prefix sum to whose point
 * is low), then that start point is going to serve future sub arrays with farther back
 * end points well. (Unless we find a subarray sum who is so negative that it's better
 * than the current smallest prefix sum to a certain start point.
 */
import java.io.*;
import java.util.*;

public class subarrayProblem {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }
        long inf = (long) 1e18;

        // Stores the minimum prefix sum from the start to the position so far visited in the for loop
        // with a specific remainder mod M. The remainder information is necessary for us to know
        // whether the ceiling of the range length / M might need to by 1 more  (in the case of there
        // being slightly extra in the difference, with the end having greater remainder than the start)
        // If anything else, the ceiling will be the same.
        long[] minSum = new long[M];
        // Everything default to 0. We will only update if we can find negative values,
        // since anything positive is useless and would squander our precious positive numbers!
        Arrays.fill(minSum, inf);
        // However, we have to remember we could always just have 0 sum
        minSum[0] = 0;

        long bestSum = 0;
        long prefixSum = 0;
        // Rather than going back through some array, we can just track the result in place while
        // sweeping. At each end point, we are determining if the total sum MINUS the minSum to
        // any index before is any bigger than our old result.
        for (int i = 1; i <= N; i++) {

            prefixSum += arr[i - 1];

            long totalValue = prefixSum - K * (long) (Math.ceil((double) i / M));
//            System.out.println("---------------------------");
//            System.out.println("Position: " + i+ " Sum: " + prefixSum + " Total Value: " + totalValue);
//            System.out.println(Arrays.toString(minSum));
            // Update our minSum for the current mod, if possible
            // This will likely happen if lots of negative values
            if (totalValue < minSum[i % M]) {
                minSum[i % M] = totalValue;
            }

            long bestRemoval = 0;
            for (int remainder = 0; remainder < M; remainder++) {
                // We must try all possible SMALLEST subtractable values
                // We were forced to store a bit more information: the remainder mod M
                // because there is a certain threshold where the ceiling rounds up an extra bit
                long removedValue = minSum[remainder];
                // This means the ceiling function will pull up one extra K, which was not accounted
                // for in our subtracting of two prefix sums.

                // Rem < i % M (i CANNOT be 0)
                    // Rem = 0
                    // 0 1 2 3 4 0 1 2 3 4
                    // R           | | | | -> Gives 2 Correctly
                    // (separate blocks, but R isn't propelled up, so the issue is masked)

                    // Rem != 0
                    // 0 1 2 3 4 0 1 2 3 4
                    //   R           | | | -> 2, but ceiling subtraction gives 1. Need to add 1, since block overflows
                    //  (separate blocks, but R IS propelled up, so we undercounted)

                // Rem > i % M (Rem CANNOT be 0)
                    // i % M = 0
                    // 0 1 2 3 4 0 1 2 3 4
                    //   R R R R |         -> 1, but the ceiling of R leads to a shrunken block, so need to add 1
                    // i isn't propelled up, but R is, so we undercount

                    // i % M != 0
                    // 0 1 2 3 4 0 1 2 3 4
                    //       R     | |     -> Gives 1 correctly
                    // Both are propelled up, so it's fine

                if (remainder < (i % M) && (remainder % M != 0)) {
                    // If the blocks are too far, the ceiling pulls up and leads
                    // to an under valuing
                    removedValue += K;
                } else if (remainder > (i % M) && (i % M == 0)) {
                    // If the blocks are too close, the ceiling on the subtracted block
                    // leads to an undervalued amount between
                    removedValue += K;
                }
                bestRemoval = Math.min(bestRemoval, removedValue);
//                System.out.println("remainder: " + remainder + " removed: " + removedValue);
            }
//            System.out.println(totalValue + " " + bestRemoval);
            bestSum = Math.max(bestSum, totalValue - bestRemoval);
        }
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
