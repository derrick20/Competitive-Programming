/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class xenia {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        int Q = sc.nextInt();
        adjList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            adjList.add(new HashSet<>());
        }

        for (int i = 0; i < N - 1; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            adjList.get(u).add(v);
            adjList.get(v).add(u);
        }

        setupLCA();
        // An important thing is that, once we've finished setting up LCA
        // we won't need adjList again. =Thus, we can decompose the tree and
        // repeatedly delete nodes safely!

        subTreeSize = new int[N];
        // A reorganized version of the original tree graoh
        centroidParent = new int[N];

        // Decompose the tree!
        buildCentroidTree(0, - 1);

        distToRed = new int[N];
        Arrays.fill(distToRed, oo);
        // Node 0 always starts out red!
        update(0);

        while (Q-->0) {
            int type = sc.nextInt();
            int node = sc.nextInt() - 1;
            if (type == 1) {
                update(node);
            }
            else if (type == 2) {
                out.println(query(node));
            }
        }
        out.close();
    }
    // Once we have all the LCA and Centroid Tree made, we can start
    // doing queries efficiently:
    static int oo = (int) 1e9;
    // this array stores the shortest distance to a red node
    // WITHIN A CERTAIN NODE'S SUBTREE in the centroid tree
    static int[] distToRed;

    // log N time of going higher and higher, and each time calculating distance
    // using LCA for another log N factor. Total of O(log^2 N)
    static int query(int node) {
        int ans = distToRed[node];
        // repeatedly try scoping out a red node from a higher vantage point
        // and see what the best was
        int par = centroidParent[node];
        boolean finished = false;
        while (!finished) {
            // We offer a higher vantage point of a path to see if any improvement possible
            ans = Math.min(ans, distToRed[par] + distance(node, par));
            if (par == centroidParent[par]) {
                // Once we've tried using all parents from bottom to top, curr crashes into parent
                // and we have to stop
                finished = true;
            }
            par = centroidParent[par];
        }
        return ans;
    }

    // Update a node to become red. This means our new distance is 0
    // We must propagate info upward, so O(log^2 N) for same reason as query
    static void update(int node) {
        distToRed[node] = 0;
        int par = centroidParent[node];
        boolean finished = false;
        while (!finished) {
            // Our parent may have a quick path to a red, but let's offer it our path,
            // and see if that PLUS the distance of us to the parent is smaller!
            distToRed[par] = Math.min(distToRed[par], distance(node, par));
            if (par == centroidParent[par]) {
                // Once we've tried using all parents from bottom to top, curr crashes into parent
                // and we have to stop
                finished = true;
            }
            par = centroidParent[par];
        }
    }


    static ArrayList<HashSet<Integer>> adjList;
    static int N;
    static int maxDepth;
    static int[] depth;
    // parent takes the exponent for the binary lift and the child node as the arguments
    static int[][] parent;
    // the immediate parents of each node, for initializing of base cases for parent[][]
    static int[] firstParents;

    static int[] centroidParent;
    static int[] subTreeSize;


    static void buildCentroidTree(int node, int par) {
        // Do a dfs to find the sizes of everything
        // inside the current tree component (which may be disconnected from
        // the other parts of the forest!)
        int subSize = findSubSize(node, par);
        int centroid = getCentroid(node, par, subSize);

        // once we've found the centroid, set its parent
        // and delete it. Recursively build the subtrees of it
        // If it's -1 though, it is its own parent
        centroidParent[centroid] = par == -1 ? centroid : par;

        HashSet<Integer> neighbors = new HashSet<>(adjList.get(centroid));
        for (int v : neighbors) {
            // all things connecting the centroid to other
            // parts of the tree are now deleted
            adjList.get(centroid).remove(v);
            adjList.get(v).remove(centroid);
            // each of those sub branches must be constructed as well
            // with the centroid as the parent of them
            buildCentroidTree(v, centroid);
        }
    }

    // Keep wandering around the tree until we get to the point
    // where have a branch that is <= half the sub tree size
    // therefore this is a good split (No subtree > half size)
    static int getCentroid(int node, int par, int subSize) {
        // Woah this is crazy:
        // By the pigeonhole principle exactly one branch may have this
        // imbalanced subtree size, so it is beneficial to go to that node
        // and cut down on this big tree. It is guaranteed the subtree we leave
        // behind must still be less than n/2, since we are currently bigger than n/2!
        // We should never have to go back on it, since it will only
        for (int adj : adjList.get(node)) {
            if (adj != par && subTreeSize[adj] > subSize / 2) {
                return getCentroid(adj, node, subSize);
            }
        }
        // this is just a case that'll never happen if it's a proper tree!
        return node;
    }

    // Since tree, no visited array needed
    static int findSubSize(int node, int par) {
        int size = 1; // itself is 1 node at least
        // then, add all of its subtree sizes to it
        for (int adj : adjList.get(node)) {
            if (adj != par) {
                size += findSubSize(adj, node);
            }
        }
        return subTreeSize[node] = size;
    }

    /*
    todo CRUCIAL NOTE: THIS IS SET UP WITH BEGINNING AT NODE 0
    todo DO NOT MESS THAT UP WHEN READING IN STUFF
     */

    static void setupLCA() {
        maxDepth = 0;
        depth = new int[N]; // store the depth of each node
        firstParents = new int[N]; // the immediate parent
        dfs(0);
        int power = 1;
        while (1 << power <= maxDepth) {
            power++;
        }
        parent = new int[power][N];
        for (int node = 0; node < N; node++) {
            parent[0][node] = firstParents[node];
        }
        // Necessary to set everything else to zero for now, so
        // that we never get confused if we are out of bounds

        // Must fill it up with -1's
        for (int p = 1; p < parent.length; p++) {
            for (int i = 0; i < N; i++) {
                parent[p][i] = -1;
            }
        }
        // p represents going up by 1 << p
        for (int p = 1; p < parent.length; p++) {
            for (int node = 0; node < N; node++) {
                if (parent[p - 1][node] != -1) {
                    int myParent = parent[p - 1][node];
                    parent[p][node] = parent[p-1][myParent];
                }
            }
        }
    }

    // no visited array needed since it's a tree
    static void dfs(int node) {
        for (int adj : adjList.get(node)) {
            if (adj != firstParents[node]) {
                firstParents[adj] = node;
                depth[adj] = depth[node] + 1;
                maxDepth = Math.max(maxDepth, depth[adj]);
                dfs(adj);
            }
        }
    }

    static int leastCommonAncestor(int a, int b) {
        if (depth[b] > depth[a]) {
            // make the bigger depth be node a
            int c = a;
            a = b;
            b = c;
        }
        int dist = depth[a] - depth[b];
        while (dist > 0) {
            // level out the depths
            int power = (int) (Math.log(dist) / Math.log(2));
            a = parent[power][a];
            dist -= 1 << power;
        }
        if (a == b) {
            // return early
            return a;
        }
        for (int j = parent.length - 1; j >= 0; j--) {
            if (parent[j][a] != parent[j][b]) {
                // Jump until right below lca
                a = parent[j][a];
                b = parent[j][b];
            }
        }
        return parent[0][a];
    }

    static int distance(int a, int b) {
        return depth[a] + depth[b] - 2 * depth[leastCommonAncestor(a, b)];
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
