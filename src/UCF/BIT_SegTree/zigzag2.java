/*
 * @author derrick20
 * AHH COUNTING THE DIFFERENT ONES WAS WRONG. NOT N CHOOSE 2
 * Instead, do different ones above and below, since this will give
 * us the pairs necessarily correctly
 */
import java.io.*;
import java.util.*;

public class zigzag2 {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int C = sc.nextInt();
        while (C-->0) {
            int N = sc.nextInt();
            int[] arr = new int[N];
            for (int i = 0; i < N; i++) {
                arr[i] = sc.nextInt();
            }
            coordinateCompress(arr);
            BIT endHi = new BIT(1);
            BIT endLo = new BIT( 0);
            BIT diff = new BIT(0);
//            BIT lenTwo = new BIT(0);
            long extraDiff = 0;
            for (int i = 0; i < N; i++) {
                int val = arr[i];
                endHi.add(val, 1);
                endLo.add(val, 1);
                diff.add(val, 1);
                if (i > 0) {
                    // every lo ending sequence can be appended by something greater than it
                    // every hi sequence that ends on a value greater than us (STRICTLY)
                    // can be expanded by adding this current smallish value
                    endHi.add(val, endLo.prefix(val - 1));
                    endLo.add(val, endHi.query(val + 1, maxVal));
                    extraDiff = (extraDiff + diff.prefix(val - 1) + diff.query(val + 1, maxVal)) % mod;
                }
            }
//            endHi.display();
//            endLo.display();
            long res = endHi.prefix(maxVal);
            res = (res + endLo.prefix(maxVal) % mod);
//            res = (res - ((N * (N - 1) / 2) % mod) + mod) % mod;
            res = (res - N * 2 + mod) % mod;
            // SINGLETS ARE DOUBLE COUNTED TWICE (1 for EACH HI/LO BIT)
//            for (int i = 1; i <= maxVal; i++) {
//                res = (res - ((numEqual[i] * (numEqual[i] - 1) / 2) % mod) + mod) % mod;
//            }
            res = (res - extraDiff + mod) % mod;
            out.println(res);
        }
        out.close();
    }

    static long mod = (long) 1e9 + 7;
    static int maxVal;
    static int[] numEqual;

    static void coordinateCompress(int[] arr) {
        HashMap<Integer, Integer> valToIndex = new HashMap<>();
        TreeSet<Integer> copy = new TreeSet<>();
        for (int i = 0; i < arr.length; i++) {
            copy.add(arr[i]);
        }
        // Basically, have a map of the value to its rank within the whole array
        int index = 1;
        for (int value : copy) {
            valToIndex.put(value, index++);
            // but we want to 1-index it
        }
        maxVal = index - 1;
        numEqual = new int[maxVal + 1];
        // Then, use that map to convert the value of
        for (int i = 0; i < arr.length; i++) {
            arr[i] = valToIndex.get(arr[i]);
            numEqual[arr[i]]++;
        }
    }

    static class BIT {
//        int[] arr;
        long[] tree;
        int type;

        public BIT(int type) {
//            this.arr = arr;
            tree = new long[maxVal + 1];
            this.type = type;
        }

        public void display() {
            for (int i = 1; i < tree.length; i++) {
                System.out.print(query(i, i));
            }
            System.out.println();
        }

        public long prefix(int i) {
            long res = 0;
            while (i > 0) {
                res = (res + tree[i]) % mod;
                i -= leastSignificantBit(i);
            }
            return res;
        }

        // negative i = complement of i + 1. This cascades everything up until
        // we reach the first 0 within the complement, which corresponds to the
        // first 1 within i, therefore producing the correct result!
        public int leastSignificantBit(int i) {
            return i & -i;
        }

        public long query(int i, int j) {
            return (prefix(j) - prefix(i - 1) + mod) % mod;
        }

        public void add(int i, long val) {
            while (i < tree.length) {
                tree[i] = (tree[i] + val) % mod;
                i += leastSignificantBit(i);
            }
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
