/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class PrintStringRedo {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        while (T-->0) {
            int N = sc.nextInt();
            out.print("133");

            if (N > 100) {
                // The squaring is only beneficial for sufficiently high numbers
                int ct3 = binarySearch(0, N, N);
                // it definitely can't be N, but overshoot is fine
                int ct7 = N - (ct3 * ct3 + ct3 + 1);
                for (int i = 0; i < ct7; i++) {
                    out.print("7");
                }
                out.print("1");
                for (int i = 0; i < ct3; i++) {
                    out.print("3");
                }
                out.print("7");
            }
            else {
                for (int i = 0; i < N; i++) {
                    out.print("7");
                }
            }
            out.println();
        }

        out.close();
    }

    // Find the last n such that it is less than the target
    // 111000
    static int binarySearch(int lo, int hi, int target) {
        while (lo < hi) {
            long mid = (lo + hi) / 2 + 1; // Overshoot, so our hi creeps down
            if (mid * mid + mid + 1 <= target) {
                lo = (int) mid; // this is safe
            }
            else {
                hi = (int) mid - 1; // creep downwards otherwise
            }
        }
        return lo; // lo = hi now
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