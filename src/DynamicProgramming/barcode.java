/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class barcode {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int rows = sc.nextInt();
        int cols = sc.nextInt();
        int min = sc.nextInt();
        int max = sc.nextInt();
        int[] black = new int[cols];
        for (int i = 0; i < rows; i++) {
            String row = sc.next();
            for (int j = 0; j < cols; j++) {
                if (row.charAt(j) == '#') {
                    black[j]++;
                }
                // The white spaces will be implicitly stored
            }
        }

        int[] prefix = new int[cols + 1];
        for (int i = 1; i <= cols; i++) {
            prefix[i] += prefix[i - 1] + black[i - 1];
        }

        // The dp array stores, for a given number of columns already processed
        // what is the minimum cost of partitioning (repainting) by grouping
        // those columns into sizes between min and max inclusive. And the color
        // of the last group of the partitioning
        int[][] dp = new int[2][cols + 1];
        Arrays.fill(dp[0], (int) 1e9);
        Arrays.fill(dp[1], (int) 1e9);
        dp[0][0] = 0; // 0 cost to do nothing
        dp[1][0] = 0; // 0 cost to do nothing

        for (int i = 0; i <= cols; i++) {
            for (int group = min; group <= max && i + group <= cols; group++) {
                int blackCt = prefix[i + group] - prefix[i];
                int whiteCt = group * rows - blackCt; // Complement of how many in each column
                // We build upon the result of having build up to this ith position,
                // and then append some group from size of min to max (as long as within bounds)
                // We build on top of prior black groupings with a white group, and onto previous
                // white groupings with a black group.
                dp[0][i + group] = Math.min(dp[0][i + group], dp[1][i] + whiteCt);
                dp[1][i + group] = Math.min(dp[1][i + group], dp[0][i] + blackCt);
            }
        }
        out.println(Math.min(dp[0][cols], dp[1][cols]));
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
