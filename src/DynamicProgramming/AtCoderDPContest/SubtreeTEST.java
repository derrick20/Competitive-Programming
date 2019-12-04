import java.io.*;
import java.util.*;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */

/*
4 1009
1 2
1 3
2 4
//3 5
//3 6

15
12
16
7
9
9
 */
public class SubtreeTEST {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastScanner in = new FastScanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        V_Subtree solver = new V_Subtree();
        solver.solve(1, in, out);
        out.close();
    }

    static class V_Subtree {
        int n;
        int m;
        List<GraphUtil.Edge>[] g;
        Node[] tree;

        void dfs1(int v) {
            tree[v].down = 1;
            for (GraphUtil.Edge edge : g[v]) {
                int u = edge.to;
                if (tree[u] == null) {
                    tree[u] = new Node(u, v);
                    tree[v].children.add(tree[u]);
                    dfs1(u);
                    tree[v].down = tree[v].down * (tree[u].down + 1) % m;
                }
            }
        }

        void dfs2(int v) {
            if (tree[v].p != -1) {
                Node node = tree[v];
                Node parent = tree[node.p];
                node.up = (parent.up + 1) * node.up1 % m;
            }
            for (Node child : tree[v].children) {
                dfs2(child.id);
            }
        }

        public void solve(int testNumber, FastScanner in, PrintWriter out) {
            n = in.ni();
            m = in.ni();
            g = in.readEdgesGraph(n, n - 1, true);
            tree = new Node[n];
            tree[0] = new Node(0, -1);

            dfs1(0);
            for (int i = 0; i < n; i++) {
                Node node = tree[i];
                if (node.children.size() != 0) {
                    long[] pref = new long[node.children.size() + 1];
                    pref[0] = 1;
                    int j = 1;
                    for (Node child : node.children) {
                        pref[j] = pref[j - 1] * (child.down + 1) % m;
                        j++;
                    }
                    ListIterator<Node> li = node.children.listIterator(node.children.size());
                    long suf = 1;
                    j = node.children.size() - 1;
                    while (li.hasPrevious()) {
                        Node child = li.previous();
                        child.up1 = pref[j] * suf % m;
                        suf = suf * (child.down + 1) % m;
                        j--;
                    }

                }
            }
            dfs2(0);
            for (int i = 0; i < n; i++) {
                out.println((tree[i].down * (tree[i].up + 1)) % m);
            }

        }

        static class Node {
            int id;
            int p;
            List<Node> children;
            long down;
            long up;
            long up1;

            public Node(int id, int p) {
                this.id = id;
                this.p = p;
                children = new LinkedList<>();
            }

        }

    }

    static class FastScanner {
        private BufferedReader in;
        private StringTokenizer st;

        public FastScanner(InputStream stream) {
            in = new BufferedReader(new InputStreamReader(stream));
        }

        public String ns() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    String rl = in.readLine();
                    if (rl == null) {
                        return null;
                    }
                    st = new StringTokenizer(rl);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        public int ni() {
            return Integer.parseInt(ns());
        }

        public List<GraphUtil.Edge>[] readEdgesGraph(int n, int m, boolean bidirected) {
            List<GraphUtil.Edge>[] g = new List[n];
            for (int i = 0; i < n; i++) {
                g[i] = new LinkedList<>();
            }
            for (int i = 0; i < m; i++) {
                int u = ni() - 1;
                int v = ni() - 1;
                g[u].add(new GraphUtil.Edge(u, v, i));
                if (bidirected)
                    g[v].add(new GraphUtil.Edge(v, u, i));
            }

            return g;
        }

    }

    static class GraphUtil {
        public static class Edge {
            public int from;
            public int to;
            public int id;
            public long w;

            public Edge(int from, int to) {
                this.from = from;
                this.to = to;
            }

            public Edge(int from, int to, int id) {
                this.from = from;
                this.to = to;
                this.id = id;
            }

            public Edge(int from, int to, int id, long w) {
                this.from = from;
                this.to = to;
                this.id = id;
                this.w = w;
            }

        }

    }
}