/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class RoundCorridor {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long N = sc.nextLong();
        long M = sc.nextLong();
        int Q = sc.nextInt();
        long g = gcd(N, M);
        long top = M / g;
        long bot = N / g;
        while (Q-->0) {
            int sx = sc.nextInt();
            long sy = sc.nextLong() - 1;
            if (sx == 1) {
                sy /= bot;
            }
            else {
                sy /= top;
            }
            int ex = sc.nextInt();
            long ey = sc.nextLong() - 1;
            if (ex == 1) {
                ey /= bot;
            }
            else {
                ey /= top;
            }
            boolean poss = true;
            if (sy != ey) {
                poss = false;
            }
            out.println(poss ? "YES" : "NO");
        }
        out.println();

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