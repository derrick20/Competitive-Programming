/**
 * @author derrick20
 */
/*
5 7 4
2 3
4 1
5 4
3 5
1 2 3 4 5
2 1 -1
2 2 2
1 1
1 2
1 3
1 4
1 5

7 10 1
1 2
1 3
2 4
2 5
3 6
3 7
0 0 0 0 0 0 0
2 3 1
2 2 -1
2 4 5
1 1
1 2
1 3
1 4
1 5
1 6
1 7


 */
import java.io.*;
import java.util.*;

public class MarioExploration {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int Q = sc.nextInt();

        int root = sc.nextInt() - 1;
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjList[u].add(v);
            adjList[v].add(u);
        }

        val = new int[N];
        for (int i = 0; i < N; i++) {
            val[i] = sc.nextInt();
        }

        inTime = new int[N];
        outTime = new int[N];
        time = 1;
        dfs(root, -1);

        int[] flattened = new int[2 * N + 1];
        // The flattened tree will be able to query subtrees
        // by checking the range from intime to outtime of a node
        // It will use a bit too add all the values stored at
        // the entrance time positions within the flattened tree
        for (int i = 0; i < N; i++) {
            int lo = inTime[i];
            int hi = outTime[i];
            flattened[lo] = val[i];
            flattened[hi] = val[i];
            // let the lower part store the values
        }

        BIT bit = new BIT(flattened);
//        SegmentTree segTree = new SegmentTree(flattened);


        while (Q-->0) {
            int type = sc.nextInt();
            int room = sc.nextInt() - 1;
//            System.out.println(bit);
            if (type == 1) {
                long sum = bit.sum(inTime[room], outTime[room]) / 2;
                long ans = sum % mod;
                while (ans < 0) {
                    ans += mod;
                }
                out.println(ans);
            }
            else if (type == 2) {
                // Update that subtree
                int brightness = sc.nextInt();
                // Maybe negative issue?
                long added = (brightness * (outTime[room] - inTime[room] + 1 + 1));
                bit.add(inTime[room], added);
                bit.add(outTime[room], -brightness);
            }
        }

        out.close();
    }

    static int[] inTime, outTime;
    static int[] val;
    static ArrayList<Integer>[] adjList;
    static int time;
    static long mod = (long) 1e9 + 7;

    static void dfs(int node, int par) {
        inTime[node] = time++;
        for (int adj : adjList[node]) {
            if (adj != par) {
                dfs(adj, node);
            }
        }
        outTime[node] = time++;
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
        public void add(int i, long val) {
            while (i < tree.length) {
                tree[i] += val;
                i += leastSignificantBit(i);
            }
        }

        // Change a value at an index (basically add the new value and subtract
        // the original value
        public void set(int i, int k) {
            long val = sum(i, i);
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