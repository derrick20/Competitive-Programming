/*
 * @author derrick20
 */
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class vasyaArray {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();

        int[] diff = new int[N - 1]; // represents diff from a[i + 1] - a[i];
        boolean poss = true;
        ArrayList<Pair> increase = new ArrayList<>();
        ArrayList<Pair> decrease = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int incr = sc.nextInt();
            int l = sc.nextInt() - 1;
            int r = sc.nextInt() - 1;

            if (incr == 1) {
                increase.add(new Pair(l, r));
            }
            else {
                decrease.add(new Pair(l, r));
            }
        }

        for (Pair p : increase) {
            int l = p.l;
            int r = p.r;
            for (int j = l; j <= r - 1; j++) {
                diff[j] = 1;
            }
        }

        for (Pair p : decrease) {
            int l = p.l;
            int r = p.r;
            boolean available = false;
            for (int j = l; j <= r - 1; j++) {
                if (diff[j] != 1) {
                    available = true;
                    diff[j] = -1;
                }
            }
            poss &= available;
        }
        if (poss) {
            int[] res = new int[N];
            int curr = 1;
            int min = 1;
            res[0] = curr;
            for (int i = 1; i <= N - 1; i++) {
                if (diff[i - 1] >= 0) {
                    curr++;
                    res[i] = curr;
                } else {
                    curr--;
                    res[i] = curr;
                }
                min = Math.min(min, res[i]);
            }
            out.println("YES");
            for (int val : res) {
                out.print((val - min + 1) + " ");
            }
        }
        else {
            out.println("NO");
        }
        out.close();
    }

    static class Pair {
        int l, r;
        public Pair(int ll, int rr) {
            l = ll;
            r = rr;
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
