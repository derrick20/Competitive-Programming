import java.io.*;
import java.util.*;
/*
 * Codeforces Round 360 Div. 2 C
 */
public class minvertexcover {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        adjList = new ArrayList[n+1];
        visited = new int[n+1];
        for (int i = 1; i <= n; i++) {
            adjList[i] = new ArrayList<>();
            visited[i] = -1;
        }
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adjList[u].add(v);
            adjList[v].add(u);
        }
        // two colors: 0 and 1
        // stored in visited, which allows us to construct the vertex covers later
        for (int i = 1; i <= n; i++) {
            if (visited[i] == -1) {
                boolean possible = dfs(i, -1, 0);
                if (!possible) {
                    System.out.println(-1);
                    System.exit(0);
                }
            }
        }
        // If we managed to get through the whole graph, then print stuff
        ArrayList<Integer> c0 = new ArrayList<>();
        ArrayList<Integer> c1 = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            // disjoint, check!
            if (visited[i] == 0) {
                c0.add(i);
            }
            else {
                c1.add(i);
            }
        }
        System.out.println(c0.size());
        for (int i : c0) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println(c1.size());
        for (int i : c1) {
            System.out.print(i + " ");
        }
    }

    static int[] visited;
    static ArrayList<Integer>[] adjList;

    public static boolean dfs(int node, int parent, int color) {
        // this means we cannot 2-color it
        if (visited[node] == 1-color) {
            return false;
        }
        else if (visited[node] == color) {
            // break early since we've gone down the path.
            return true;
        }
        else {
            // However, the last case, if not visited, then set it
            visited[node] = color;
            for (int adj : adjList[node]) {
                // since undirected, make sure we aren't just going back and forth
                if (adj != parent) {
                    boolean valid = dfs(adj, node, 1-color);
                    if (!valid) {
                        return false;
                    }
                }
            }
            return true; // If we managed to get through all branches, then it's fine in this connected component
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
