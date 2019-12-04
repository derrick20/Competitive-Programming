/*
 * @author derrick20
 * Couldn't solve. Issue was that I didn't limit the search by only traversing
 * the size of the DELETE ARRAY. I was going through to 1e18
 */

import java.io.*;
import java.util.*;

public class discard {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        long N = sc.nextLong();
        int M = sc.nextInt();
        long K = sc.nextLong();
        long[] arr = new long[M];
        for (int i = 0; i < M; i++) {
            arr[i] = sc.nextLong() - 1;
        }
        int i = 0;
        int shift = 0;
        int ops = 0;
        while (i < arr.length) {
            int lastShift = shift;
            long block = (arr[i] - lastShift) / K; // the block rounding down
            long nextBlock = (block + 1) * K;
            while (i < arr.length && arr[i] - lastShift < nextBlock) {
                shift++;
                i++;
                // we don't update lastShift since we don't want to delete everything
                // in one page. If things shift in, they will be handled in a
                // separate operation
            }
            // we deleted something from this page
            ops++;
        }
        out.println(ops);
        out.close();
    }

    static class Scanner {
        BufferedReader br;
        StringTokenizer st;

        Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        Scanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
