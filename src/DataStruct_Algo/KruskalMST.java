/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class KruskalMST {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name) throws Exception {
        sc = new FastScanner(new FileReader(problem_name + ".in"));
        out = new PrintWriter(new FileWriter(problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner(System.in);
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {

        int N = sc.nextInt();
        int[] cows = new int[N];
        for (int i = 0; i < cows.length; i++) {
            cows[i] = sc.nextInt();
        }

        PriorityQueue<Edge> edges = new PriorityQueue<>();
        // Make the graph of the possible connections represented by the matches
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                edges.add(new Edge(i, j, cows[i] ^ cows[j]));
            }
        }
        long totalCost = Kruskals(N, edges);
        out.println(totalCost);
        out.close();
    }

    // Return the min or MAX cost of a spanning TREE
    static long Kruskals(int N, PriorityQueue<Edge> edges) {
        UnionFind unionFind = new UnionFind(N);
        long totalCost = 0;
        // Start with N components, all separate.
        // While we haven't formed a single connected component,
        // keep adding edges
        while (unionFind.numComponents > 1) {
            Edge e = edges.poll();
            if (!unionFind.connected(e.u, e.v)) {
                // Only add the edges if they are on separate
                // components currently.
                unionFind.unify(e.u, e.v);
                // Store the costs as you go along
                totalCost += e.wt;
            }
        }
        return totalCost;
    }

    static class Edge implements Comparable<Edge> {
        int wt;
        int u, v;

        public Edge(int u, int v, int wt) {
            this.u = u;
            this.v = v;
            this.wt = wt;
        }

        public int compareTo(Edge e) {
            return - (this.wt - e.wt);
        }
    }

    static class UnionFind {

        private int size;
        private int[] sz;
        private int[] id;
        private int numComponents;

        public UnionFind(int size) {

            if (size <= 0) throw new IllegalArgumentException("Size <= 0 is not allowed");

            this.size = numComponents = size;
            sz = new int[size];
            id = new int[size];

            for (int i = 0; i < size; i++) {
                id[i] = i; // Link to itself (self root)
                sz[i] = 1; // Each component is originally of size one
            }
        }

        public int find(int p) {

            // Find the root of the component/set
            int root = p;
            while (root != id[root]) root = id[root];

            // Compress the path leading back to the root.
            // Doing this operation is called "path compression"
            // and is what gives us amortized time complexity.
            while (p != root) {
                int next = id[p];
                id[p] = root;
                p = next;
            }

            return root;
        }
        // Return whether or not the elements 'p' and
        // 'q' are in the same components/set.
        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        // Return the size of the components/set 'p' belongs to
        public int componentSize(int p) {
            return sz[find(p)];
        }

        // Return the number of elements in this UnionFind/Disjoint set
        public int size() {
            return size;
        }

        // Returns the number of remaining components/sets
        public int components() {
            return numComponents;
        }

        // Unify the components/sets containing elements 'p' and 'q'
        public void unify(int p, int q) {

            int root1 = find(p);
            int root2 = find(q);

            // These elements are already in the same group!
            if (root1 == root2) return;

            // Merge smaller component/set into the larger one.
            if (sz[root1] < sz[root2]) {
                sz[root2] += sz[root1];
                id[root1] = root2;
            } else {
                sz[root1] += sz[root2];
                id[root2] = root1;
            }

            // Since the roots found are different we know that the
            // number of components/sets has decreased by one
            numComponents--;
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
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException {
            return br.readLine();
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
