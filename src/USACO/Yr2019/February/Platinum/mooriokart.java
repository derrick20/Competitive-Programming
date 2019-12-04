/*
 * @author derrick20
 */
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
/*
8 5 1 13
1 2 3
2 3 4
4 5 6
5 6 7
7 8 2

7 4 1 12
1 2 3
3 4 6
5 6 2
6 7 2

3.in steps for aboveY and aboveYFreq
[259460, 261]
[754416090, 1600061]
[817044249, 843994310]
[858751257, 309619108]
[34006943, 166221094]
[342616195, 918725437]
[13582487, 300930430]
 */
public class mooriokart {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
//        sc = new FastScanner(new FileReader("mooriokart.in"));
        sc = new FastScanner(new FileReader("src/USACO/Yr2019/February/Platinum/mooriokart_platinum_feb19/6.in"));
//        out = new PrintWriter(new FileWriter("mooriokart.out"));
        N = sc.nextInt();
        M = sc.nextInt();
        X  = sc.nextInt();
        Y = sc.nextInt();

        adjList = new ArrayList[N];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int wt = sc.nextInt();
            adjList[u].add(new Edge(v, wt));
            adjList[v].add(new Edge(u, wt));
        }

        visited = new boolean[N];
        distFreqMap = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                distFreqMap.add(new TreeMap<>());
                currComponent = new ArrayList<>();
                dfs(i, -1);
                // Now, we have a filled up component.
                // Go through each node and figure the distance to all other nodes
                // from it as a source, do this for each possible source node (O(N^2))
                for (int source : currComponent) {
                    dfsForDistances(source, -1, source, 0);
                }
//                System.out.println(currComponent);
                int component = distFreqMap.size() - 1;
                for (Map.Entry<Integer, Long> entry : distFreqMap.get(component).entrySet()) {
                    // Since we overcounted everything by 2
                    // AHH cleverness is you only put distances into the dfs if source node is less than us
                    // (Exactly half of them will allow for this)
                    distFreqMap.get(component).put(entry.getKey(), entry.getValue()); // / 2);
                }
            }
        }
        K = distFreqMap.size();
        for (TreeMap<Integer, Long> map : distFreqMap) {
            System.out.println(map);
        }

        TreeMap<Integer, Long> ways = new TreeMap<>();
        // Basically, start off with the given KX roads and all future paths will have used it
        ways.put((int) ((K * X) % mod), 1L);
        ans = 0;
        aboveY = 0;
        aboveYFreq = 0;
        // TODO ALSO, within each of the maps, you should be getting rid of stuff over Y
        for (int i = 0; i < distFreqMap.size(); i++) {
            ways = combine(ways, distFreqMap.get(i));
//            System.out.println(ways);
            System.out.println(aboveY + " " + aboveYFreq);
        }
        ans = aboveY;

        ans *= fastExponentiate(2, K - 1);
        ans %= mod;
        ans *= factorial(K - 1);
        ans %= mod;
//        System.out.println(ways);
//        System.out.println(distFreqMap);
        out.println(ans);
        out.close();
    }
    static long ans, aboveY, aboveYFreq;
    static long mod = (long) 1e9 + 7;
    static int N, M, K, X, Y;
    static ArrayList<Edge>[] adjList;
    static ArrayList<TreeMap<Integer, Long>> distFreqMap;
    static boolean[] visited;
    static ArrayList<Integer> currComponent;

    // Go through all possible distances we've discovered so far,
    // and update with new distances by building upon those (as well just
    // this distance alone)
    static TreeMap<Integer, Long> combine(TreeMap<Integer, Long> map1, TreeMap<Integer, Long> map2) {
        TreeMap<Integer, Long> updated = new TreeMap<>();

        long nextAboveFreq = 0;
        long nextAboveY = 0;
//        System.out.println(map1 + " " + map2);

        for (Map.Entry<Integer, Long> entry1 : map2.entrySet()) {
            int dist1 = entry1.getKey();
            long freq1 = entry1.getValue();

            // There are two buckets: distances in the currently merged set
            // that are 1. less than the goal, or 2. greater than our equal
            // Let's deal with 1
            for (Map.Entry<Integer, Long> entry2 : map1.entrySet()) {
                int dist2 = entry2.getKey();
                long freq2 = entry2.getValue();

                int path = dist1 + dist2;
                long poss = (freq1 * freq2) % mod;

                // Create a new path big enough.
                if (dist1 + dist2 >= Y) {

                    // Use both freqs to compute total ways, update the new values
                    nextAboveY += (path * poss) % mod;
                    nextAboveY %= mod;
                    nextAboveFreq += poss;
                    nextAboveFreq %= mod;
                }
                else {
                    updated.put(path, poss);
                }
            }

            // Now, for 2. If we actually had stuff inside this bucket to deal with
            // What kind of path would we be creating?
            if (aboveYFreq > 0) {
                long path = (aboveY + dist1 * aboveYFreq) % mod;
                // Distinction since the aboveY already contains all of the prior paths
                nextAboveY += (path * freq1) % mod;
                nextAboveY %= mod;
                // all of these ways can be done with the old ways
                nextAboveFreq += (freq1 * aboveYFreq);
                nextAboveFreq %= mod;
            }
        }
        aboveY = nextAboveY;
        aboveYFreq = nextAboveFreq;
        return updated;
    }

    // Use a visited array, even though it's a tree, because we need to
    // track connected components
    static void dfs(int node, int parent) {
        visited[node] = true;
        currComponent.add(node);
        for (Edge e : adjList[node]) {
            int adj = e.to;
            if (adj != parent) {
                dfs(adj, node);
                // the current component we have attained
//                int component = distFreqMap.size() - 1;
//                distFreqMap.set(component, update(distFreqMap.get(component), dist));
            }
        }
    }

    static long factorial(int k) {
        long ans = 1;
        while (k >= 2) {
            ans *= k--;
            ans %= mod;
        }
        return ans;
    }

    static long fastExponentiate(int x, int p) {
        if (p == 0) {
            return 1;
        }
        if (p == 1) {
            return x;
        }
        else {
            long root = fastExponentiate(x, p / 2);
            long res = (root * root) % mod;
            if (p % 2 == 0) {
                return res;
            }
            else {
                return (x * res) % mod;
            }
        }
    }

    static void dfsForDistances(int node, int parent, int root, int d) {
        for (Edge e : adjList[node]) {
            int adj = e.to;
            int dist = e.wt;
            if (adj != parent) {
                dfsForDistances(adj, node, root, d + dist);
                // the current component we have attained
                int component = distFreqMap.size() - 1;
                if (adj < root) {
                    updateMap(distFreqMap.get(component), d + dist);
                }
//                distFreqMap.set(component, update(distFreqMap.get(component), dist));
            }
        }
    }

    static void updateMap(TreeMap<Integer, Long> map, int dist) {
        if (map.containsKey(dist)) {
            map.put(dist, map.get(dist) + 1);
        }
        else {
            map.put(dist, 1L);
        }
    }

    static class Edge {
        int to, wt;

        public Edge(int t, int w) {
            to = t;
            wt = w;
        }

        public String toString() {
            return "(" + to + ", " + wt + ")";
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
