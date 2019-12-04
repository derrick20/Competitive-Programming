/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class StonesNIM {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int K = sc.nextInt();
        int[] amt = new int[N];
        for (int i = 0; i < N; i++) {
            amt[i] = sc.nextInt();
        }

        boolean[] canWin = new boolean[K + 1];
        // canWin[x] stores whether this is a winning state or not

        canWin[0] = false; // Can't play = lose
        // Transition is trying all N steps and seeing if we can win from one
        // A winning state is one that can reach AT LEAST 1 losing state
        // A losing state is one that reaches ONLY winning states, or can't take any
        // O(K states * N per transition) = 10^7
        for (int stones = 1; stones <= K; stones++) {
            boolean poss = false;
            // We want to disprove the question of being a losing state,
            // just by finding one counterexample
            for (int take : amt) {
                if (take > stones) break;
                if (!canWin[stones - take]) {
                    // We can reach a losing state and set up the opponent
                    // for failure, so we win!
                    poss = true;
                    break;
                }
            }
            canWin[stones] = poss;
        }
        out.println(canWin[K] ? "First" : "Second");
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