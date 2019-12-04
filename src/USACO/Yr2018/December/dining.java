/*
 * @author derrick20
 * Ok hmm this was a tricky problem. I'd like to come back later,
 * but here's one main point about it that I was almost about to figure out
 * The core idea here lies in the CORE of what does Dijkstra really give us?
 * it's not just algorithm for shortest distance, but from where we are sourcing
 * the paths and to what our endpoints are -- those are the fine lines that
 * add so much power to it. In this situation, we took advantage of the two faces of Dijkstra
 * We first thought, ok we need to compare going straight to the end and going to a haybale, then
 * to the end. The weird part is that the act of going to a haybale reduces our cost, but
 * we can only pick one haybale to go to. How do we fix that? How about we split of this problem. The part where
 * we go to a haybale seems finicky, since different bales have different costs. However, an easy
 * part is going from the haybale to the END. That's  just the same as going from any regular node to the end.
 * In fact we should've already gathered that information when we were looking for the first part of our
 * comparison, the regular old quickest path to the end from ALL PATHS. That's now a very fruitful effort,
 * so let's do it. A subtlety - we do this cleverly by thinking about what is indiscriminate between all of these
 * paths: the endpoint. So, let's capitalize on that constant and do dijkstra from the end point!! Genius
 *
 * Goal is  to compare:
 * Shortest path from node to end  *VERSUS*  Shortest path from node to nearest bale, then from there to end
 *
 * Now what, we need the first part of the second item being compared (shortest path from node to NEAREST BALE)
 * Now that's a little weird, we have to go from some node to ANY BALE nearby, and somehow reap the singular benefit
 * from one bale. Ok, again, since we see that the bales are all indiscriminate endpoints, we should dijkstra from them
 * But now, this is the leap of cleverness I was missing. If we dijkstra from each bale, we have the perfect opportunity.
 * From this bale, going to any node, we must remember that there is a benefit of going to THIS BALE ALONE. Notice
 * luckily that we will never use the yumminess of OTHER BALES. Since this really a reverse operation of going from
 * the endpoint (the hay bale) to the start (some random node). Ahh, and if we remember this is multiple sources,
 * we don't care from which bale we figure out is the min path to a given node, say node 1. If node 1 can reach a
 * different Hay bale source in less distance, then it is definitely better. Once we've perfected our distances,
 * we can just check that comparison we thought of at the beginning.
 *
 * The weakness on my part was thinking about the comparison too early. Not sure how to prevent this in the future,
 * but I guess when something looks ugly, don't think about it yet. Try to generalize every part of the problem
 * (all nodes finding the comparison of to the end, and to a bale, then the end). Since you knew that, at the
 * stupidest level, the question is asking that directly. If we have exactly that information, we can't be wrong.
 */
import java.io.*;
import java.util.*;

public class dining {
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
        setupIO();
        setupIO("dining");

        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        ArrayList<Edge>[] adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int wt = sc.nextInt();
            adjList[u].add(new Edge(v, wt));
            adjList[v].add(new Edge(u, wt));
        }
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        PriorityQueue<Pair> pq2 = new PriorityQueue<>();

        int[] yum = new int[N]; // How yummy a given bale is
        int oo = (int) 1e9;
        for (int i = 0; i < K; i++) {
            int id = sc.nextInt() - 1;
            int yumminess = sc.nextInt();
            yum[id] = yumminess;
        }
        boolean[] visited = new boolean[N];
        int[] distToEnd = new int[N];
        Arrays.fill(distToEnd, oo);
        pq.add(new Pair(0, N - 1)); // This is the single source
        distToEnd[N - 1] = 0;
        while (!pq.isEmpty()) {
            Pair top = pq.poll();
            if (!visited[top.id]) {
                visited[top.id] = true;
                for (Edge e : adjList[top.id]) {
                    int altDist = distToEnd[top.id] + e.wt;
                    if (altDist < distToEnd[e.to]) {
                        // Update the distance to that bale
                        pq.add(new Pair(altDist, e.to));
                        distToEnd[e.to] = altDist;
                    }
                }
            }
        }

        // the final node, N-1 represents the start for the second dijkstra
        int[] distToBaleThenEnd = new int[N];
        Arrays.fill(distToBaleThenEnd, oo);
        for (int id = 0; id < N; id++) {
            if (yum[id] > 0) {
                // Currently, the distance to this bale, then to the end is the yumminess subtracted from the actual
                // distance
                int reducedDist = distToEnd[id] - yum[id];
                pq2.add(new Pair(reducedDist, id)); // these will be all the haybale sources
                distToBaleThenEnd[id] = reducedDist;
            }
        }

        visited = new boolean[N];
        while (!pq2.isEmpty()) {
            Pair top = pq2.poll();
            if (!visited[top.id]) {
                visited[top.id] = true;
                for (Edge e : adjList[top.id]) {
                    int altDist = distToBaleThenEnd[top.id] + e.wt;
                    if (altDist < distToBaleThenEnd[e.to]) {
                        pq2.add(new Pair(altDist, e.to));
                        distToBaleThenEnd[e.to] = altDist;
                    }
                }
            }
        }
        for (int id = 0; id < N - 1; id++) {
//            System.out.println(distToBaleThenEnd[id] + " " + distToEnd[id]);
            if (distToBaleThenEnd[id] <= distToEnd[id]) {
                out.println(1);
            }
            else {
                out.println(0);
            }
        }
        out.close();
    }

    static class Pair implements Comparable<Pair> {
        int dist, id;

        public Pair (int d, int i) {
            dist = d;
            id = i;
        }

        public int compareTo(Pair p2) {
            return dist - p2.dist;
        }
    }

    static class Edge {
        int to, wt;

        public Edge(int t, int w) {
            to = t;
            wt = w;
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