/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class table {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        long M = sc.nextLong();
        int K = sc.nextInt();

        // choose[i][j] Represents i choose j
        long[][] choose = new long[N + 1][N + 1];
        for (int i = 0; i <= N; i++) {
            choose[i][0] = 1;
            choose[i][i] = 1;
            for (int j = 1; j < i; j++) {
                choose[i][j] = (choose[i - 1][j - 1] + choose[i - 1][j]) % mod;
            }
        }

        // dp[i][j] stores a solution bag of using up to and including the ith
        // column the number of ways to place j dots among them.
        long[][] dp = new long[N][K + 1];

        // aux[i][j] = N choose j, raised to the power appropriate depending on i and M % N
        long[][] aux = new long[N][N + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= N; j++) {
                // We want it so that if i < M % N, then it'll add 1 power.
                aux[i][j] = quicklyExponentiate(choose[N][j], M / N);
                if (i < M % N) {
                    aux[i][j] *= choose[N][j];
                    aux[i][j] %= mod;
                }
            }
        }

        // The key insight is that, during our transitions, we only need to look
        // at unique columns modulo N, since those with same remainder must have
        // the same number of dots, because the N - 1 columns between them are shared,
        // so by symmetry, they must match!
        // Base case: 1st column
        for (int i = 0; i < N; i++) {
            // Count how many times a column of a certain residue will occur
//            System.out.println(i);
            // Place all possible numbers of dots into this column residue class
            for (int dots = 0; dots <= K; dots++) {
                if (i == 0) {
                    // Base case: all dots go inside here
                    if (dots <= N) {
                        // With only the first column, we can only fit at most N dots
                        dp[i][dots] = aux[i][dots];
                    }
                }
                else {
                    for (int used = 0; used <= dots && used <= N; used++) {
                        // We can use up to N dots at most. The rest must be pushed off
                        // onto other columns
                        long myWays = aux[i][used];
                        // This is using the remaining dots to fill up the rest
                        // of the grid (using a solution bag already computed!)
                        long otherWays = dp[i - 1][dots - used];
                        dp[i][dots] += (myWays * otherWays) % mod;
                        dp[i][dots] %= mod;
                    }
                }
            }
        }
        // Given a total of K dots allowed to distribute, and the N column residues
        // available (up to the N-1 indexed one), we have all ways counted
        out.println(dp[N - 1][K]);
        out.close();
    }

    static long mod = (long) 1e9 + 7;

    static long quicklyExponentiate(long x, long p) {
        if (p == 0) {
            return 1;
        }
        else if (p == 1) {
            return x;
        }
        else {
            long sqrt = quicklyExponentiate(x, p / 2);
            long ans = (sqrt * sqrt) % mod;
            if (p % 2 == 0) {
                return ans;
            }
            else {
                return (x * ans) % mod;
            }
        }
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
