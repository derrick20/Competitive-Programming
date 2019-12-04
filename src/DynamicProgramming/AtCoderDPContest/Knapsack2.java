/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Knapsack2 {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        long W = sc.nextLong();
        long[] weight = new long[N];
        long[] value = new long[N];
        for (int i = 0; i < N; i++) {
            weight[i] = sc.nextLong();
            value[i] = sc.nextLong();
        }

        TreeMap<Long, Long> dp = new TreeMap<>(Collections.reverseOrder());
        dp.put(0L, 0L);
        long best = 0;
        // What's the best value achievable with weight W?
        // We must do FORWARD DP, due to the nature of treemaps containing keys
        for (int item = 0; item < N; item++) {
            long val = value[item];
            long wt = weight[item];

            TreeMap<Long, Long> tempMap = new TreeMap<>();
            for (long oldWt : dp.keySet()) {
                if (oldWt + wt > W) continue;
                // We can either add to the first state or something that was
                // reachable already
                // This statement makes me feel safer but it's actually NOT necessary,
                // since we're jsut maximizing everything

                long newVal = dp.get(oldWt) + val;
                long oldVal = 0;
                if (dp.containsKey(oldWt)) {
                // If there was an old value, maybe we won't update
                    oldVal = dp.get(oldWt);
                }
                if (newVal > oldVal) {
                    // Promptly update
                    tempMap.put(oldWt + wt, newVal);
                    best = Math.max(best, newVal);
                }
            }
            dp.putAll(tempMap);
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
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}