/**
 ID: d3rrickl
 LANG: JAVA
 PROG: 2016 USACO Open Gold #2
 */
import java.io.*;
import java.util.*;

public class ClosingTheFarm {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean testing) throws Exception {
        String prefix = testing ? "/Users/derrick/IntelliJProjects/src/USACO/" : "";
        sc = new FastScanner(prefix + problem_name + ".in");
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner();
        out = new PrintWriter(System.out);
    }

    /**
     * Recursive implementation is about twice as bad as regular,
     * sooo, NOPE
    static int[] id, sz;
    static int comps;

    static int find(int node) {
        return id[node] == node ? id[node] : (id[node] = find(id[node]));
    }

    static void union(int a, int b) {
        int p1 = find(a);
        int p2 = find(b);
        // Only decrease size if truly different components
        if (p1 != p2) {
            if (sz[p1] < sz[p2]) {
                int temp = p1;
                p1 = p2;
                p2 = temp;
            }
            id[p2] = p1; // join smaller to bigger
            sz[p1] += sz[p2]; // increase size
            comps--; // connected one component
        }
    }

     */

    static class UnionFind {
        // total number of nodes, number of components
        int size, components;
        // the parent of a node
        int[] id;
        // Size of the component
        int[] sz;

        public UnionFind(int total) {
            this.size = components = total;
            id = new int[size];
            sz = new int[size];

            for (int i = 0; i < size; i++) {
                // each node is singular, so it is its own root, and size = 1
                id[i] = i;
                sz[i] = 1;
            }
        }

        // Find the root of a node
        public int find(int node) {
            int root = node;
            // the root of the tree will have an id of itself,
            // so continue climbing up until we reach that point
            while (id[root] != root) {
                root = id[root];
            }

            // path compression: the idea is that, for each
            // of the nodes starting from the node climbing up to the root,
            // we want quick access to the proper parent at the top to save
            // time in the future
            while (node != root) {
                int curr = node;
                node = id[node]; // go up one level, then, after using this information, replace it with
                // the updated info acquired by the find process above
                id[curr] = root;
            }
            return root;
        }

        public void union(int node1, int node2) {
            // Shoot, big error here. Need to update everything up along its parent chain,
            // then we can union them. This ensures no unupdated nodes along the chain
            int root1 = find(node1);
            int root2 = find(node2);
            // They are already in the same component, so break
            if (root1 == root2) {
                return;
            }
            // by convention, merge the smaller into the bigger
            else if (sz[root1] < sz[root2]) {
                id[root1] = root2; // point root1 to root2
                sz[root2] += sz[root1];
                // don't worry about cascading information about
                // the root down the chain. The next time we call
                // find(something in group), it will convert everything in the chain
            }
            else {
                // root1 absorbs root2's component
                id[root2] = root1;
                sz[root1] += sz[root2];
                // interestingly, the size of the smaller component will never be accessed again,
                // since the roots are all fed into the larger component!
            }
            components--;
        }

        // if two nodes have the same, root, then their roots are the same
        public boolean connected(int node1, int node2) {
            return find(node1) == find(node2);
        }

        public int componentSize(int node) {
            return sz[find(node)];
        }

        public int size() {
            return this.size;
        }

        public int components() {
            return this.components;
        }
    }

    public static void main(String args[]) throws Exception {
//        setupIO();
      setupIO("closing", false);

        int N = sc.nextInt();
        int M = sc.nextInt();


        ArrayList<Integer>[] adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjList[u].add(v);
            adjList[v].add(u);
        }

        int[] queries = new int[N];
        for (int i = 0; i < N; i++) {
            queries[i] = sc.nextInt() - 1;
        }

        // create union find
//        id = new int[N];
//        sz = new int[N];
//        for (int i = 0; i < N; i++) {
//            id[i] = i;
//            sz[i] = 1;
//        }
//        comps = N;
        UnionFind dsu = new UnionFind(N);

        // Initialize in the reverse order
        HashSet<Integer> net = new HashSet<>();
        boolean[] answers = new boolean[N];
        // We are sweeping from 1 added to N added
        // They are asking, sweeping from 0 removed (N added) to N - 1 removed (1 added)

        for (int i = N - 1; i >= 0; i--) {
            int u = queries[i];
            for (int v : adjList[u]) {
                if (net.contains(v)) {
                    dsu.union(u, v);
                }
            }
            net.add(u);

            // After adding this one, is it the correct number of components?

//            System.out.println(comps);
            if (dsu.components <= N - net.size() + 1) {
                assert dsu.components == N - net.size() + 1 : "Comp size is impossible";
                answers[i] = true;
            }
        }

        for (int i = 0; i < N; i++) {
            out.println(answers[i] ? "YES" : "NO");
        }

        out.close();
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