/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class PedigreeCounting {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
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
        inTime = new int[N];
        outTime = new int[N];
        time = 1; // 1-indexed BIT
        dfs(root, -1);
        color = new int[N];
        for (int i = 0; i < N; i++) {
            color[i] = sc.nextInt();
        }
        int[] flattenedTree = new int[2 * N + 1];
        for (int i = 0; i < N; i++) {
            flattenedTree[inTime[i]] = color[i];
            flattenedTree[outTime[i]] = color[i];
            // Put in all the colors, so for a given time point, we know what color is there
        }

        BIT bit = new BIT(new int[2 * N + 1]);

        Query[] queries = new Query[Q];
        for (int i = 0; i < Q; i++) {
            queries[i] = new Query(i, sc.nextInt() - 1);
        }
        Arrays.sort(queries);

        HashMap<Integer, Integer> solutionBag = new HashMap<>();
        int[] answers = new int[Q];
        int ptr = 1;
        for (Query q : queries) {
            int node = q.node;
            int idx = q.idx;
            // Keep maximizing the right endpoint of the color's position
            while (ptr <= outTime[node]) {
                int colorHere = flattenedTree[ptr];
                if (solutionBag.containsKey(colorHere)) {
                    // Need to clean out our structure to have the right numbers for greedily
                    // querying intervals
                    int prev = solutionBag.get(colorHere);
                    bit.add(prev, -1);
                    bit.add(ptr, 1);
                    solutionBag.put(colorHere, ptr);
                } else {
                    // Just update that position to now have something of that color
                    bit.add(ptr, 1);
                    solutionBag.put(colorHere, ptr);
                }
                ptr++;
            }
            answers[idx] = bit.sum(inTime[node], outTime[node]);
        }
        for (int ans : answers) {
            out.println(ans);
        }
        out.close();
    }

    static int N;
    static ArrayList<Integer>[] adjList;
    static int[] inTime, outTime, color;
    static int time;

    static void dfs(int node, int par) {
        inTime[node] = time++;
        for (int adj : adjList[node]) {
            if (adj != par) {
                dfs(adj, node);
            }
        }
        outTime[node] = time++;
    }

    static class Query implements Comparable<Query> {
        int idx, node;
        public Query(int index, int myNode) {
            idx = index;
            node = myNode;
        }
        public int compareTo(Query q2) {
            // Sort by right end, so that we progressively build on a subproblem
            // of so far completed/processed nodes

            // I thought about then comparing by left endpoint, but these are
            // strict intervals so all unique endpoints!
            return outTime[node] - outTime[q2.node];
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