/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class bacteria {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        long C = sc.nextLong();
        for (int i = 0; i < C; i++) {
            long a = sc.nextLong();
            long b = sc.nextLong();
            long n = sc.nextLong();
            out.println(((fib(n + 1) * a) % mod + (fib(n) * b) % mod) % mod);
        }  // */
        out.close();
    }

    static long mod = (long) 1e9 + 7;

    static long fib(long n) {
        if (n <= 2) {
            return 1;
        }
        if (n % 2 == 0) {
            long a = fib(n / 2 + 1) % mod;
            long b = fib(n / 2 - 1) % mod;
            return (a * a % mod - b * b % mod + mod) % mod;
        }
        else {
            long a = fib(n / 2) % mod;
            long b = fib(n / 2 + 1) % mod;
            return (a * a % mod + b * b % mod) % mod;
        }
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
