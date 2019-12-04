/**
 * @author derrick20
 * Key greedy idea: we want to optimally pick things, so let's try
 * to sort it. In fact, I can guarantee what the subsequence is,
 * finding it in O(N) time. I will pick the largest number,
 * then, go to the smaller numbers in order of index.
 * This is our comparator definition: sort by biggest val,
 * THEN smallest index
 *
 * However, the issue with this is the transition from one subsequence
 * to another. The only way to do this effectively would be to insert
 * into the sequence. However, what this amounts to is incrementing
 * a delta to ALL values that come after me! So, this naturally
 * suggests a range sum update, which is easy through a BIT.
 * It's nicely arranged such that the BIT is 10^5 in size, so it all
 * works out!
 * However, one subtlety is that we don't actually know the exact
 * position of the current ACTIVE elements. However, we can binary
 * search for that position, the first position where we can hit
 * a number with a certain "effective" index, as specified in the current
 * kth sized subsequence
 *
 * Key bugs: having the
 */
import java.io.*;
import java.util.*;

public class OptimalSubsequences {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        // Make everything one-indexed, since BIT
        Pair[] seq = new Pair[N + 1];
        // Put an impossibly early pair that'll stay at the beginning
        seq[0] = new Pair(-1, (int) 2e9);

        // This sequence is gonna be sorted, but we need to retrieve the original value
        // for a given index
        HashMap<Integer, Integer> original = new HashMap<>();
        for (int i = 1; i <= N; i++) {
            int val = sc.nextInt();
            seq[i] = new Pair(i, val);
            original.put(i, val);
        }

        int M = sc.nextInt();
        Query[] queries = new Query[M];
        int[] answers = new int[M];
        for (int i = 0; i < M; i++) {
            int k = sc.nextInt();
            int pos = sc.nextInt();
            queries[i] = new Query(k, i, pos);
        }
        // The queries need to be sorted by the size of the subsequence
        //
        Arrays.sort(queries);

        Arrays.sort(seq);
//        System.out.println(Arrays.toString(queries));
//        System.out.println(Arrays.toString(seq));


        BIT bit = new BIT(new int[N + 1]);
        int k = 1;
        int ptr = 0;
        while (k <= N) {
            // the 0th arr pair won't be used!
            bit.add(seq[k].idx, 1);
//            System.out.println(bit + "\n");

            // Scan through all the queries of a certain k value
            while (ptr < M && queries[ptr].k == k) {
                // Find the idxOfCurr inside the current subsequence
                int index = binarySearch(bit, queries[ptr].pos);
//                System.out.println((seq[index] + " " + original.get(index)));
                answers[queries[ptr].idx] = original.get(index);
                ptr++;
            }
            k++;
        }

        for (int ans : answers) {
            out.println(ans);
        }
        out.close();
    }

    static int N;
    // Find the first INDEX within the BIT at which the "effective index"
    // stored by the bit at that position is value we inserted
    // 001112222333
    // Say we want to find 1, then we keep querying the BIT at different
    // positions, so if it is >= 1 or not.
    // Reduced to: 0011
    static int binarySearch(BIT bit, int val) {
        int lo = 1;
        int hi = N;
        while (lo < hi) {
            int mid = (hi - lo) / 2 + lo; // round DOWN
            if (bit.prefixSum(mid) >= val) {
                hi = mid; // We can guarantee this is fine
            }
            else {
                lo = mid + 1; // otherwise, we scrape up and eliminate it
            }
        }
        // Now they are equal
        return lo;
    }

    static class Query implements Comparable<Query> {
        int k, idx, pos;
        public Query(int myK, int index, int position) {
            k = myK; idx = index; pos = position;
        }
        public int compareTo(Query q2) {
            // All same size will be grouped together
            return k - q2.k;
        }
        public String toString() {
            return "(" + k + ", " + idx + ", " + pos + ")";
        }
    }

    static class Pair implements Comparable<Pair> {
        int idx, val;
        public Pair(int index, int value) {
            idx = index; val = value;
        }
        public int compareTo(Pair p2) {
            return (p2.val - val) != 0 ? (p2.val - val) : idx - p2.idx;
        }
        public String toString() {
            return "(" + idx + ", " + val + ")";
        }
    }

    static class BIT {
        int[] arr;
        int[] tree;

        // todo ***This ASSUMES THAT ARR IS 1-INDEXED ALREADY***
        public BIT(int[] arr) {
            this.arr = arr;
            this.tree = new int[arr.length];
            // copy arr values into tree
            for (int i = 1; i < arr.length; i++) {
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
            sb.append("\nPrefix Sum: ");
            for (int i = 1; i < tree.length; i++) {
                sb.append(this.prefixSum(i) + " ");
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

        public int findFirstOccurrence(int val) {
            int lastmask = Integer.highestOneBit(N);
            int bitmask = Integer.highestOneBit(N) >> 1;
            int index = lastmask;
            int sum = tree[index];

            int ans = sum == val ? index : -1;
            while (bitmask != 0) {
                int next = (index | bitmask);
                if (next <= N && sum < val) {
                    index = next;
                    sum += tree[next];
                }
                else if (sum >= val) {
                    // try to go downwards
                    // todo KEY ERROR HERE!!!
                    // todo NEEDED TO SUBTRACT THE CORRECT RESPONSIBILITY
                    sum -= tree[index];
                    index ^= lastmask;
                    index |= bitmask;
                    sum += tree[index];
                }

                if (sum == val) {
                    ans = index;
                }
                lastmask = bitmask;
                bitmask >>= 1;
            }
            return ans;
        }

        public int valueAt(int i) {
            int value = tree[i];
            // This overcounts by some amount of overlap due
            // to the responsibility (size given by LSB(i))
            if (i > 0) {
                // If i == 0, bad!
                int end = i - leastSignificantBit(i);
                int j = i - 1;
                // Slide down j's chain of authority until we meet,
                // subtracting everything in the path. This
                // is slightly more efficient than calling sum twice!
                while (j != end) {
                    value -= tree[j];
                    j -= leastSignificantBit(j);
                }
            }
            return value;
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

        public int[] nextInts(int N) {
            int[] res = new int[N];
            for (int i = 0; i < N; i++) {
                res[i] = (int) nextLong();
            }
            return res;
        }

        public long[] nextLongs(int N) {
            long[] res = new long[N];
            for (int i = 0; i < N; i++) {
                res[i] = nextLong();
            }
            return res;
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