/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class TwoBurgers {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        while (T-->0) {
            int buns = sc.nextInt();
            int beef = sc.nextInt();
            int chicken = sc.nextInt();
            int hVal = sc.nextInt();
            int cVal = sc.nextInt();
            int profit = 0;
            if (cVal > hVal) {
                int burgers = Math.min(buns / 2, chicken);
                profit += burgers * cVal;
                buns -= 2 * burgers;
                profit += hVal * Math.min(buns / 2, beef);
            }
            else {
                int burgers = Math.min(buns / 2, beef);
                profit += burgers * hVal;
                buns -= 2 * burgers;
                profit += cVal * Math.min(buns / 2, chicken);
            }
            out.println(profit);
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