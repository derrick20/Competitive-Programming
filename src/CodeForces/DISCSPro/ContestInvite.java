/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class ContestInvite {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        HashMap<String, Integer> id = new HashMap<>();
        adjList = new ArrayList[N];
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            String name = sc.next();
            String name2 = sc.next();
            if (!id.containsKey(name)) {
                id.put(name, id.size());
            }
            if (!id.containsKey(name2)) {
                id.put(name2, id.size());
            }
            int id1 = id.get(name);
            int id2 = id.get(name2);
            adjList[id1].add(id2);
            adjList[id2].add(id1);
        }

        ArrayList<Integer> compSize = new ArrayList<>();
        visited = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (!visited[i])
                compSize.add(dfs(i, -1));
        }
        Collections.sort(compSize, Collections.reverseOrder());

        int ans = 0;
        for (int i = 0; i < Math.min(K, compSize.size()); i++) {
            ans += compSize.get(i);
        }
        out.println(ans);
        out.close();
    }

    static ArrayList<Integer>[] adjList;
    static boolean[] visited;
    static int dfs(int node, int parent) {
        int ans = 1;
        visited[node] = true;
        for (int adj : adjList[node]) {
            if (adj != parent && !visited[adj]) {
                ans += dfs(adj, node);
            }
        }
        return ans;
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
