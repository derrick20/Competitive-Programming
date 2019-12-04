/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Bones {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int X = sc.nextInt();
        int ct = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int L = sc.nextInt();
                if (GCD(L, X) != 1) {
                    ct++;
                }
            }
        }
        out.println(ct);
        out.close();
    }

    static int GCD(int a, int b) {
        if (a < b) {
            int temp = b;
            b = a;
            a = temp;
        }

        while (b != 0) {
            a %= b;
            int temp = b;
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
