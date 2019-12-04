/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class LCS {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] s = sc.next().toCharArray();
        char[] t = sc.next().toCharArray();
        int[][] dp = new int[s.length + 1][t.length + 1];
        // Base case, first chars
        // dp[i][j] store the LCS up to and including the first i, first j chars
        // dp[0][0] = 0 automatically
        for (int i = 1; i <= s.length; i++) {
            for (int j = 1; j <= t.length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (s[i - 1] == t[j - 1]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        int i = s.length;
        int j = t.length;
        StringBuilder ans = new StringBuilder();
        while (i > 0 && j > 0) {
            // There are 2 approaches. On one hand, we can do
            // the below if statement, which ensures that we've found a
            // bona fide match and can add to our answer.
            // The other approach below that, is to maintain the invariant
            // that we're right at the corner of the rectangle, into which
            // a square with a match points. The landscape of the dp array
            // will be rectangles starting from a top-left point, extending
            // down and painting the lower right with some number of possible
            // length subsequences, which get progressively overriden
            // by higher (longer) sequences.
            // Thus, those fringe points are the locations of the actual string
            // position matches. We go there to append to our answer!
            // I feel my way is more logical - if we are using the landscape
            // for one section (the traversal to the corner), we should also
            // apply its properties at the fringe corners!
//            if (s[i - 1] == t[j - 1]) {
////                dp[i][j] = 9;
//                i--; j--;
//                ans.append(s[i]);
//            }
//            else {
                // Non-matches, so we must climb back up the strings,
                // UNTIL we reach that corner point
            while (i > 0 && dp[i][j] == dp[i - 1][j]) {
                i--;
            }
            while (j > 0 && dp[i][j] == dp[i][j - 1]) {
                j--;
            }
            if (i > 0 && j > 0) {
                i--;
                j--;
                ans.append(s[i]);
            }
//            }
        }
        out.println(ans.reverse());
        out.close();
    }

    /*
    Bottom-up Ain't It

    static StringBuilder[][] memo;

    static StringBuilder solve(int i, int j, char[] s, char[] t) {
        if (i == s.length || j == t.length) {
            return new StringBuilder;
        }
        else if (memo[i][j] != null) {
            return memo[i][j];
        }
        else {
            if (s[i] == t[j]) {
                // In fact we need to append the solution to the rest to this!
                StringBuilder ans = new StringBuilder(s[i] + "").append(solve(i + 1, j + 1, s, t));
                return memo[i][j] = ans;
            }
            else {
                StringBuilder a = solve(i + 1, j, s, t);
                StringBuilder b = solve(i, j + 1, s, t);
                if (a.length() >= b.length()) {
                    memo[i][j] = a;
                }
                else {
                    memo[i][j] = b;
                }
                return memo[i][j];
            }
        }
    }
    */

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