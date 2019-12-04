/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

/*
2
5 3
5 1 2 4 6
9 6
10 1 10 1 10 1 10 1 10
 */
public class average {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int T = sc.nextInt();
        while (T-->0) {
            int N = sc.nextInt();
            int A = sc.nextInt();

            int[] pre = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                int score = sc.nextInt();
                pre[i] = score + pre[i - 1];
            }
//            int min = (int) 1e9;
            int max = 0;
            for (int i = 1; i <= N; i++) {
                // Normalize the rest of it with
                // an average score. Now, we see
                // how it would perform if the rest
                // of the array was average.
                // Currently, all elements of pre represent
                // a set of N scores (some being just padded A's)
                pre[i] += (N - i) * A;
//                min = Math.min(min, pre[i]);
                max = Math.max(max, pre[i]);
            }
            // Make it one indexed, so that everything is shifted down by min
            // but then add 1 to keep 1-indexing
            BIT bit = new BIT(new int[max+ 1]);
            long total = 0;
            // We know that the total count of the bit will
            // just be N, so no int overflow. However, summing N over
            // and over can get to 10^10!!
            long average = N * A; // - min + 1;
            for (int i = 1; i <= N; i++) {
//                pre[i] -= (min - 1);
                // We can subtract anything LESS OR EQUAL TO US,
                // Since that still maintains an above/equal average score!
                // Across our prefix
                // This only applies if we're high enough first. Removing
                // parts of our prefix is pointless if we're not yet above average/equal
                if (pre[i] >= average) {
                    long validRemovals = bit.prefix(pre[i]);
                    // We must include the fact that we could just not remove anything too!
                    total += validRemovals + 1;
                }
                bit.update(pre[i], 1);
            }
            out.println(total);
        }

        out.close();
    }

    static class BIT {
        int[] tree;

        // 1-INDEXED!
        public BIT(int[] arr) {
            tree = new int[arr.length];
            buildTree(arr);
        }

        public void buildTree(int[] arr) {
            for (int i = 1; i < arr.length; i++) {
                tree[i] = arr[i];
                int j = i + leastSignificantBit(i);
                if (j < arr.length) {
                    tree[j] += tree[i];
                }
            }
        }

        public int prefix(int i) {
            int ans = 0;
            while (i > 0) {
                ans += tree[i];
                i -= leastSignificantBit(i);
            }
            return ans;
        }

        public int sumRange(int i, int j) {
            return prefix(j) - prefix(i - 1);
        }

        public void update(int i, int amount) {
            while (i < tree.length) {
                tree[i] += amount;
                i += leastSignificantBit(i);
            }
        }

        public int leastSignificantBit(int i) {
            return i & -i;
        }
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