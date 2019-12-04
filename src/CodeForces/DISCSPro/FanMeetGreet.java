/*
 * @author derrick20
 */
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class FanMeetGreet {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long X = sc.nextLong();
        out.println(X);
//        BigInteger[][] matrix = new BigInteger[2][2];
//        matrix[0] = new BigInteger[]{new BigInteger("2"), new BigInteger("-1")};
//        matrix[1] = new BigInteger[]{new BigInteger("1"), new BigInteger("0")};
//
//        BigInteger[][] ans = fastMatrixExpo(matrix, X - 1);
//        System.out.println(ans[0][0]);
        out.close();
    }

    static BigInteger[][] fastMatrixExpo(BigInteger[][] matrix, long exp) {
        if (exp == 0) {
            return identity();
        }
        if (exp == 1) {
            return matrix;
        }
        else {
            BigInteger[][] root = fastMatrixExpo(matrix, exp / 2);
            BigInteger[][] ans = multiply(root, root);
            if (exp % 2 == 0) {
                return ans;
            }
            else {
                return multiply(matrix, ans);
            }
        }
    }

    static BigInteger[][] multiply(BigInteger[][] m1, BigInteger[][] m2) {
        BigInteger[][] ans = new BigInteger[2][2];
        ans[0][0] = m1[0][0].multiply(m2[0][0]).add(m1[0][1].multiply(m2[1][0]));
        ans[0][1] = m1[0][0].multiply(m2[0][1]).add(m1[0][1].multiply(m2[1][1]));
        ans[1][0] = m1[1][0].multiply(m2[0][0]).add(m1[1][1].multiply(m2[1][0]));
        ans[1][1] = m1[1][0].multiply(m2[0][1]).add(m1[1][1].multiply(m2[1][1]));
        return ans;
    }

    static BigInteger[][] identity() {
        BigInteger[][] matrix = new BigInteger[2][2];
        matrix[0] = new BigInteger[]{BigInteger.ONE, BigInteger.ZERO};
        matrix[1] = new BigInteger[]{BigInteger.ZERO, BigInteger.ONE};
        return matrix;
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
