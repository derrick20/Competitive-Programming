/*
 * @author derrick20
 * The intuition on this problem is that we don't need to swap around
 * the cards, because the relative order is preserved. So, Freddie showed
 * that you can just query the range sum between the current card and the last,
 * and the remaining half of the deck will be the rest (the last is the top,
 * so from our card to the top is upper half, or lower half if we are before them
 * currently)
 */

import java.io.*;
import java.util.*;

public class cardsA {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int c = 0; c < C; c++) {
            int N = sc.nextInt();
            int[] arr = new int[N + 1];

            long total = 0;
            HashMap<Integer, Integer> index = new HashMap<>();
            for (int i = 1; i <= N; i++) {
                arr[i] = sc.nextInt();
                index.put(arr[i], i); // reverse map of value to its index
                total += arr[i];
            }
//            for (int i = N; i >= 1; i--) {
//                arr[i] = N - i + 1;
//                index.put(arr[i], i);
//                total += arr[i];
//            }
            BIT bit = new BIT(arr);
            long cost = 0;
//            System.out.println(bit.prefixSum(N));
            for (int val = 1; val <= N; val++) {
                int ind = index.get(val);
                long left, right;
                if (val == 1) {
                    left = bit.prefixSum(ind - 1);
                    right = bit.sum(ind, N);
                }
                else {
                    // use the old index to know where the top is, relative to us
                    int prevInd = index.get(val - 1);
                    if (prevInd < ind) {
                        left = bit.sum(prevInd, ind - 1);
                        right = total - left;
                    }
                    else {
                        right = bit.sum(ind, prevInd);
                        left = total - right;
                    }
                }
                // need to delete that card
                bit.set(ind, 0);
                total -= val;

                // Use the minimum cost to spend
                if (left < right) {
                    cost += left;
                }
                else {
                    cost += right;
                }
//                if (val < 100)
//                    System.out.println(val + " " + left + " " +  right);
            }
            out.println(cost);
        }
        out.close();
    }

    static class BIT {
        int[] arr;
        long[] tree;

        public BIT(int[] arr) {
            this.arr = arr;
            this.tree = new long[arr.length];
            constructBIT(arr, tree);
        }

        public void display() {
            for (int i = 1; i < tree.length; i++) {
                System.out.print(sum(i, i));
            }
            System.out.println();
        }

        public int leastSignificantBit(int x) {
            // by negating it, all
            return x & (-x);
        }

        public void constructBIT(int[] arr, long[] tree) {

            // copy arr values into tree
            for (int i = 1; i < arr.length; i++) {
                tree[i] = arr[i];
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
                    tree[j] += tree[i];
                }
            }
        }

        // return the sum
        public long sum(int i, int j) {
            long a = prefixSum(j);
            a -= prefixSum(i - 1);
            return a;
            // exclude the values under i
        }

        // returns sum from 1 to i of the array
        // propagate downward! (decomposing the sum)
        public long prefixSum(int i) {
            long sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= leastSignificantBit(i);
            }
            return sum;
        }

        // add a value of val at the ith value
        // propagate upward!
        public void add(int i, long val) {
            while (i < tree.length) {
                tree[i] += val;
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
