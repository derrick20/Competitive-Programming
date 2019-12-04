/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class BridgesOfKolsberg {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int T = sc.nextInt();
        while (T-->0) {
            int U = sc.nextInt();
            int[][] upperVals = new int[2][U]; // ID and value
            TreeMap<String, Integer> map = new TreeMap<>();
            for (int i = 0; i < U; i++) {
                sc.next(); // Lol useless
                String name = sc.next();
                int value = sc.nextInt();
                if (!map.containsKey(name)) {
                    map.put(name, map.size());
                }
                upperVals[0][i] = map.get(name);
                upperVals[1][i] = value;

            }

            int L = sc.nextInt();
            int[][] lowerVals = new int[2][L];
            for (int i = 0; i < L; i++) {
                sc.next(); // Lol useless
                String name = sc.next();
                int value = sc.nextInt();
                if (!map.containsKey(name)) {
                    map.put(name, map.size());
                }
                lowerVals[0][i] = map.get(name);
                lowerVals[1][i] = value;
            }
            int B = Math.min(U, L); // the max number of bridges possible
            int[][][] dp = new int[U + 1][L + 1][B + 1];
            // 0 Value from using the first 0 cities on top bottom, and 0 bridges
            // Everything in dp starts at 0, so it's fine
            // Backwards/pull dp is safer! No int overflow from subtraction!
            int maxVal = 0;
            int minBridges = 0;
            for (int u = 1; u <= U; u++) {
                int upperId = upperVals[0][u - 1];
                int upperValue = upperVals[1][u - 1];
                for (int l = 1; l <= L; l++) {
                    int lowerId = lowerVals[0][l - 1];
                    int lowerValue = lowerVals[1][l - 1];
                    for (int b = B; b >= 1; b--) {
                        // We can either take whatever bridges built with the previous
                        dp[u][l][b] = Math.max(dp[u][l][b], dp[u - 1][l][b]);
                        dp[u][l][b] = Math.max(dp[u][l][b], dp[u][l - 1][b]);
                        if (upperId == lowerId && (dp[u - 1][l - 1][b - 1] != 0 || b == 1)) {
                            int newVal = upperValue + lowerValue + dp[u - 1][l - 1][b - 1];
                            // We get this state by building whatever bridges before, then one right here at u, l
                            if (newVal > dp[u][l][b]) {
                                dp[u][l][b] = newVal;
                            }
                            if (newVal > maxVal) {
                                maxVal = newVal;
                                minBridges = b;
                                // In the case of equality, we still want to update minBridges if possible
                            }
                            if (newVal == maxVal) {
                                minBridges = Math.min(minBridges, b);
                            }
                        }
                    }
                }
            }
            out.println(maxVal + " " + minBridges);
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