/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class XORGuessing {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        // Better idea: Use the fact there are 2 queries as a hint.
        // Also 100 < 2^7 = 2^(14 / 2). This suggests doing half and
        // half with each of our queries. So, the first 7 bits can
        // be found by giving it a set with empty bits for the first 7,
        // then wiping away the second half
        out.print("? ");
        int start = 1;
        int end = 100;
        for (int i = start; i <= end; i++) {
            out.print(i + (i == end ? "\n" : " "));
        }
        out.flush();
        int ans = 0;
        int topHalf = sc.nextInt();
        topHalf = (topHalf >> 7) << 7;
        ans += topHalf;
        out.print("? ");
        for (int i = start; i <= end; i++) {
            // Here, we try the same 100, but with 0's appended
            out.print((i << 7) + (i == end ? "\n" : " "));
        }
        out.flush();
        int bottomHalf = sc.nextInt();
        bottomHalf %= 128; // clean off everything above.
        ans += bottomHalf;
//        System.out.println(topHalf + " " + bottomHalf);
        out.println("! " + ans);
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