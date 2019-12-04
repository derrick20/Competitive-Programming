/*
 * @author derrick20
 * Wait seriously >:( this problem is literally an instantt get you
 * just have to notice that it's always gonna work. Since there's 3N
 * nodes, and you just need N to go into either an independent set or the matching
 * set, by the pigeonhole principle one of them will have more than enough.
 * Thus, you can just pick whichever one and count to it! (often both can work!)
 */
import java.io.*;
import java.util.*;

public class matchingSets {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        while (T-->0) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            ArrayList<Edge> adjList = new ArrayList<>();

            for (int i = 1; i <= M; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                adjList.add(new Edge(u, v, i));
            }

            boolean[] removed = new boolean[3 * N + 1];
            ArrayList<Integer> edges = new ArrayList<>();
            for (Edge e : adjList) {
                if (!removed[e.u] && !removed[e.v]) {
                    removed[e.u] = true;
                    removed[e.v] = true;
                    edges.add(e.id);
                }
            }
            ArrayList<Integer> nodes = new ArrayList<>();
            for (int i = 1; i <= 3 * N; i++) {
                if (!removed[i]) {
                    removed[i] = true;
                    nodes.add(i);
                }
            }

            if (edges.size() >= N) {
                out.println("Matching");
                int i = 0;
                for (int id : edges) {
                    if (i == N) {
                        break;
                    }
                    out.print(id + " ");
                    i++;
                }
                out.println();
            }
            else if (nodes.size() >= N) {
                out.println("IndSet");
                int i = 0;
                for (int node : nodes) {
                    if (i == N) {
                        break;
                    }
                    out.print(node + " ");
                    i++;
                }
                out.println();
            }
        }

        out.close();
    }

    static class Edge {
        int u, v, id;

        public Edge(int uu, int vv, int ii) {
            u = uu; v = vv; id = ii;
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
