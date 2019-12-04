/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class SquareFilling {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] grid = new int[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                grid[i][j] = sc.nextInt();
            }
        }
        int[][] m = new int[N + 1][M + 1];
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();

        for (int i = 1; i <= N - 1; i++) {
            for (int j = 1; j <= M - 1; j++) {
                if (grid[i][j] + grid[i + 1][j] + grid[i][j + 1] + grid[i + 1][j + 1] == 4) {
                    m[i][j] = 1;
                    m[i + 1][j] = 1;
                    m[i][j + 1] = 1;
                    m[i + 1][j + 1] = 1;
                    x.add(i);
                    y.add(j);
                }
            }
        }
        boolean poss = true;
        for (int i = 1; i <= N; i++) {
//            System.out.println(Arrays.toString(grid[i]));
//            System.out.println(Arrays.toString(m[i]));
            for (int j = 1; j <= M; j++) {
                if (grid[i][j] != m[i][j])
                    poss = false;
            }
        }
        if (poss) {
            out.println(x.size());
            for (int i = 0; i < x.size(); i++) {
                out.println(x.get(i) + " " + y.get(i));
            }
        }
        else {
            out.println(-1);
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