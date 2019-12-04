/**
 * @author derrick20
 * Couple easy mistakes. Initialize the distance matrix with infinity, then
 * relax them based on the inputted adjMatrix, as well as through Floyd-Warshall
 * The crucial error was
 */
import java.io.*;
import java.util.*;
/*
4
0110
1110
0001
1000
6
1 2 1 2 1 2

4
0111
1011
1101
1110
 */
public class AnnaMaps {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
//        int[][] adjMat = new int[N][N];
        int[][] dist = new int[N][N];
        for (int i = 0; i < N; i++) {
            int j = 0;
            Arrays.fill(dist[i], (int) 1e9);
            dist[i][i] = 0;
            for (char c : sc.next().toCharArray()) {
                if (c == '1') {
//                    adjMat[i][j] = 1;
                    dist[i][j] = 1;
                }
                j++;
            }
        }
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
        int len = sc.nextInt();
        int[] path = new int[len];
        for (int i = 0; i < len; i++) {
            path[i] = sc.nextInt() - 1;
        }
        ArrayList<Integer> ans = new ArrayList<>();
        int idx = 0;
        int node = path[0];
        int idx2 = 0;
        ans.add(node);
        while (true) {
            int next = path[idx2];
            // test the next one
            // we can jump because distances match, so the shortest path also matches up til here
            while (idx2 < path.length - 1 && dist[node][path[idx2 + 1]] == (idx2 + 1) - idx) {
                idx2++;
                next = path[idx2];
            }
            idx = idx2; // jump up
//            if (node == next) {
//                ans.add(path[idx2 - 1]); // we must add at least 1 thing in between to prevent this
//            }
            node = next;
            ans.add(node);
            if (idx == path.length - 1) break;
        }
//        ans.remove(ans. size() - 1); //eh
        out.println(ans.size());
        for (int v : ans) {
            out.print((v + 1) + " ");
        }
        out.close();
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