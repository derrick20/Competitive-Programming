/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class PolygonForAngle {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        while (T-->0) {
            int X = sc.nextInt();
            int G = gcd(180, X);
            int ans = 180 / G;
            if (X / G > ans - 2) {
                ans *= 2;
            }
            out.println(ans);
        }
        out.close();
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static int GCD(int a, int b) {
        if (a < b) {
            int temp = b;
            b = a;
            a = temp;
        }

        while (b != 0) {
            a %= b;
            int temp = b;
            b = a;
            a = temp;
        }
        return a;
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