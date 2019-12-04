/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class cactus {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int C = sc.nextInt();
        int c = 1;
        while (C-->0) {
            int V = sc.nextInt();
            int E = sc.nextInt();
            MagicComponents mc = new MagicComponents(V);
            for (int i = 0; i < E; i++) {
                int u = sc.nextInt() - 1;
                int v = sc.nextInt() - 1;
                mc.addEdge(u, v);
            }
            mc.run();
            long ans = 1;
            long mod = 1007;
            for (ArrayList<Edge> component : mc.bccs) {
                ans = (ans * component.size() % mod);
            }
            out.println("Case #" + c++ + ": " + ans + "\n");
        }
        out.close();
    }

    static class Edge {
        int u, v, id;
        public Edge(int U, int V, int i) {
            u = U; v = V; id = i;
        }

        public String toString() {
            return u+"->"+v;
        }
    }

    static class MagicComponents { // Stack Trick!
        int num, n, edges;
        int[] dfsNum, low, vis;
        ArrayList<Integer> cuts;
        ArrayList<Edge> bridges, adj[];
        ArrayList<ArrayList<Edge>> bccs;
        ArrayDeque<Edge> eStack;

        MagicComponents(int N) {
            adj = new ArrayList[n = N];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();
            edges = 0;
        }

        void addEdge(int u, int v) {
            adj[u].add(new Edge(u, v, edges));
            adj[v].add(new Edge(v, u, edges++));
        }

        void run() {
            vis = new int[n];
            dfsNum = new int[n];
            low = new int[n];
            bridges = new ArrayList<>();
            cuts = new ArrayList<>();
            bccs = new ArrayList<>();
            eStack = new ArrayDeque<>();
            num = 0;

            for (int i = 0; i < n; i++) {
                if (vis[i] != 0) continue;
                dfs(i, -1);
            }
        }

        void dfs(int node, int par) {
            dfsNum[node] = low[node] = num++;
            vis[node] = 1;
            int nChild = 0;
            for(Edge e : adj[node]) {
                if(e.v == par) continue;
                if(vis[e.v] == 0) {
                    ++nChild; eStack.push(e); dfs(e.v, node);
                    low[node] = Math.min(low[node], low[e.v]);
                    if(low[e.v] >= dfsNum[node]) {
                        if(dfsNum[node] > 0 || nChild > 1)
                            cuts.add(node);
                        if(low[e.v] > dfsNum[node]) {
                            bridges.add(e); pop(node);
                        } else pop(node);
                    }
                } else if(vis[e.v] == 1) {
                    low[node] = Math.min(low[node], dfsNum[e.v]);
                    eStack.push(e);
                }
            }
            vis[node] = 2;
        }

        void pop(int u) {
            ArrayList<Edge> list = new ArrayList<>();
            for (;;) {
                Edge e = eStack.pop(); list.add(e);
                if(e.u == u) break;
            }
            bccs.add(list);
        }

        // Prints all the relevant information after running bccs.
        void print() {
            System.out.println("Node\tPre\tLow");
            for (int i=0; i<n; i++)
                System.out.println(i+"\t"+dfsNum[i]+"\t"+low[i]);
            System.out.println("---------------------------");

            // Go through each bcc
            for (int i=0; i<bccs.size(); i++) {
                System.out.println("Component "+i);
                ArrayList<Edge> tmp = bccs.get(i);
                for (Edge e: tmp)
                    System.out.println(e);
            }
            System.out.println("-------------------------");
            System.out.println("Bridges");
            for (Edge e: bridges)
                System.out.println(e);
            System.out.println("-------------------------");
            System.out.println("Articulation Pts");
            for (Integer v: cuts)
                System.out.println(v);
        }

        //# Make sure to call run before calling this function.
        // Function returns a new graph such that all two connected
        // components are compressed into one node and all bridges
        // in the previous graph are the only edges connecting the
        // components in the new tree.
        // map is an integer array that will store the mapping
        // for each node in the old graph into the new graph. //$
        MagicComponents componentTree(int[] map) {
            boolean[] vis = new boolean[edges];
            for(Edge e : bridges) vis[e.id] = true;

            int numComp = 0;
            Arrays.fill(map, -1);
            for(int i = 0; i < n; ++i) {
                if(map[i] == -1) {
                    ArrayDeque<Integer> q = new ArrayDeque<>();
                    q.add(i);
                    map[i] = numComp;
                    while(!q.isEmpty()) {
                        int node = q.poll();
                        for(Edge e : adj[node]) {
                            if(!vis[e.id] && map[e.v] == -1) {
                                vis[e.id] = true;
                                map[e.v] = numComp;
                                q.add(e.v);
                            }
                        }
                    }
                    ++numComp;
                }
            }

            MagicComponents g = new MagicComponents(numComp);
            Arrays.fill(vis, false);
            for(int i = 0; i < n; ++i) {
                for(Edge e : adj[i]){
                    if(!vis[e.id] && map[e.v] < map[e.u]) {
                        vis[e.id] = true;
                        g.addEdge(map[e.v], map[e.u]);
                    }
                }
            }

            return g;
        }

        //# Make sure to call run before calling this function.
        // Function returns a new graph such that all biconnected
        // components are compressed into one node. Cut nodes will
        // be in multiple components, so these nodes will also have
        // their own component by themselves. Edges in the graph
        // represent components to articulation points
        // map is an integer array that will store the mapping
        // for each node in the old graph into the new graph.
        // Cut points to their special component, and every other node
        // to their specific component. //$
        MagicComponents bccTree(int[] map) {
            int[] cut = new int[n];
            Arrays.fill(cut, -1);
            int size = bccs.size();
            for(int i : cuts) map[i] = cut[i] = size++;
            MagicComponents g = new MagicComponents(size);
            int[] used = new int[n];
            for(int i = 0; i < bccs.size(); ++i) {
                ArrayList<Edge> list = bccs.get(i);
                for(Edge e: list) {
                    for(int node : new int[] {e.u, e.v}) {
                        if(used[node] != i+1) {
                            used[node] = i+1;
                            if(cut[node] != -1)
                                g.addEdge(i, cut[node]);
                            else map[node] = i;
                        }
                    }
                }
            }
            return g;
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
