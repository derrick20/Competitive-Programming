/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class RestorePermutations {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        long[] sums = new long[N];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = sc.nextLong();
        }
//        available = new TreeSet<>();
        int[] arr = new int[N + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
//            if (i > 0) {
//                available.add(i);
//            }
        }
        bit = new BIT(arr);
//        used = new HashSet<>();

        int[] seq = new int[N];
        for (int i = N - 1; i >= 0; i--) {
            // For some reason it's crucial to binary search, finding the highest
            // number to give this sum
            int val = binarySearch(1, N, sums[i]);

//            if (available.higher(val - 1) != null) {
//                int temp = available.higher(val - 1);
//                if (bit.prefixSum(temp - 1) == sums[i]) {
//                    val = temp;
//                }
//            }
//            if (available.lower(val + 1) != null) {
//                int temp = available.lower(val + 1) ;
//                if (bit.prefixSum(temp - 1) == sums[i]) {
//                    val = temp;
//                }
//            }
            seq[i] = val;
            bit.add(val, -val);
//            System.out.println(bit);
//            available.remove(val);
        }
        for (int i = 0; i < seq.length; i++) {
            out.print(seq[i] + " ");
        }
        out.close();
    }

    static BIT bit;
//    static HashSet<Integer> used;
//    static TreeSet<Integer> available;

    // Find the number such that the sum of things below it in the
    // current sequence state equals the goal sum
    // 0001111 Let's find the lowest one that's >= it. That's the same thing
    // ISSUE: if we find the lowest thing that can still produce the sum,
    // there will be duplicates in the case of a sum of 0. Otherwise, each
    // value should yield a unique prefix sum. However,
    // if we instead climb upwards to find the highest thing that is under
    // the sum. That works because a series of 0's will be in decreasing order
    // (increasing from right to left, so that' why climbing up makes sense)

    // Instead, let's try finding the highest thing that's <= it
    // 1100
    static int binarySearch(int lo, int hi, long sum) {
        while (lo < hi) {
            int mid = (lo + hi + 1) / 2; // go towards the 0's, so we crawl down
            // If it's not used, then we need to go up.
            if (bit.prefixSum(mid - 1) <= sum) {
                lo = mid;
            }
            else {
                hi = mid - 1;
            }
        }
        return lo;
    }

    static class BIT {
        int[] arr;
        long[] tree;

        // todo ***This ASSUMES THAT ARR IS 1-INDEXED ALREADY***
        public BIT(int[] arr) {
            this.arr = arr;
            this.tree = new long[arr.length];
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

        public void constructBIT(int[] arr, long[] tree) {
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
            return prefixSum(j) - prefixSum(i - 1);
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
        public void add(int i, int val) {
            while (i < tree.length) {
                tree[i] += val;
                i += leastSignificantBit(i);
            }
        }
    }

    static class FastScanner {
        public int BS = 1<<16;
        public char NC = (char)0;
        byte[] buf = new byte[BS];
        int bId = 0, size = 0;
        char c = NC;
        double cnt = 1;
        BufferedInputStream in;

        public FastScanner() {
            in = new BufferedInputStream(System.in, BS);
        }

        public FastScanner(String s) {
            try {
                in = new BufferedInputStream(new FileInputStream(new File(s)), BS);
            }
            catch (Exception e) {
                in = new BufferedInputStream(System.in, BS);
            }
        }

        private char getChar(){
            while(bId==size) {
                try {
                    size = in.read(buf);
                }catch(Exception e) {
                    return NC;
                }
                if(size==-1)return NC;
                bId=0;
            }
            return (char)buf[bId++];
        }

        public int nextInt() {
            return (int)nextLong();
        }

        public long nextLong() {
            cnt=1;
            boolean neg = false;
            if(c==NC)c=getChar();
            for(;(c<'0' || c>'9'); c = getChar()) {
                if(c=='-')neg=true;
            }
            long res = 0;
            for(; c>='0' && c <='9'; c=getChar()) {
                res = (res<<3)+(res<<1)+c-'0';
                cnt*=10;
            }
            return neg?-res:res;
        }

        public double nextDouble() {
            double cur = nextLong();
            return c!='.' ? cur:cur+nextLong()/cnt;
        }

        public String next() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=getChar();
            while(c>32) {
                res.append(c);
                c=getChar();
            }
            return res.toString();
        }

        public String nextLine() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=getChar();
            while(c!='\n') {
                res.append(c);
                c=getChar();
            }
            return res.toString();
        }

        public boolean hasNext() {
            if(c>32)return true;
            while(true) {
                c=getChar();
                if(c==NC)return false;
                else if(c>32)return true;
            }
        }
    }
}