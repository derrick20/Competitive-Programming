/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class BinaryStrings {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        while (T-->0) {
            String x = sc.next();
            String y = sc.next();
            int gap = 0;
            for (int i = y.length() - 1; i >= 0; i--) {
                if (y.charAt(i) == '1') {
                    break;
                }
                gap++;
            }
            int k = 0;
            for (int i = x.length() - 1 - gap; i >= 0; i--) {
                if (x.charAt(i) == '1') {
                    break;
                }
                k++;
            }
            out.println(k);
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
