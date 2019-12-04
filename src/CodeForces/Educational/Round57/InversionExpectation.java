/*
 * @author derrick20
 * Wow Ok my idea actually wasn't far off from the correct idea. Basically,
 * split into cases: counting inversions within the two  groups of fixed and
 * unfixed. I derived a recurrence to generate the total inversion count among
 * all permutations of some set of N numbers.
 * f(n) = n * f(n - 1) + (n - 1)(n) / 2 * (n - 1)! This worked on paper,
 * and it actually worked! In fact, you can be smarter and do 1-to-1 correspondence:
 * each pair is either inverted or not. So half will be!
 * Then, the other component was to count regular inversions in the fixed array. Easy
 * Then, we need to count ways for nonfixed to invert a fixed (be greater and earlier)
 * or the opposite (fixded is greater than an unfixed). I do this
 * by keeping a count of how many slots available up to this current number. For the
 * first case, I rangeSum to count number of unfixed numbers above the current number in question
 * And also multiply factorials to correctly count number of ways. Go in reverse for the other
 * case.
 * Overall, the hugest trouble were the modding. Never think that a step won't go overflow
 * Just put a mod at every step, just in case. CRUCIALLY - when the index (int i = 0...)
 * is involved, you MUST CAST TO LONG. To be safe, split up steps to mod individual
 * numbers! Just never screw this up again gahh!
 */
import java.io.*;
import java.math.BigInteger;
import java.util.*;

/*
Jesus Wept.
 */
public class InversionExpectation {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);//new FileReader("/Users/derrick/IntelliJProjects/src/CodeForces/Round57/InvExp.out"));
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] fixed = new int[N];
        int[] notFixed = new int[N];
        int[] seq = new int[N];
        int sizeF = 0;
        int sizeN;
        TreeSet<Integer> seen = new TreeSet<>();
        for (int i = 1; i <= N; i++) {
            seen.add(i);
        }
        for (int x = 0; x < N; x++) {
            int val = sc.nextInt();
            seq[x] = val;
            if (val != -1) {
                fixed[sizeF++] = val;
                seen.remove(val);
            }
        }
        sizeN = N - sizeF;
        for (int i = 0; i < sizeN; i++) {
            notFixed[i] = seen.pollFirst();
        }

        long[] factorial = new long[N + 1];
        factorial[0] = 1;
        // Build up a factorial array for speed
        for (int i = 1; i <= N; i++) {
            factorial[i] = (factorial[i - 1] * (long) i) % mod;
        }
        long fixedInv = 0;
        BIT fixedBIT = new BIT(new int[N + 1]);
        for (int i = 0; i < sizeF; i++) {
            fixedInv += fixedBIT.sum(fixed[i] + 1, N);
            fixedInv %= mod;
            fixedBIT.add(fixed[i], 1);
        }
        fixedInv %= mod;
        fixedInv *= factorial[sizeN]; // each inversion occurs numerous times!
        fixedInv %= mod;
//        System.out.println(Arrays.toString(factorial));
        long total = fixedInv;
        if (sizeN != 0) {

            long[] unfixedInv = new long[sizeN + 1];
            // Count the number of inversions among ALL permutations of those extra numbers. Doesn't
            // matter what they are, just need to count the intra inversions
            unfixedInv[0] = 0;

            for (long i = 2; i <= sizeN; i++) {
                unfixedInv[(int) i] = (i * unfixedInv[(int) i - 1]) % mod; // Branch factor
                unfixedInv[(int) i] += ((((i - 1) * i / 2) % mod) * factorial[(int) i - 1]) % mod; // the inversion resulting from the head
                unfixedInv[(int) i] %= mod;
            }
//            System.out.println(Arrays.toString(unfixedInv));
/*
6
3 -1 -1 4 2 -1
 */
            // Now for inter inversions
            long interInv = 0;
            BIT unfixedBIT = new BIT(new int[N + 1]);
            for (int i = 0; i < sizeN; i++) {
                unfixedBIT.add(notFixed[i], 1);
            }
            // Go through and do two passes. One forward to count how many ways we can invert
            // a fixed number. One backward to count how many ways a fixed number can invert us!
            // todo BRUHHHH CURR HAD TO BE LONGGNGNGNGN
            long curr = 0;
            for (int i = 0; i < N; i++) {
                if (seq[i] == -1) {
                    // add to the count of unfixed numbers prior
                    curr++;
                } else {
                    // Now, we can see how many things invert the current number
                    // For each slot, we can place something bigger than it.
                    // Then, we can independently arrange the remaining numbers = (sizeNotFixed - 1)!
                    long ways = (curr * unfixedBIT.sum(seq[i] + 1, N)) % mod;
                    ways *= factorial[sizeN - 1];
                    interInv += ways % mod;
                    interInv %= mod;
                }
            }
            curr = 0;
            for (int i = N - 1; i >= 0; i--) {
                if (seq[i] == -1) {
                    curr++;
                } else {
                    // For all the slots after this number, we can place something
                    // that gets inverted by this FIXED number
                    long ways = (curr * unfixedBIT.prefixSum(seq[i] - 1)) % mod;
                    ways *= factorial[sizeN - 1];
                    interInv += ways % mod;
                    interInv %= mod;
                }
            }
            total += (interInv + unfixedInv[sizeN]) % mod;
            total %= mod;
//            System.out.println("Fixed: " + fixedInv + " Inter: " + interInv + " Unfixed: " + unfixedInv[sizeN]);
        }
        BigInteger P = new BigInteger(total + "");
        BigInteger Q = new BigInteger(factorial[sizeN] + "");
        BigInteger MOD = new BigInteger(mod + "");
        out.println(P.multiply(Q.modInverse(MOD)).mod(MOD));
        out.close();
    }

    static long mod = (long) 998244353;

    static class BIT {
        int[] arr;
        int[] tree;

        // todo ***This ASSUMES THAT ARR IS 1-INDEXED ALREADY***
        public BIT(int[] arr) {
            this.arr = arr;
            this.tree = new int[arr.length];
            // copy arr values into tree
            for (int i = 1; i < tree.length; i++) {
                tree[i] = arr[i];
            }
            constructBIT(arr, tree);
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

        public int leastSignificantBit(int x) {
            // by negating it, all
            return x & (-x);
        }

        public void constructBIT(int[] arr, int[] tree) {
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