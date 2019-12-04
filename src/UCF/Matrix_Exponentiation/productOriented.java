/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class productOriented {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long N = sc.nextLong();
        long[][] f = new long[3][1];
        // We do this in reverse order, since the higher indices
        // are lower within the array (visually)
        // 5
        // 2
        // 1
        for (int i = 2; i >= 0; i--) {
            f[i][0] = sc.nextLong();
        }
        long C = sc.nextLong();
        long[][] start = {
                {1, 1, 1},
                {1, 0, 0},
                {0, 1, 0}
        };
        long[][] end = matrixExponentiate(start, N - 3);
        // We must perform the transitions on each base case (f3, f2, f1)
        /*long[][] initial3 = {{1}, {0}, {0}};
        long[][] initial2 = {{0}, {1}, {0}};
        long[][] initial1 = {{0}, {0}, {1}};
        long[][] powers3 = getProduct(end, initial3);
        long[][] powers2 = getProduct(end, initial2);
        long[][] powers1 = getProduct(end, initial1);
        // For each power, we exponentiate the original base case by the power calculated based on the outcome
        // The*/
        f[0][0] = fastExponentiate(f[0][0], end[0][0]);
        f[1][0] = fastExponentiate(f[1][0], end[0][1]);
        f[2][0] = fastExponentiate(f[2][0], end[0][2]);
//        for (int i = 0; i < 3; i++) {
//            out.println(f[i][0]);
//        }
        // 2 * (N)(N+1) / 2 (but N actually equals N - 3)
        long coefficient = fastExponentiate(C, (N - 3));
        coefficient = fastExponentiate(coefficient, N - 3 + 1);
        long ans = coefficient;
        ans = (ans * f[0][0]) % mod;
        ans = (ans * f[1][0]) % mod;
        ans = (ans * f[2][0]) % mod;
        out.println(ans);
        out.close();
    }

    static long mod = (long) 1e9 + 7;

    static long fastExponentiate(long n, long power) {
        if (power == 1) {
            return n;
        }
        else {
            long sqrt = fastExponentiate(n, power / 2) % mod;
            long ret = (sqrt * sqrt) % mod;
            if (power % 2 == 0) {
                return ret;
            }
            else {
                ret = (ret * n) % mod;
                return ret;
            }
        }
    }

    static long[][] matrixExponentiate(long[][] matrix, long power) {
        if (power == 1) {
            return matrix;
        }
        else {
            long[][] sqrt = matrixExponentiate(matrix, power / 2);
            if (power % 2 == 1) {
                return getProduct(matrix, sqrt);
            } else {
                return getProduct(sqrt, sqrt);
            }
        }
    }

    static long[][] getProduct(long[][] a, long[][] b) {
        int aRows = a.length;
        int aCols = a[0].length;
        int bRows = b.length;
        int bCols = b[0].length;
        // the number of aCols must match the bRows, for us
        // to match them perfectly
        int strip = aCols = bRows;
        long[][] res = new long[aRows][bCols];
        // for each intersecting aRow and bCol, we
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                for (int k = 0; k < strip; k++) {
                    // multiply the values of the ith row of a
                    // by the values of the jth column of b
                    long value = (a[i][k] % mod);
                    value = (value * (b[k][j] % mod)) % mod;
                    res[i][j] = (res[i][j] + value) % mod;
                }
            }
        }
        return res;
    }

    static class Scanner {
        BufferedReader br;
        StringTokenizer st;

        Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        Scanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
