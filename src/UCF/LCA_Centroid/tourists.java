/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class tourists {

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
//        Scanner sc = new Scanner(new FileReader("haybales.in"));//
//        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("haybales.out")));

        // everything must go a bit beyond
        // In this case, we need to start from 1, otherwise more work later
        // so, everything will be N+1, and just never use node 0
        N = sc.nextInt() + 1;
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < N - 1 - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adjList[u].add(v);
            adjList[v].add(u);
        }

        // Bunch of set up for LCA
        setupLCA();

        long ans = 0;
        // O(N log N) by approximating Riemann sum of 1/x as log N, then the N
        // which scales it up. Then, log N for each lca, so O(N log^2 N) overall!
        for (int i = 1; i < N; i++) {
            for (int j = i*2; j < N; j += i) {
                int lca = leastCommonAncestor(i, j);
                int path1 = depth[i] - depth[lca];
                int path2 = depth[j] - depth[lca];
                ans += path1 + path2 + 1; // +1 for the ancestor itself
            }
        }
        out.println(ans);
        out.close();
    }

    static int N;
    static int maxDepth;
    static int[] depth;
    static int[][] parent;
    static int[] firstParents;
    static ArrayList<Integer>[] adjList;

    static void setupLCA() {
        maxDepth = 0;
        depth = new int[N]; // store the depth of each node
        firstParents = new int[N]; // the immediate parent
        dfs(1, -1, 0);
        int power = 1;
        // Increase the power higher until we go over the maxDepth
        // This will be one more than we need
        while (1 << power <= maxDepth) {
            power++;
        }
        parent = new int[power][N]; // go up 2^power from node i of N
        // Now, use that initial information acquired from the dfs to build
        // base cases of the sparse tables!
        for (int node = 1; node < N; node++) {
            parent[0][node] = firstParents[node];
        }
        // Necessary to set everything else to zero for now, so
        // that we never get confused if we are out of bounds

        for (int p = 1; p < parent.length; p++) {
            for (int i = 1; i < N; i++) {
                parent[p][i] = -1;
            }
        }
        for (int p = 1; p < parent.length; p++) {
            for (int node = 1; node < N; node++) {
                if (parent[p - 1][node] != -1) {
                    int myParent = parent[p - 1][node];
                    parent[p][node] = parent[p-1][myParent];
                }
            }
        }
    }

    // no visited array needed since it's a tree
    static void dfs(int node, int par, int d) {
        depth[node] = d;
        firstParents[node] = par;
        maxDepth = Math.max(maxDepth, d);
        for (int adj : adjList[node]) {
            if (adj != par) {
                dfs(adj, node, d + 1);
            }
        }
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

        int dist = depth[a] - depth[b];
        while (dist > 0) {
            int power = (int) (Math.log(dist) / Math.log(2));
            a = parent[power][a];
            dist -= 1 << power;
        }

//        for (int j = parent.length - 1; j >= 0; j--) {
//            // Once we are finally the same level, exit
//            if (depth[a] == depth[b]) {
//                break;
//            }
////            if (level[parent[a][j]] < level[b]) { // then we went too far
////                continue;
////            }
//            // if we didn't overshoot, then update us
//            // FATAL BUG, the -1 problem. Basically this could be overcome with the above commented
//            // method, which makes sense since it goes right to the amount we need to jump, rather than trying all
//            // jump sizes
//            if (parent[j][a] != -1) {
//                if (depth[parent[j][a]] >= depth[b]) {
//                    a = parent[j][a];
//                }
//            }
//        }
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
