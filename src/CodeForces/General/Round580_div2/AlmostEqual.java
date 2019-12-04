/**
 * @author derrick20
 * Took a while for this one. It's almost always going to be a constructive
 * algorithm. The strategy in this situation was naturally to have some sort of
 * alternating structure. My observation was that when you shift the window
 * over, you gain a number on the right, lose on the left. If right - left
 * is 1, we stay at the current, -1 we go down. We need it to go up and down.
 * It is impossible if it is even, since then left = right, which would fail.
 *
 */
import java.io.*;
import java.util.*;

public class AlmostEqual {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        if (N % 2 == 0) {
            out.println("NO");
        }
        else {
            int[] seq = new int[2 * N];
            ArrayDeque<Integer> up = new ArrayDeque<>();
            ArrayDeque<Integer> down = new ArrayDeque<>();
            for (int i = 0; i <= N / 2; i++) {
                up.add(2 * i + 1);
            }
            for (int i = 0; i < N / 2; i++) {
                down.add(2 * N - 2 * i);
            }
            for (int i = 0; i < N; i++) {
                if (i % 2 == 0) {
                    seq[i] = up.removeFirst();
                    seq[i + N] = seq[i] + 1;
                }
                else {
                    seq[i] = down.removeFirst();
                    seq[i + N] = seq[i] - 1;
                }
            }
            out.println("YES");
            for (int val : seq) {
                out.print(val + " ");
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