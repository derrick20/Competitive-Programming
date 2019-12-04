/**
 * @author derrick20
 * Ez Peasy. Remember about bounds is the key. Oh wait it didn't matter
 * since X was within -100, 100, and k too lololo.
 * Look at bounds more carefully!>!$
 */
import java.io.*;
import java.util.*;

public class OneClue {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int K = sc.nextInt();
        int X = sc.nextInt();
        int end = Math.min(1000000, X + K - 1);
        for (int i = Math.max(-1000000, X - K + 1); i <= end; i++) {
            out.print(i);
            if (i != end) {
                out.print(" ");
            }
        }
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