/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class mootube {

    public static void main(String[] args) throws Exception {
//        Scanner sc = new Scanner(System.in);
//        PrintWriter out = new PrintWriter(System.out);
        Scanner sc = new Scanner(new FileReader("mootube.in"));
        PrintWriter out = new PrintWriter(new FileWriter("mootube.out"));

        int N = sc.nextInt();
        int Q = sc.nextInt();

        PriorityQueue<Edge> edgeList = new PriorityQueue<>();
        for (int i = 0; i < N - 1; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int relevance = sc.nextInt();
            Edge e = new Edge(u, v, relevance);
            edgeList.add(e);
        }

        ArrayList<Query> queries = new ArrayList<>();
        for (int i = 0; i < Q; i++) {
            int k = sc.nextInt();
            int v = sc.nextInt() - 1;
            queries.add(new Query(i, k, v));
        }
        Collections.sort(queries);

        // Make a structure for all nodes in the graph
        UnionFind unionFind = new UnionFind(N);
        int[] answers = new int[Q];
        for (Query q : queries) {
            int index = q.i;
            int minRelevance = q.k;
            int videoID = q.v;
            // If we can add an
            while (edgeList.size() > 0 && edgeList.peek().relevance >= minRelevance) {
                Edge e = edgeList.poll();
                // Put these nodes into a connected component!
                unionFind.unify(e.u, e.v);
            }

            // At the end of it all, we want to see, after all valid edges
            // (which would be relevant enough, as they exceed the goal relevance)
            // how many are reachable from the video of interest
            // Must subtract 1 since we don't include ourselves as a reachable vertex!
            answers[index] = unionFind.componentSize(videoID) - 1;
        }

        for (int ans : answers) {
            out.println(ans);
        }
        out.close();
    }

    static class Query implements Comparable<Query> {
        int i, k, v;

        public Query(int ii, int kk, int vv) {
            i = ii;
            k = kk;
            v = vv;
        }

        public int compareTo(Query q2) {
            // We want bigger k values to come first
            // We can gradually build up information
            // on subproblems if we do it in this order
            return - (k - q2.k);
        }
    }

    static class Edge implements Comparable<Edge> {
        int relevance;
        int u, v;

        public Edge(int u, int v, int wt) {
            this.u = u;
            this.v = v;
            this.relevance = wt;
        }

        public int compareTo(Edge e) {
            // We will make it so bigger relevances go first
            return - (relevance - e.relevance);
        }
    }

    static class UnionFind {

        int size;
        int[] sz;
        int[] id;
        int numComponents;

        public UnionFind(int size) {
            this.size = numComponents = size;
            sz = new int[size];
            id = new int[size];

            for (int i = 0; i < size; i++) {
                id[i] = i; // Link to itself (self root)
                sz[i] = 1; // Each component is originally of size one
            }
        }

        public int find(int node) {
            // First, we find it's root (preserving the original value of node though)
            int root = node;
            while (root != id[root]) {
                root = id[root];
            }

            // Climb up and perform path compression on everything along the way
            while (node != root) {
                int parent = id[node];
                // set its correct id
                id[node] = root;
                node = parent;
            }

            return root;
        }

        public void unify(int node1, int node2) {
            // Make it so that node1 is the bigger component
            if (sz[node1] < sz[node2]) {
                int temp = node2;
                node2 = node1;
                node1 = temp;
            }
            int root1 = find(node1);
            int root2 = find(node2);

            if (root1 != root2) {
                // Make the smaller component point into the bigger component
                id[root2] = root1;
                // Update the size of the component
                sz[root1] += sz[root2];
                // Lower the total count of components
                numComponents--;
            }
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