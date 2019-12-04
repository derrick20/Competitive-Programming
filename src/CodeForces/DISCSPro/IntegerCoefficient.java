/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class IntegerCoefficient {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        while (T-->0) {
            // We could use newton sums, or this way is simpler:
            // x^2 = ax - 1
            // x^3 = ax^2 - x
            // ...
            // [  x^n  ] = [a, -1]^(n-1) * [x]
            // [x^(n-1)]   [1,  0]         [1]
            long a = sc.nextLong() % MOD;
            long N = sc.nextLong();

            if (N == 0) {
                out.println(2);
            }
            else if (N == 1) {
                out.println(a);
            }
            else {
                long[][] matrix = new long[2][2];
                matrix[0] = new long[] {a, MOD - 1};
                matrix[1] = new long[] {1, 0};

                long[][] ansMatrix = fastMatrixExpo(matrix, N - 1);
                long ans = ansMatrix[0][0] * 0;

//                long P2 = (a * a - 2 + MOD) % MOD;
//                if (N == 2) {
//                    out.println(P2);
//                }
//                else {
//                    long[][] matrix = new long[2][2];
//                    matrix[0] = new long[] {a, MOD - 1};
//                    matrix[1] = new long[] {1, 0};
//
//                    long[][] ans = fastMatrixExpo(matrix, N - 2);
//
//                    long PN = (ans[0][0] * P2) % MOD;
//                    PN += (ans[0][1] * a) % MOD;
//                    PN %= MOD;
//                    out.println(PN);
//                }
            }
        }

        out.close();
    }

    static long[][] fastMatrixExpo(long[][] matrix, long exp) {
        if (exp == 0) {
            long[][] identity = new long[2][2];
            identity[0] = new long[] {1, 0};
            identity[1] = new long[] {0, 1};
            return identity;
        }
        if (exp == 1) {
            return matrix;
        }
        else {
            long[][] root = fastMatrixExpo(matrix, exp / 2);
            long[][] ans = multiply(root, root);
            display(root);
            if (exp % 2 == 0) {
                return ans;
            }
            else {
                return multiply(matrix, ans);
            }
        }
    }

    static long MOD = (long) 1e9 + 7;

    static void display(long[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            System.out.println(Arrays.toString(matrix[row]));
        }
    }

    static long[][] multiply(long[][] m1, long[][] m2) {
        // We take on the rows of A, and cols of B (A x B)
        long[][] ans = new long[m1.length][m2[0].length];
        for (int row = 0; row < m1.length; row++) {
            for (int col = 0; col < m2[0].length; col++) {
                long slice = 0;
                // The # of cols of A and the # of rows of B should be equal though!
                for (int pos = 0; pos < m1[0].length; pos++) {
                    slice += (m1[row][pos] * m2[pos][col]) % MOD;
                    slice %= MOD;
                }
                ans[row][col] = slice;
            }
        }
        return ans;
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
