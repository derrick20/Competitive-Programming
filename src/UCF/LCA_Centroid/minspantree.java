/*
 * @author derrick20
 * One stupid bug. DON"T USE ARRAYS.FILL SINCE IT DOES THE SAME OBJECT FOR EVERYTHING
 * Other bugs:
 * Need to use longs since adding bunch of 10^9 integers
 * Wasn't consistently using the parent[2^power][node] convention, reversed it in the lca method
 * Other bug, wasn't filling out the parent array of 0 with default values of -1
 * Another bug was that the union find was messed up. I was updating the node
 * values for size and id, when in reality i should've been updating the ROOTS
 * Keep in mind,the idea behind union find is to keep getting rid of extraneous components,
 * eventually shaving things down to just the roots
 * Be careful when mixing different
 */
import java.io.*;
import java.util.*;

public class minspantree {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        adjList = new ArrayList[N];
        PriorityQueue<Edge> edges = new PriorityQueue<>();
        ArrayList<Edge> edgesCopy = new ArrayList<>();
        Arrays.fill(adjList, new ArrayList<>());

//        times = new int[2 * N];
//        inTime = new int[N];
//        outTime = new int[N];
//        time = 0;
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int wt = sc.nextInt();
            adjList[u].add(v);
            adjList[v].add(u);
            Edge e = new Edge(u, v, wt);
            edges.add(e);
            edgesCopy.add(e);
        }

        long total = 0;
        UnionFind unionFind = new UnionFind(N);

        adjList2 = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList2[i] = new ArrayList<>();
        }
//        value = new int[N];
        while (unionFind.components > 1 && !edges.isEmpty()) {
            Edge e = edges.poll();
            if (!unionFind.isConnected(e.u, e.v)) {
                unionFind.union(e.u, e.v);
                total += e.wt;
                // represent each node as potentially being the child
                // within some larger tree, but that is to be decided later!
                // (Within the dfs, if we are coming down upon a node,
                // it takes the weight associated with the connection from
                // the current node INTO it.
                adjList2[e.u].add(new Child(e.v, e.wt));
                adjList2[e.v].add(new Child(e.u, e.wt));
//                System.out.println(e.u + " " + e.v);
//                for (int i = 0; i < adjList2.length; i++) {
//                    System.out.println(adjList2[i]);
//                }
            }
        }

//        for (int from = 0; from < N; from++) {
//            System.out.println(adjList2[from]);
//        }
//        eulerTour(0);
//        // arbitrarily root it at node 0
//        for (int node = 0; node < N; node++) {
//            times[inTime[node]] = node;
//            times[outTime[node]] = node;
//        }

        visited = new boolean[N];
        depth = new int[N];
        value = new int[N];
        maxDepth = 0;

        firstParents = new int[N];
        firstMax = new int[N];
        // The 0th node will have 0 weight
        dfs(0, 0, -1, 0);
        int power = 1;
        // Increase the power higher until we go over the maxDepth
        // This will be one more than we need
        while (1 << power <= maxDepth) {
            power++;
        }
        parent = new int[power][N]; // go up 2^power from node i of N
        maxWeights = new int[power][N];
        // Now, use that initial information acquired from the dfs to build
        // base cases of the sparse tables!
        for (int node = 0; node < N; node++) {
            parent[0][node] = firstParents[node];
            maxWeights[0][node] = firstMax[node];
        }
        parent[0][0] = -1;
        for (int j = 1; j < parent.length; j++) {
            for (int i = 0; i < N; i++) {
                parent[j][i] = -1;
            }
        }

        for (int p = 1; p < parent.length; p++) {
            for (int node = 0; node < N; node++) {
                if (parent[p - 1][node] != -1) {
                    int myParent = parent[p - 1][node];
                    int myParentMax = maxWeights[p - 1][node];
                    parent[p][node] = parent[p-1][myParent];
                    maxWeights[p][node] = Math.max(myParentMax, maxWeights[p - 1][myParent]);
                }
            }
        }
//        System.out.println(maxDepth);
//        for (int i = 0; i < maxWeights.length; i++) {
//            for (int j = 0; j < maxWeights[0].length; j++) {
//                System.out.print(maxWeights[i][j] + " ");
//            }
//            System.out.println();
//        }
        // go through the edges listed in the input as we saw them
//        System.out.println(edgesCopy);
        for (Edge e : edgesCopy) {
            int u = e.u;
            int v = e.v;
//            System.out.println(u + " " + v);
            int lca = leastCommonAncestor(u, v);
            int max1 = maxWeight(u, lca);
//            System.out.println(u + " " + lca + " " + "max1 = " + max1);
            int max2 = maxWeight(v, lca);
//            System.out.println(v + " " + lca + " " + "max2 = " + max2);
            // the better of the two paths is the amount of weight we want to
            // remove, (since we are forced to add in the extra edge as
            // stipulated by the input.
            int lostEdge = Math.max(max1, max2);
//                System.out.println(lostEdge + " " + e.wt);
//                System.out.println();
            out.println(total - lostEdge + e.wt);
        }
        out.close();
    }

    static int maxDepth;
    static int[] value;
    static int[] depth;
    static boolean[] visited;
    static int[][] parent;
    static int[][] maxWeights;

    static int[] firstParents;
    static int[] firstMax;

    static ArrayList<Integer>[] adjList;
    static ArrayList<Child>[] adjList2;

//    public static String displayTree(int node, int par, int wt, int level) {
//        String toRet = "";
//        if (adjList2[node].size() == 0)
//            return node + ": " + wt;
//        for (int i = 0; i < adjList2[node].size(); i++) {
//            Child adj = adjList2[node].get(i);
//            toRet += displayTree(adj.v, node, adj.wt,level + 1);
//            if (i == adjList2[node].size() / 2) {
//                for (int k = 0; k < level; k++) {
//                    toRet += "\t\t";
//                }
//                toRet += node + ": " + wt;
//            }
//        }
//        return toRet;
//    }

    static int maxWeight(int node, int anc) {
        int max = 0;
        for (int j = parent.length - 1; j >= 0; j--) {
            // no edges to delete if they're equal
            // this is equivalent to saying node == anc, since it's a tree
            if (depth[node] == depth[anc]) {
//                System.out.println("NODE: " + node + " ANC: " + anc + " " + depth[node]  + " " + depth[anc]);
                break;
            }
            // if we are allowed to go that much higher, and it doesn't overshoot us
            // jump 2^j nodes up. Simultaneously, keep track of the max value
            // along the nodes we jumped over, using the query binary lifting table
            if (parent[j][node] != -1 && depth[parent[j][node]] >= depth[anc]) {
                max = Math.max(max, maxWeights[j][node]);
                node = parent[j][node];
            }
        }
        return max;
    }

    static int leastCommonAncestor(int a, int b) {
        if (depth[b] > depth[a]) {
            int c = a;
            a = b;
            b = c;
        }
        // We assume that a is farther down, so that's why we swap
        // Now, keep increasing the level of a by using sparse table. Try using the
        // biggest jumps possible, then exponentially refine it
        // This is just a more complex way of representing it as a binary number

//        int dist = level[a] - level[b];
//        while (dist > 0) {
//            int power = (int) (Math.log(dist) / Math.log(2));
//            a = parent[a][power];
//            dist -= 1 << power;
//        }

        for (int j = parent.length - 1; j >= 0; j--) {
            // Once we are finally the same level, exit
            if (depth[a] == depth[b]) {
                break;
            }
//            if (level[parent[a][j]] < level[b]) { // then we went too far
//                continue;
//            }
            // if we didn't overshoot, then update us
            // FATAL BUG, the -1 problem. Basically this could be overcome with the above commented
            // method, which makes sense since it goes right to the amount we need to jump, rather than trying all
            // jump sizes
            if (parent[j][a] != -1) {
                if (depth[parent[j][a]] >= depth[b]) {
                    a = parent[j][a];
                }
            }
        }
        // Sometimes, we'll be at the same level AND be the same, so return early
        // (the other statement will be wrong since it'll return our parent, when we were already the same
        if (a == b) {
            return a;
        }

        for (int j = parent.length - 1; j >= 0; j--) {
            // We want to make sure that we keep going while they're parents are different.
            // This process will take us right to here (since we can always represent
            // any number in binary, just subtract 1):
            //  lca
            //  / \
            // a   b
            if (parent[j][a] != parent[j][b]) {
                a = parent[j][a];
                b = parent[j][b];
            }
        }
        return parent[0][a];
    }

    static void dfs(int node, int wt, int par, int d) {
        if (!visited[node]) {
            visited[node] = true;
            value[node] = wt;
            depth[node] = d;
            firstParents[node] = par; // this node has a parent of the mother
            firstMax[node] = wt;
            // this arbitrarily roots the tree
            maxDepth = Math.max(maxDepth, depth[node]);
            for (Child adj : adjList2[node]) {
                if (adj.v != par) {
                    dfs(adj.v, adj.wt, node, d + 1);
                }
            }
        }
    }

//    static int[] times, inTime, outTime;
//    static int time;
//    static void eulerTour(int node) {
//        inTime[node] = time++;
//        for (int adj : adjList2[node]) {
//            eulerTour(adj);
//        }
//        outTime[node] = time++;
//    }

    static class Child {
        int v, wt;

        public Child(int vv, int weight) {
            v = vv;
            wt = weight;
        }

        public String toString() {
            return "(" + v + ": " + wt + ")";
        }
    }

    static class Edge implements Comparable<Edge> {
        int u, v;
        int wt;

        public Edge(int uu, int vv, int weight) {
            u = uu;
            v = vv;
            wt = weight;
        }

        public String toString() {
            return "(" + u + ", " + v + ")" + ": " + wt;
        }

        public int compareTo(Edge e2) {
            return this.wt - e2.wt;
        }
    }

    static class UnionFind {
         int size;
         int components;
         int[] sz;
         int[] id;

         public UnionFind(int size) {
             this.size = size;
             sz = new int[size];
             id = new int[size];
             components = size;

             for (int i = 0; i < size; i++) {
                 sz[i] = 1;
                 id[i] = i;
             }
         }

         public int find(int node) {
             // Path compression idea:
             // let's do a little extra work here to fix everyone's
             // root in the path
             int root = node;
             while (id[root] != root) {
                 root = id[root];
             }

             // now, we go through everything fromm node to root, and update
             // their roots properly
             while (node != root) {
                 int next = id[node];
                 id[node] = root;
                 node = next;
             }
             return root;
         }

         public boolean isConnected(int node1, int node2) {
             return find(node1) == find(node2);
         }

         public void union(int node1, int node2) {
             // Bug
             int root1 = find(node1);
             int root2 = find(node2);
             // don't care if they were already unified
             if (root1 != root2) {
                 if (sz[root1] > sz[root2]) {
                     sz[root1] += sz[root2];
                     // no need to update node2 size/information anymore
                     id[root2] = root1;
                 }
                 else {
                     sz[root2] += sz[root1];
                     id[root1] = root2;
                 }
                 components--;
             }

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

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
