/*
 * @author derrick20
 */

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class subway {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();
        for (int c = 0; c < C; c++) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            PriorityQueue<Edge> pq = new PriorityQueue<>();
            UnionFind unionFind = new UnionFind(N);
            for (int i = 0; i < M; i++) {
                pq.add(new Edge(sc.nextInt(), sc.nextInt(), sc.nextDouble()));
            }
            double cost = 0;
            while (unionFind.numComponents > 1) {
                Edge e = pq.poll();
                if (!unionFind.connected(e.u, e.v)) {
                    unionFind.unify(e.u, e.v);
                    cost += e.wt;
                }
            }
            int ans = (int) (cost * 100);
//            out.println((double) ans / 100.0);
            DecimalFormat df = new DecimalFormat("0.00");
            out.println(df.format(cost));
        }
        out.close();
    }

    static class Edge implements Comparable<Edge> {
        double wt;
        int u, v;

        public Edge(int u, int v, double wt) {
            this.u = u;
            this.v = v;
            this.wt = wt;
        }

        public int compareTo(Edge e) {
            return (int) (1000 * (this.wt - e.wt));
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
