/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class EasyProblem {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        char[] letters = sc.next().toCharArray();
        int[] cost  = new int[N];
        for (int i = 0; i < cost.length; i++) {
            cost[i] = sc.nextInt();
        }

        long[] dpPrev = new long[4];

        long oo = (long) 1e18;
        // 0 - nothing
        // 1 - H
        // 2 - HA
        // 3 - HAR
        // dp[i] stores the minimum cost with the current running
        // prefix of one of the mapped prefixes above for i.
        Arrays.fill(dpPrev, oo);
        if (letters[0] == 'h') {
            dpPrev[0] = cost[0]; // Spend to delete H
            dpPrev[1] = 0; // Or don't spend, so cost change happens
        }
        else {
            dpPrev[0] = 0;
        }
        for (int i = 1; i < N; i++) {
            long[] dpNext = new long[4];
            Arrays.fill(dpNext, oo);
            // Look at the cases of adding the letter (not spending
            // anything to delete it)
            // We could have reached this state either using the last state
            // with this letter (just adding it again is neutral). Or
            // we could've not had this letter, then just added it.

            // Then, look at the case of spending to delete it, maintaining the current
            // prefix as it is. This is just adding the cost of the letter
            // in question (which is one beyond the current prefix's last letter)
            char curr = letters[i];
            if (curr == 'h') {
                dpNext[1] = Math.min(dpPrev[1], dpPrev[0]);
                dpNext[0] = dpPrev[0] + cost[i];

                // The rest of it will be the same (doesn't effect it)
                dpNext[2] = dpPrev[2];
                dpNext[3] = dpPrev[3];
            }
            else if (curr == 'a') {
                dpNext[2] = Math.min(dpPrev[2], dpPrev[1]);
                dpNext[1] = dpPrev[1] + cost[i];

                dpNext[0] = dpPrev[0];
                dpNext[3] = dpPrev[3];
            }
            else if (curr == 'r') {
                dpNext[3] = Math.min(dpPrev[3], dpPrev[2]);
                dpNext[2] = dpPrev[2] + cost[i];

                dpNext[0] = dpPrev[0];
                dpNext[1] = dpPrev[1];
            }
            else if (curr == 'd') {
                // we CAN'T ADD THIS, only pay to maintain our position
                dpNext[3] = dpPrev[3] + cost[i];

                dpNext[0] = dpPrev[0];
                dpNext[1] = dpPrev[1];
                dpNext[2] = dpPrev[2];
            }
            else {
                // It's useless, so we skip it
                continue;
            }
            dpPrev = dpNext;
        }
        long ans = oo;
        for (long amt : dpPrev) {
            ans = Math.min(ans, amt);
        }
        out.println(ans);
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