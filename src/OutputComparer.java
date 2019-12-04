/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class OutputComparer {

    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(new FileReader("piepie.out"));
        FastScanner sc2 = new FastScanner(new FileReader("5.out"));
        while (true) {
            int a = sc.nextInt();
            int b = sc2.nextInt();
            if (a != b) {
                System.out.println(a + " " + b);
            }
        }

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