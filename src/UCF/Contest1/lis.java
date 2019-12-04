/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class lis {
    static long mod = (long) 1e9 + 7;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int c = 0; c < C; c++) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            int[] arr = new int[N];

            int maxValue = 0;
            for (int i = 0; i < N; i++) {
                arr[i] = sc.nextInt();
            }
            coordinateCompress(arr);
            // OTHER CRUCIAL ERROR, DIDN'T FIX THE MAX VALUE AFTER COMPRESSION
            for (int i = 0; i < N; i++) {
                maxValue = Math.max(maxValue, arr[i]);
            }

            BIT[] bits = new BIT[K + 1];
            for (int lis = K; lis >= 1; lis--) {
                bits[lis] = new BIT(new int[maxValue + 1]);
            }

            // The idea is that, for each index, we want to propagate information up
            // But, at each index, we can only go up 1 in the lis number, using something that is non-increasing
            bits[1].add(arr[0], 1);
            for (int i = 1; i < N; i++) {
                int val = arr[i];
                for (int lis = K; lis >= 1; lis--) {
                    // add all the possible sequences that end with the current value
                    // to the BIT with lis = lis. (+1 since val is its own lis
                    if (lis < K) {
                        bits[lis + 1].add(val, bits[lis].sum(val, maxValue));
                        // Only if there is room for our lis to expand (it cannot exceed
                        // K, do we append a value to something greater, which makes it
                        // begin a new LIS)

                        // TODO CRUCIAL ERROR
                        // I needed to have this statement BEFORE adding in new numbers,
                        // since otherwise I'd be double counting
                    }
                    if (lis == 1) {
                        // anything with a lower value can be appended to using this number
                        // However, the lis number stays the same, since increasing!
                        // Note, that we can also BEGIN a new lis of number 1 just by using it
                        long toAdd = (1 + bits[lis].prefixSum(val - 1)) % mod;
                        /*if (bits[1].sum(val, val) == 0) {
                            toAdd++;
                            // We can only create a new singlet if nothing was there
                        }*/
                        bits[lis].add(val, toAdd);
                    }
                    else {
                        bits[lis].add(val, bits[lis].prefixSum(val - 1));
                    }
                }
            }
            out.println(bits[K].prefixSum(maxValue));
        }
        out.close();
    }

    static void coordinateCompress(int[] arr) {
        TreeSet<Integer> values = new TreeSet<>();
        for (int val : arr) {
            values.add(val);
        }
        HashMap<Integer, Integer> valToIndex = new HashMap<>();
        int unique = values.size();
        for (int i = 1; i <= unique; i++) {
            int val = values.pollFirst();
            valToIndex.put(val, i);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = valToIndex.get(arr[i]);
        }
    }

    static class BIT {
        int[] arr;
        long[] tree;

        public BIT(int[] arr) {
            this.arr = arr;
            this.tree = new long[arr.length + 1];
            constructBIT(arr, tree);
        }

        public int leastSignificantBit(int x) {
            // by negating it, all
            return x & (-x);
        }

        public void constructBIT(int[] arr, long[] tree) {

            // copy arr values into tree
            for (int i = 0; i < arr.length; i++) {
                tree[i + 1] = arr[i];
            }
            // propagate information up to the pieces above it that would be responsible for it
            for (int i = 1; i < tree.length; i++) {
                int j = i + leastSignificantBit(i);
                // all of it's "parents" will need to possess its values,
                // but, we can be clever and only propagate to its immediate parent.
                // Since we are processing in order, that parent will propagate to its parent
                // eventually, so we are fine. Add methods are log(N) because we can't rely on our
                // parents eventually doing work for us.
                if (j < arr.length) {
                    tree[j] = (tree[j] + tree[i]) % mod;
                }
            }
        }

        // return the sum
        public long sum(int i, int j) {
            return (prefixSum(j) - prefixSum(i - 1) + mod) % mod;
            // exclude the values under i
        }

        // returns sum from 1 to i of the array
        // propagate downward! (decomposing the sum)
        public long prefixSum(int i) {
            long sum = 0;
            while (i > 0) {
                sum = (sum + tree[i]) % mod;
                i -= leastSignificantBit(i);
            }
            return sum;
        }

        // add a value of val at the ith value
        // propagate upward!
        public void add(int i, long val) {
            while (i < tree.length) {
                tree[i] = (tree[i] + val) % mod;
                i += leastSignificantBit(i);
            }
        }

        // Change a value at an index (basically add the new value and subtract
        // the original value
        public void set(int i, long k) {
            long val = sum(i, i);
            add(i, k - val);
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
