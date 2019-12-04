/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class ShortCut {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean testing) throws Exception {
        String prefix = testing ? "/Users/derrick/IntelliJProjects/src/USACO/" : "";
        sc = new FastScanner(prefix + problem_name + ".in");
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner();
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
//        setupIO();
      setupIO("shortcut", false);

        int N = sc.nextInt();
        int M = sc.nextInt();
        T = sc.nextInt();
        cows = new int[N];
        for (int i = 0; i < N; i++) {
            cows[i] = sc.nextInt();
        }

        int[] pred = new int[N];
        ArrayList<Edge>[] graphAdjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            graphAdjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int wt = sc.nextInt();
            graphAdjList[u].add(new Edge(v, wt));
            graphAdjList[v].add(new Edge(u, wt));
        }
        // Dijkstra, then get predecessor tree.
        // Then start from each node and go up (O(N))
        // Basically union-find style, then we have a tree
        // Then, dfs down, then when recursing up,
        // we sum all the adjacent farther children and see
        // their sum of cows, and check if (distance to here - T) * cows
        // is better than our best answer so far
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        dist = new int[N];
        int oo = (int) 1e9;
        Arrays.fill(dist, oo);
        dist[0] = 0;
        pq.add(new Edge(0, 0));
        while (pq.size() > 0) {
            Edge curr = pq.poll();
            for (Edge e : graphAdjList[curr.to]) {
                int altDist = dist[curr.to] + e.wt;
                if (altDist < dist[e.to]) {
                    dist[e.to] = altDist;
                    pred[e.to] = curr.to;
                    pq.add(e);
                }
                else if (altDist == dist[e.to]) {
                    // If the new way to reach e.to has a better predecessor (curr)
                    // then update the path
                    if (curr.to < pred[e.to]) {
                        pred[e.to] = curr.to;
                        pq.add(e);
                    }
                }
            }
        }
        for (int i = 0; i < N; i++) {
            int curr = i;
//            System.out.print(curr+1 + " ");
            while (pred[curr] != curr) {
                curr = pred[curr];
//                System.out.print(curr+1 + " ");
            }
//            System.out.println();
        }

        treeAdjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            treeAdjList[i] = new ArrayList<>();
        }
        boolean[] visited = new boolean[N];
        // visited[node] represents the fact that it has already been connected to its
        // predecessor. When another branch has connected and now is on same path, it
        // will just break

        for (int i = 0; i < N; i++) {
            int curr = i;
            // Make sure we don't jump to something that was visited (meaning
            // that whole path was already added to tree already)
            while (pred[curr] != curr && !visited[curr]) {
                visited[curr] = true;
                // Add all connections that aren't self-looping
                treeAdjList[curr].add(pred[curr]);
                treeAdjList[pred[curr]].add(curr);
                curr = pred[curr];
            }
        }
//        for (int i = 1; i < N; i++) {
//            treeAdjList[pred[i]].add(i);
//        }

//        for (int i = 0; i < N; i++) {
//            System.out.println(treeAdjList[i]);
//        }

        subtreeCows = new long[N];
        dfs(0, -1);
//        System.out.println(Arrays.toString(subtreeCows));
        for (int node = 0; node < N; node++) {
            long costReduction = ((long) dist[node] - T) * subtreeCows[node];
            if (costReduction > best) {
                best = costReduction;
            }
        }
        out.println(best);
        out.close();
    }

    static ArrayList<Integer>[] treeAdjList;
    static int[] dist;
    static int T;
    static long best;
    static int[] cows;
    static long[] subtreeCows;

    // Go down the tree of shortest paths constructed, and sum the number of
    // cows in our subtree. If we add the shortcut at this node, we will get
    // a (dist[node] - T) * numCows of a decrease.
    static long dfs(int node, int par) {
        long numCows = cows[node];
        for (int adj : treeAdjList[node]) {
            if (adj != par) {
                numCows += dfs(adj, node);
            }
        }
        return subtreeCows[node] = numCows;
    }

    static class Edge implements Comparable<Edge> {
        int to, wt;
        public Edge(int neighbor, int weight) {
            to = neighbor; wt = weight;
        }

        public String toString() {
            return "(" + to  + ", " + wt + ")";
        }

        public int compareTo(Edge e2) {
            // Sort by weight, then lexicoographically
            return wt - e2.wt != 0 ? wt - e2.wt : to - e2.to;
        }
    }

    static class FastScanner {
        public int BS = 1<<16;
        public char NC = (char)0;
        byte[] buf = new byte[BS];
        int bId = 0, size = 0;
        char c = NC;
        double cnt = 1;
        BufferedInputStream in;

        public FastScanner() {
            in = new BufferedInputStream(System.in, BS);
        }

        public FastScanner(String s) {
            try {
                in = new BufferedInputStream(new FileInputStream(new File(s)), BS);
            }
            catch (Exception e) {
                in = new BufferedInputStream(System.in, BS);
            }
        }

        private char getChar(){
            while(bId==size) {
                try {
                    size = in.read(buf);
                }catch(Exception e) {
                    return NC;
                }
                if(size==-1)return NC;
                bId=0;
            }
            return (char)buf[bId++];
        }

        public int nextInt() {
            return (int)nextLong();
        }

        public long nextLong() {
            cnt=1;
            boolean neg = false;
            if(c==NC)c=getChar();
            for(;(c<'0' || c>'9'); c = getChar()) {
                if(c=='-')neg=true;
            }
            long res = 0;
            for(; c>='0' && c <='9'; c=getChar()) {
                res = (res<<3)+(res<<1)+c-'0';
                cnt*=10;
            }
            return neg?-res:res;
        }

        public double nextDouble() {
            double cur = nextLong();
            return c!='.' ? cur:cur+nextLong()/cnt;
        }

        public String next() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=getChar();
            while(c>32) {
                res.append(c);
                c=getChar();
            }
            return res.toString();
        }

        public String nextLine() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=getChar();
            while(c!='\n') {
                res.append(c);
                c=getChar();
            }
            return res.toString();
        }

        public boolean hasNext() {
            if(c>32)return true;
            while(true) {
                c=getChar();
                if(c==NC)return false;
                else if(c>32)return true;
            }
        }
    }
}