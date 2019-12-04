/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Sushi {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        // Trust me, you'll thank me later...
        double N = sc.nextDouble();
        int ones = 0;
        int twos = 0;
        int threes = 0;
        for (int i = 0; i < N; i++) {
            switch (sc.nextInt()) {
                case 1:
                    ones++;
                    break;
                case 2:
                    twos++;
                    break;
                case 3:
                    threes++;
                    break;
            }
        }
        double[][][] dp = new double[(int) N + 1][(int) N + 1][(int) N + 1];
        // At most N = 300, so 100^3 = 10^6 run time.
        // dp[i][j][k] = expected number of moves until reaching 0, 0, 0 left

        for (int k = 0; k <= N; k++) {
            for (int j = 0; j <= N; j++) {
                for (int i = 0; i <= N; i++) {
                    // Our recurrence is dp[i][j][k] = 1 + p(take from 1 pile) * 1 piles + ...
                    if (i == 0 && j == 0 && k == 0 || (i + j + k) > N) continue;

                    // Two approaches here:
                    // Here's the fun way:
                    // Geometric series of repeatedly taking from a zero pile, then finally taking a nonzero pile
                    // q = 1 - p
                    // p * [q + 2q^2 + 3q^3 + ...]
                    // = (1 - p) / p
                    // Prove this! But then, after we have played for an expected amount of time,
                    // then finally successfully picked something nonzero, we still need to pick
                    // WHICH nonzero plate is valid
                    double conditionalSpace = i + j + k;
                    double pSuccess = conditionalSpace / N;
                    double expectedSteps = (1 - pSuccess) / pSuccess;
                    dp[i][j][k] = expectedSteps; // So, we take these steps and eventually stop.
                    // Now, we look at all possibilities in the conditionalSpace and go from there
                    // In each transition now, we've already taken all the steps associated with getting
                    // something from a zero pile. Now, we must add in 1 last step here.
                    // Equivalently, we could've aggregated all of the steps into the
                    // "expectedSteps" variable, which excludes the final success step!! Geometric probability!
                    if (i > 0) {
                        // Take from 1 => i-1, j, k
                        dp[i][j][k] += (i / conditionalSpace) * (1 + dp[i - 1][j][k]);
                    }
                    if (j > 0) {
                        // Take from 2 => i+1, j-1, k!
                        dp[i][j][k] += (j / conditionalSpace) * (1 + dp[i + 1][j - 1][k]);
                    }
                    if (k > 0) {
                        // Take from 3 => i, j+1, k-1!
                        dp[i][j][k] += (k / conditionalSpace) * (1 + dp[i][j + 1][k - 1]);
                    }

                    // There's the last possibility of taking a zero, so we just add another step
                    /*
                    // The simpler way is to write out the expected value equations and simplify
                    // to get rid of the E_ijk term on both sides

                    if (i > 0) {
                        // Take from 1 => i-1, j, k
                        dp[i][j][k] += (i / N) * (dp[i - 1][j][k]);
                    }
                    if (j > 0) {
                        // Take from 2 => i+1, j-1, k!
                        dp[i][j][k] += (j / N) * (dp[i + 1][j - 1][k]);
                    }
                    if (k > 0) {
                        // Take from 3 => i, j+1, k-1!
                        dp[i][j][k] += (k / N) * (dp[i][j + 1][k - 1]);
                    }


                    dp[i][j][k]++;
                    dp[i][j][k] *= (N / (i + j + k));
                    */


                }
            }
        }
//        for (int i = 0; i <= ones; i++) {
//            for (int j = 0; j <= twos; j++) {
//                for (int k = 0; k <= threes; k++) {
//                    System.out.println("Expected moves until 0,0,0 from " + i + " " + j + " " + k + " : " + dp[i][j][k]);
//                }
//            }
//        }
        out.println(dp[ones][twos][threes]);
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