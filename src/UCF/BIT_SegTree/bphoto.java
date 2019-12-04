/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class bphoto {
    public static void main(String[] args) throws Exception {
//        Scanner sc = new Scanner(System.in);
//        PrintWriter out = new PrintWriter(System.out);
        Scanner sc = new Scanner(new FileReader("bphoto.in"));//
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("bphoto.out")));

        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }


        coordinateCompress(arr);
        /*for (int i = 1; i <= N; i++) {
            System.out.print(arr[i-1]);
        }
        System.out.println(); */
        // a frequency table of each value
        BIT bit = new BIT(new int[N + 1]);

        // we add in values in the order that they are seen
        // therefore we know exactly how many taller than
        // us have been added already (to the left, since
        // we are traversing starting from the left to the right)
        int[] left = new int[N + 1];
        int[] right = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            // update the frequency of that value by 1
            bit.add(arr[i - 1], 1);
            // See how many were bigger than it (and have been added already)
            left[i] = bit.sum(arr[i - 1] + 1, N);
//            bit.display();
//            System.out.println("left" + " " + left[i]);
        }
        bit = new BIT(new int[N + 1]);
        for (int i = N; i >= 1; i--) {
            // update the frequency of that value by 1
            bit.add(arr[i - 1], 1);
            // See how many were bigger than it (and have been added already)
            right[i] = bit.sum(arr[i - 1] + 1, N);
//            bit.display();
//            System.out.println("right" + " " + right[i]);
        }
        int ct = 0;
        for (int i = 1; i <= N; i++) {
//            System.out.println(left[i] + " " + right[i]);
            int hi = Math.max(left[i], right[i]);
            int lo = Math.min(left[i], right[i]);
            if (hi > 2 * lo) {
                ct++;
//                System.out.println(i);
            }
        }
        out.println(ct);
        out.close();
    }


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
        // Then, use that map to convert the value of
        for (int i = 0; i < arr.length; i++) {
            arr[i] = valToIndex.get(arr[i]);
        }
    }

    static class BIT {
        int[] arr;
        int[] tree;

        public BIT(int[] arr) {
            this.arr = arr;
            this.tree = new int[arr.length + 1];
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

        public void constructBIT(int[] arr, int[] tree) {

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
                // this is the classic DP idea used in repeated knapsack
                if (j < arr.length) {
                    tree[j] += tree[i];
                }
            }
        }

        // return the sum
        public int sum(int i, int j) {
            return prefixSum(j) - prefixSum(i - 1);
            // exclude the values under i
        }

        // returns sum from 1 to i of the array
        // propagate downward! (decomposing the sum)
        public int prefixSum(int i) {
            int sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= leastSignificantBit(i);
            }
            return sum;
        }

        // add a value of val at the ith value
        // propagate upward!
        public void add(int i, int val) {
            while (i < tree.length) {
                tree[i] += val;
                i += leastSignificantBit(i);
            }
        }

        // Change a value at an index (basically add the new value and subtract
        // the original value
        public void set(int i, int k) {
            int val = sum(i, i);
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
