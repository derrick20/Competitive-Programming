/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class MagicStones {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();

        int[] base = new int[N];
        int[] goal = new int[N];
        ArrayList<Integer> diffs1 = new ArrayList<>();
        ArrayList<Integer> diffs2 = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            base[i] = sc.nextInt();
            if (i > 0) {
                diffs1.add(base[i] - base[i - 1]);
            }
        }
        for (int i = 0; i < N; i++) {
            goal[i] = sc.nextInt();
            if (i > 0) {
                diffs2.add(goal[i] - goal[i - 1]);
            }
        }
        boolean poss = true;
        // We must be anchored down on the edges, since those can't change,
        // and also because they ensure that the deltas lead us to the same
        // end state
        if (base[0] != goal[0] || base[N - 1] != goal[N - 1]) {
            poss = false;
        }

        Collections.sort(diffs1);
        Collections.sort(diffs2);
        for (int i = 0; i < N - 1; i++) {
            if (!diffs1.get(i).equals(diffs2.get(i))) {
                poss = false;
            }
        }
        out.println(poss ? "Yes" : "No");
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