/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class CommonDivisors {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        long[] arr = new long[N];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextLong();
        }
        long GCD = arr[0];
        for (int i = 1; i < arr.length; i++) {
            GCD = gcd(GCD, arr[i]);
        }
        long ct = 0;
        long x;
        for (x = 1; x * x < GCD; x++) {
            if (GCD % x == 0) {
                ct++;
            }
        }
        ct *= 2;
        if (GCD == x * x) {
            ct++;
        }
        out.println(ct);
        out.close();
    }

    static long gcd(long a, long b) {
        if (a < b) {
            long temp = b;
            b = a;
            a = temp;
        }
        while (b != 0) {
            a %= b;
            long temp = b;
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