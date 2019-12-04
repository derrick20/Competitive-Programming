/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class dimensionHop {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        double N = sc.nextDouble();
        double x = sc.nextDouble() / 100;
        double y = sc.nextDouble() / 100;

        double mon = N;
        double delta = N * y * (1 - x) / (1 + y * (x - 1));
        double wed = (1 - x) * (N + delta);
        double thur = wed * (1 - y);
        double tue = N + delta;
        double fri = thur;
        out.printf("%.5f ", mon);
        out.printf("%.5f ", tue);
        out.printf("%.5f ", wed);
        out.printf("%.5f ", thur);
        out.printf("%.5f ", fri);
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