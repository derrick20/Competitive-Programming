/**
 * @author derrick20
 * ARGH Beware of the small numbers in a problem. If it has 3 cuts, you can
 * bet your rat's tootum that it's gonna take advantage of that. In either
 * two-pointers or some other idea. Remember, it's very simple to relate
 * those together. This might suggest DP, but 3 is so small, we might think
 * it's possible to be greedy! Ahah, what if we just look at the small case
 * of 1 cut. That's easy, we could sweep through just for certainty sake,
 * but it's definitely gonna be quick (right near the middle +- some iota).
 * Now, we think about induction. If we are asking about 2 cuts, we can try
 * all our positions for first cut, then keep a pointer to the second cut position.
 * Again, it's gonna sweep outward? Wait actually no you MUST take advantage of
 * the symmetry of having 3 cuts. Hmm ok come back this seems a bit sus.
 */
import java.io.*;
import java.util.*;

public class EqualCut {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt() - (i + 1);
        }
        Arrays.sort(arr);
        long total = 0;
        for (int i = 0; i < N / 2; i++) {
            total += arr[N - i - 1] - arr[i];
        }
        out.println(total);
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