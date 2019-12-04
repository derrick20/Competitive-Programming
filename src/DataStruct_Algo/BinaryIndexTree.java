/*
 * @author derrick20
 */
/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class BinaryIndexTree {
    public static void main(String[] args) throws Exception {
        int[] arr = {1, 3, 4, 5, 6};
        BIT bit = new BIT(arr);
        System.out.println(bit.sum(2,4));
    }

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
}


