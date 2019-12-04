/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class MisLoveArray {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int lo = sc.nextInt();
        int hi = sc.nextInt();

        // Sum from 1 to 2^(lo - 1) (lo powers of 2, so reached min uniqueness)
        // then, N - lo  numbers left, all will be 1
        long min = (1 << lo) - 1 + (N - lo);
        // Sum from 1 to 2^(hi - 1)
        // Then, N - hi numbers left, all will be 2^(hi - 1)
        long max = (1 << hi) - 1 + (N - hi) * (1 << (hi - 1));
        /*
        int i = 1;
        while (N - i >= lo) {
            i++;
            min++;
        }
        for (int unique = 1, curr = 1; i < N; i++) {
            if (unique < lo) {
                curr <<= 1;
                unique++;
            }
            min += curr;
        }
        i = 1;
        for (int unique = 1, curr = 1; i < N; i++) {
            if (unique < hi) {
                curr <<= 1;
                unique++;
            }
            max += curr;
        }
        */
        out.println(min + " " + max);
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