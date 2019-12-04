/**
 * @author derrick20
 */
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class ShortestCycle {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        adjList = new ArrayList[N];
        buckets = new HashMap<>();
        for (int i = 0; i <= 60; i++) {
            buckets.put(i, new ArrayList<>());
        }
        HashSet<Long> values = new HashSet<>();
        for (int i = 0; i < N; i++) {
            long v = sc.nextLong();
            long temp = v;
            int bucket = 0;
            while (temp > 0) {
                if ((temp & 1) > 0) {
                    buckets.get(bucket).add(v);
                    values.add(v);
                }
                temp >>= 1;
                bucket++;
            }
        }
        boolean found3 = false;
        for (Integer bucket : buckets.keySet()) {
            if (buckets.get(bucket).size() >= 3) {
                found3 = true;
                break;
            }
        }
        if (found3) {
            out.println(3);
        }
        else {
            int shortestCycle = oo;
            // This ensures that there are only 120 nodes at most. We can literally just bfs from each one now
            for (Long source : values) {
                HashMap<Long, Integer> visited = new HashMap<>();
                for (Long val : values) {
                    visited.put(val, 0);
                }
                ArrayDeque<Long> queue = new ArrayDeque<>();
                HashMap<Long, Integer> dist = new HashMap<>();
                HashMap<Long, Long> parent = new HashMap<>();

                parent.put(source, source);
                queue.add(source);
                dist.put(source, 0);
                bfs : while (queue.size() > 0) {
                    Long top = queue.removeFirst();
                    visited.put(top, 2);
                    long temp = top;
                    int bucket = 0;
                    while (temp > 0) {
                        if ((temp & 1) > 0) {
                            for (Long adj : buckets.get(bucket)) {
                                if (adj.equals(top)) continue;
                                if (visited.get(adj).equals(0)) {
                                    visited.put(adj, 1);
                                    queue.add(adj);
                                    dist.put(adj, dist.get(top) + 1);
                                    parent.put(adj, top);
                                }
                                else {
                                    // Ahah! found the cycle
                                    // Key issue: with something that branches to many repeated edges (parallel edges),
                                    // we may see an edge that we just visited,
                                    if (!parent.get(top).equals(adj) && visited.get(adj).equals(2)) {
                                        shortestCycle = Math.min(shortestCycle, dist.get(top) + dist.get(adj) + 1);
//                                        System.out.println("Top : " + top + " " + " Adj : " + adj + " " + shortestCycle);
                                        break bfs;
                                    }
                                }
                            }
                        }
                        temp >>= 1;
                        bucket++;
                    }
                }
//                System.out.println("Source: " + source + "\t Distances: " + dist);
            }
            out.println(shortestCycle == oo ? -1 : shortestCycle);
        }
        out.close();
    }

    static ArrayList<Long>[] adjList;
    static HashMap<Integer, ArrayList<Long>> buckets;
    static int oo = (int) 1e9;

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