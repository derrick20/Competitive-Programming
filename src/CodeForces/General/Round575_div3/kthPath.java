/*
 * @author derrick20
 * Interesting problem - the main concepts are applying Dijkstra or Floyd-Warshall
 * on a SHRUNKEN GRAPH. The hardest part is correctly removing the weightiest edges
 * until we reach a graph of minimal size (K, which is necessary to give the Kth
 * most expensive path). In order to do this, we need to reconstruct N, and the adjList
 * and cost 2D array. We use coordinate compression and then perform dijkstra from each
 * node. Importantly, there is an alternate method of doing this with edges stored inside
 * the PriorityQueue, but to each his own ¯\_(ツ)_/¯) Also, there's some hella cleverness we
 * can do with compressing, since we know there aren't any duplicates
 */

import java.io.*;
import java.util.*;

public class kthPath {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();

        PriorityQueue<Edge> edges = new PriorityQueue<>();
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int wt = sc.nextInt();
            edges.add(new Edge(u, v, wt));
        }
        while (edges.size() > Math.min(M, K)) {
            // Keep popping until we are limited by the smaller value
            // If not enough edges, stop, or if we have enough edges, stop once we hit K

            // Pop out the biggest weight edge
            edges.poll();
        }
        // CRUCIAL CHANGE: NEED TO RESIZE N APPROPRIATELY!
        TreeSet<Integer> vertices = new TreeSet<>();

        for (Edge e : edges) {
            int u = e.u;
            int v = e.v;
            vertices.add(u);
            vertices.add(v);
        }
        // RESIZING!!!!
        N = vertices.size();

        HashMap<Integer, Integer> coordinateCompress = new HashMap<>();
        for (int v: vertices) {
            // This is an ULTRA CLEVER way to keep track of what position
            // Literally the size is how far we've gone
            coordinateCompress.put(v, coordinateCompress.size());
        }

        // Initialize distances to infinity
        cost = new long[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cost[i][j] = inf;
            }
        }

        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (Edge e : edges) {
            // Now, we convert everything into the proper
            // compressed indices, and hereafter we will use the
            // proper values!
            int u = coordinateCompress.get(e.u);
            int v = coordinateCompress.get(e.v);
            int wt = e.wt;
            vertices.add(u);
            vertices.add(v);
            adjList[u].add(v);
            adjList[v].add(u);
            cost[u][v] = wt;
            cost[v][u] = wt;
        }


        // Perform Dijkstra's on the reduced graph, keeping track
        // of new distances at the end
        for (int start = 0; start < N; start++) {
            // A bunch of the nodes might not even be in there after deletion
            if (adjList[start].size() > 0) {
                // Really clever here: put in the literal distance matrix and fill it up (no copying needed!)
                // Abuse the reference
                dijkstra(start, cost[start]);
            }
        }
        paths = new ArrayList<>();
        // add all costs to the global paths list
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                paths.add(cost[i][j]);
            }
        }
        Collections.sort(paths);
//        System.out.println(paths);
        out.println(paths.get(K - 1));
        out.close();
    }

    static int N;
    static long inf = (long) 1e18;
    static ArrayList<Integer>[] adjList;
    static long[][] cost;
    static ArrayList<Long> paths;

    // Greedily determine the shortest paths from some source vertex
    // to all other vertices. Rely on the fact that new shortest paths
    // must build upon prior shortest paths to relax distances
    static void dijkstra(int start, long[] distance) {
//        boolean[] visited = new boolean[N];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0));
        distance[start] = 0;

        while (!pq.isEmpty()) {
            Node next = pq.poll();
            int node = next.id;
            if (distance[node] < next.dist) {
                // this means we've found a better path to this before
                // Don't bother exploring its edges therefore
                continue;
            }
            for (int adj : adjList[node]) {
                // If unvisited, relax the distance.
                // We have now found the shortest distance to this path
                long altDist = distance[node] + cost[node][adj];
                if (altDist < distance[adj]) {
                    distance[adj] = altDist;
                }
                pq.add(new Node(adj, altDist));
            }
        }
    }

    static class Node implements Comparable<Node> {
        int id;
        long dist;

        public Node(int i, long d) {
            id = i;
            dist = d;
        }

        public int compareTo(Node n2) {
            return dist < n2.dist ? -1 : 0;
        }
    }

    static class Edge implements Comparable<Edge> {
        int u, v, wt;

        public Edge(int uu, int vv, int w) {
            u = uu;
            v = vv;
            wt = w;
        }

        public int compareTo(Edge e2) {
            return e2.wt - wt;
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
