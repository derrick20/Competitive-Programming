/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;
/*
Slightly fixed (DFS was wrong in the original, i added danny's into mine)
15374341 9590
49386577 363697052
516446329 179751805
364823940 636446164
508799164 598302108
378877611

Correct:
[15335981, 9590]
[958295435, 363697052]
[666223900, 613225400]
[44654104, 570492080]
741649036

Old one:
15374341 9590
12154901 362645250
830170741 303256124
371102715 689260712
23292404 975987317
944283080
 */
public class mooriokartFIX {
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

        Meadow[] meadows = new Meadow[N];
        for (int i = 0; i < N; i++) {
            meadows[i] = new Meadow(i);
        }
        for (int i = 0; i < M; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            long d = sc.nextInt();
            meadows[a].edges.put(meadows[b], d);
            meadows[b].edges.put(meadows[a], d);
        }
        Map<Integer, List<Integer>> pathLists = new HashMap<>();
        for (Meadow root : meadows) {
            List<Integer> paths = new ArrayList<>();
            int minIx = root.ix;
            int currLength = 0;
            root.importantEdgeLength = 0;
            Stack<Meadow> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                Meadow meadow = stack.pop();
                if (meadow.counted) {
                    meadow.counted = false;
                    currLength -= meadow.importantEdgeLength;
                } else {
                    meadow.counted = true;
                    currLength += meadow.importantEdgeLength;
                    minIx = Math.min(minIx, meadow.ix);
                    stack.push(meadow);
                    if (meadow.ix > root.ix) {
                        paths.add(currLength);
                    }
                    for (Map.Entry<Meadow, Long> edge : meadow.edges.entrySet()) {
                        if (edge.getKey().counted) {
                            continue;
                        }
                        edge.getKey().importantEdgeLength = edge.getValue();
                        stack.push(edge.getKey());
                    }
                }
            }
            if (pathLists.containsKey(minIx)) {
                pathLists.get(minIx).addAll(paths);
            } else {
                pathLists.put(minIx, paths);
            }
        }
        distFreqMap = new ArrayList<>();
        for (List<Integer> distances : pathLists.values()) {
            TreeMap<Integer, Long> map = new TreeMap<>();
            for (int d : distances) {
                updateMap(map, d);
            }
            distFreqMap.add(map);
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

    static class Meadow {
        final int ix;
        final Map<Meadow, Long> edges = new HashMap<>();
        boolean counted = false;
        long importantEdgeLength = -1;

        Meadow(int ix) {
            this.ix = ix;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Meadow meadow = (Meadow) o;
            return ix == meadow.ix;
        }

        @Override
        public int hashCode() {
            return Objects.hash(ix);
        }
    }

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

            /*
[955, 1]
[1912, 2]
[2872, 3]
[4791, 5]
[7668, 8]
[10551, 11]
[13443, 14]
[25029, 26]
[29867, 31]
[35688, 37]
[42512, 44]
[51318, 53]
[60232, 62]
[69218, 71]
[79185, 81]
[90125, 92]
[103064, 105]
[119917, 122]
[138762, 141]
[159613, 162]
[184387, 187]
[219835, 222]
[259460, 261]
             */

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

    static void dfsForDistances(int node, int parent, int d) {
        for (Edge e : adjList[node]) {
            int adj = e.to;
            int dist = e.wt;
            if (adj != parent) {
                dfsForDistances(adj, node, d + dist);
                // the current component we have attained
                int component = distFreqMap.size() - 1;
                updateMap(distFreqMap.get(component), d + dist);
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
