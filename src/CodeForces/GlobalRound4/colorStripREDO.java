/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class colorStripREDO {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        int M = sc.nextInt();

        colors = new int[N];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = sc.nextInt();
        }

        // from bound i to j, what is the position of lowest valued index
        // notice that it doesn't matter WHAT the value is, just that
        // it is the lowest
        indexOfMin = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // literally search that i -> j range for the lowest value
                // this isn't too bad, N^3 is fine for 500
                int index = i; // just let it be the first in the range arbitrarily
                for (int k = i; k <= j; k++) {
                    if (colors[k] < colors[index]) {
                        index = k;
                    }
                }
                indexOfMin[i][j] = index;
            }
        }

        memo = new long[N][N];

        for (int i = 0; i < N; i++) {
            // length 1 base case
            memo[i][i] = 1;
            // length 2 base case
            if (i + 1 < N) {
                memo[i][i + 1] = 2;
            }
        }

        out.println(solve(0, N - 1));

        out.close();
    }

    static long[][] memo;
    static int[][] indexOfMin;
    static int[] colors;
    static int N;
    static long mod = 998244353;

    static long solve(int i, int j) {
        // we should never have any out of bounds issues
        if (memo[i][j] != 0) {
            return memo[i][j];
        }
        else {
            long left = 0;
            long right = 0;
            int pivot = indexOfMin[i][j];

            if (i != pivot) {
                // keep picking the left bound to which we will paint to
                for (int l = i + 1; l <= pivot - 1; l++) {
                    left += (solve(i, l - 1) * solve(l, pivot - 1)) % mod;
                    left %= mod;
                }
                // we have to add the final case of 0:len as the segment because it
                // isn't easy to fit that into the for loop without a bunch of edge cases
                left += solve(i, pivot - 1);
                left %= mod;
                left += solve(i, pivot - 1);
                left %= mod;
            }
            else {
                left = 1;
            }

            if (j != pivot) {
                // keep picking the right bound to paint to, independent of the left bound
                for (int r = pivot + 1; r <= j - 1; r++) {
                    right += (solve(pivot + 1, r) * solve(r + 1, j)) % mod;
                    right %= mod;
                }
                // add the len:0 case
                right += solve(pivot + 1, j);
                right %= mod;
                // As well as the 0:len case
                right += solve(pivot + 1, j);
                right %= mod;
            }
            else {
                right = 1;
            }

            return memo[i][j] = (left * right) % mod;
        }
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
