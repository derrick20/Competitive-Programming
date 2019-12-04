/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class TooManySegmentsImproved {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int K = sc.nextInt();
        Interval[] arr = new Interval[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Interval(i + 1, sc.nextInt(), sc.nextInt());
        }
        Arrays.sort(arr);
        // We move over each starting point. At each one, we see if
        // there is an overflow of intervals covering it. Since we move
        // in increasing size order, we will be greedily removing the biggest
        // intervals, which is optimal.
//        System.out.println(Arrays.toString(arr));
        ArrayList<Integer> removed = new ArrayList<>();
        int i = 0;
        // sort only by right side
        TreeSet<Interval> set = new TreeSet<>(new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                if (o1.hi != o2.hi) {
                    return -(o1.hi - o2.hi);
                }
                else {
                    return o1.idx - o2.idx;
                }
            }
        });
        int j = 0;
        while (i < N) {
            while (j < N && arr[i].lo == arr[j].lo) {
                Interval interval = arr[j];
                set.add(interval);
                j++;
            }
            // Maintain invariant: the set will possess all intervals that
            // enclose the current position specified by arr[i].lo
            // This will include all the ones just added above (all things with
            // that same start location), as well as any that were from prior
            // additions that were long enough to reach here.
            // We must delete them to maintain this invariant
            while (set.size() > 0 && set.last().hi < arr[i].lo) {
                // Poll things that
                set.pollLast();
            }
            while (set.size() > K) {
                Interval lost = set.pollFirst(); // poll the biggest ones first
                removed.add(lost.idx);
            }
//            System.out.println(bit);
            i = j;
        }
        out.println(removed.size());
        for (int idx : removed) {
            out.print(idx + " ");
        }
        out.close();
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
            for (int i = 1; i < 30; i++) {
                sb.append(i + " ");
            }
            sb.append("\nValue: ");
            for (int i = 1; i < 30; i++) {
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

    static class Interval implements Comparable<Interval> {
        int lo, hi;
        int idx;
        public Interval(int index, int start, int end) {
            idx = index; lo = start; hi = end;
        }

        public String toString() {
            return idx + ": " + "(" + lo + ", " + hi + ")";
        }

        public int compareTo(Interval i2) {
            if (lo != i2.lo) {
                return lo - i2.lo;
            }
            else {
                return -(hi - i2.hi); // sort so longer ones come earlier. We want to delete THOSE
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