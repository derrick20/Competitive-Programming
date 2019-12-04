/**
 * @author derrick20
 * What I learned in boating school is...
 * What I learned in boating school iiiiis...
 * 1. Use HashSets since they tend to give lots of parallel edges (baseically
 * duplicates of (u, v). Thus, also use += when adding in new edges, because
 * this takes care of such parallel edges by amplifying their capacity!
 * Adjacency SET, therefore, not Adjacency List!!
 *
 * 2. If it's UNDIRECTED, both the forward and backward capacities must increase!!
 * when initializing things
 *
 * 3. It makes much more intuitive sense to DFS and grab hold of whatever quickest
 * path we can find to get to the end. We need to have a visited array!! This is a
 * graph with many potential cycles and interleaving connections!
 *
 * 4. The cleverest trick here is with not needing to store the flows in a matrix. Instead,
 * we track the capacities, which implicitly represent the total capacity initially. Essentially
 * we're pouring the pitcher back and forth between the flow in forward and backward
 * (slightly different, though since we're transferring CAPACITY, or an ABSENCE OF FLOW,
 * but the idea is the same basically.)
 * Essentially, conservation of flow is the same as conservation of capacity, which we
 * can use more intuitively when updating based on the bottleneck determined from
 * the path minimum.
 * An alternative technique stores the flow and capacities separately. Thus,
 * we would be checking if ORIGINAL capacity - flow is > 0, meaning more capacity
 * is left. And to update, we would only modify the flow through an edge, as
 * the capacities represent initial amounts.
 *
 * 5. My basic instinct was that it'd be tricky to track all the paths. However, this
 * algorithm really isn't that complex. Our dfs just grabs a SINGLE PATH, then
 * returns. I was right about the boolean, and most of the flow, just didn't
 * know exactly whether parents for reconstructign path was the right way. Also,
 * I wasn't confident on adjMatrix vs adjList. In fact it's important to see
 * that this requires a DFS, so clearly adjList will help for O(N). However,
 * we need the distances between all connections, so a look up table via a matrix
 * is also necessary. Don't assume that the two structures are mutually excluseive!!
 */
import java.io.*;
import java.util.*;

public class FordFulkersonTest {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int test = 1;
        while (true) {
            int N = sc.nextInt();
            if (N == 0) {
                break; // last network
            }
            source = sc.nextInt() - 1;
            sink = sc.nextInt() - 1;
            int edges = sc.nextInt();
            adjList = new HashSet[N];
            for (int i = 0; i < N; i++) {
                adjList[i] = new HashSet<>();
            }
            // Instead of doing an adjacency List, we could be smarter
            // and realize that we don't need to store flow and capacity!
            // flow can be kept in a variable, and the forward and backward
            // edges will always sum to the original capacity. Thus, we
            // just need to update those edges carefully and we'll never lose
            // any information
            // O wait i was wrong. More clearly it was to split the info into the two.
            // still don't track flow itself in each edge tho. (edwardesque lol)
            capacityMatrix = new int[N][N];
            for (int i = 0; i < edges; i++) {
                int u = sc.nextInt() - 1;
                int v = sc.nextInt() - 1;
                int cap = sc.nextInt();
                adjList[u].add(v);
                adjList[v].add(u);
                capacityMatrix[u][v] += cap; // Forward edge begins with this capacity
                capacityMatrix[v][u] += cap; // GAHHGAHHAHGH!!
            }
            parent = new int[N];
            // Ford-Fulkerson Time!

            int maxFlow = 0;
            int oo = (int) 1e9;
            // Keep DFS'ing to find a new path, that is NOT fully filled yet
            // While there exists a path of nonzero capacity from source to sink, keep updating
            visited = new boolean[N];
            while (dfs(source)) {
                // After we dfs, we have to clean out the visited array again
                visited = new boolean[N];
                // Now we can climb back up the parent ladder and
                // update everything along the way
                int bottleNeck = oo;
                // Go up once with a pointer variable to find the min flow
//                String path = "";
                for (int pointer = sink; pointer != source; pointer = parent[pointer]) {
                    int u = parent[pointer];
                    int v = pointer;
                    bottleNeck = Math.min(bottleNeck, capacityMatrix[u][v]);// - flow[u][v]);
//                    path = showEdge(u, v) + path;
                }
//                System.out.print(path);
                // Don't forget to update our overall flow!
                maxFlow += bottleNeck;
                // p now updating each node as we meet them
                for (int pointer = sink; pointer != source; pointer = parent[pointer]) {
                    // Loss of capacity in the forward direction
                    int u = parent[pointer];
                    int v = pointer;
                    capacityMatrix[u][v] -= bottleNeck;
                    // But it is still available in the backward direction.
                    // Thus, the net flow is still fine
                    capacityMatrix[v][u] += bottleNeck;
                }

            }
            out.println("Network " + test++);
            out.println("The bandwidth is " + maxFlow + ".\n");
        }
        out.close();
    }

    static int source, sink;
    static HashSet<Integer>[] adjList;
    static int[][] capacityMatrix, flow;
    static int[] parent;
    static boolean[] visited;

    static String showEdge(int u, int v) {
       return u + " -> " + v + " Forward: " + (capacityMatrix[u][v] - flow[u][v]) + " Backward: " + (capacityMatrix[v][u] - flow[v][u]) + "\n";
    }

    // Honestly, this problem is way more conducive to using
    // a DFS since think about the idea of just finding ANY path that
    // would work for us.
    static boolean dfs(int node) {
        if (node == sink) {
            return true;
        }
        else {
            for (int adj : adjList[node]) {
                // This thing must have more capacity for flow, too
                if (!visited[adj] && capacityMatrix[node][adj] > 0) {
                    visited[adj] = true;
                    parent[adj] = node;
                    if (dfs(adj)) {
                        return true; // finished so recurse all the way up
                    }
                }
            }
            return false;
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
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}