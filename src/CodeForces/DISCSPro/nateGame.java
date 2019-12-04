/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class nateGame {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] lo = new int[N];
        int[] hi = new int[N];

        int min = (int) 1e9;
        int max = (int) -1e9;
        for (int i = 0; i < N; i++) {
            int x0 = sc.nextInt();
            int y0 = sc.nextInt();
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();
            lo[i] = Math.min(y0, Math.min(y1, y2));
            hi[i] = Math.max(y0, Math.max(y1, y2));
            min = Math.min(min, lo[i]);
            max = Math.max(max, hi[i]);
        }

        int[] freq = new int[max - min + 1 + 1];
        for (int i = 0; i < N; i++) {
            freq[lo[i] - min]++;
            freq[hi[i] - min + 1]--;
        }

        int[] prefix = new int[freq.length + 1];
        int best = 0;
        for (int i = 1; i < freq.length + 1; i++) {
            prefix[i] += prefix[i - 1] + freq[i - 1];
            best = Math.max(best, prefix[i]);
        }
        out.println(best);
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
