/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class bread {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        BIT bit = new BIT(new int[N + 1]);
        long inversion1 = 0; // adding 100000 Choose 2 gets big!
        for (int i = 0; i < N; i++) {
            int bread = sc.nextInt();
            bit.add(bread, 1);
            // count how many breads were already added and incorrectly
            // greater than us, when the goal is to be ascending, not descending order
            inversion1 += bit.sum(bread + 1, N);
//            System.out.println(bit);
        }
        BIT bit2 = new BIT(new int[N + 1]);
        long inversion2 = 0; // adding 100000 Choose 2 gets big!
        for (int i = 0; i < N; i++) {
            int bread = sc.nextInt();
            bit2.add(bread, 1);
            // count how many breads were already added and incorrectly
            // greater than us, when the goal is to be ascending, not descending order
            inversion2 += bit2.sum(bread + 1, N);
//            System.out.println(bit2);
        }
        out.println(inversion1 % 2 == inversion2 % 2 ? "Possible" : "Impossible");
        out.close();
    }

    static class BIT {
        int[] tree;

        public BIT(int[] arr) {
            tree = arr;
            constructBIT();
        }

        public void constructBIT() {
            for (int i = 1; i < tree.length; i++) {
                int j = i + leastSignificantBit(i);
                if (j < tree.length) {
                    tree[j] += tree[i];
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Index: ");
            for (int i = 1; i < tree.length; i++) {
                sb.append(i + " ");
            }
            sb.append("\nValue: ");
            for (int i = 1; i < tree.length; i++) {
                sb.append(this.sum(i, i) + " ");
            }
            return sb.toString();
        }

        public int leastSignificantBit(int i) {
            return i & -i;
        }

        public void add(int i, int val) {
            while (i < tree.length) {
                tree[i] += val;
                i += leastSignificantBit(i);
            }
        }

        public int sum(int i, int j) {
            return prefix(j) - prefix(i - 1);
        }

        public int prefix(int i) {
            int ans = 0;
            while (i > 0) {
                ans += tree[i];
                i -= leastSignificantBit(i);
            }
            return ans;
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
