import java.io.*;
import java.util.*;
/*
 * HackerEarth Lonely Island
 * Crucial takeaways: I wasn't actually using a topo sort
 * for what it was meant for. I was just dfs-ing and accumulating the probabilities
 * The core of what topo sort does is allows us to propagate probabilities in an orderly fashion
 * so that we won't have to repeat nodes. Also, i forgot to add a check to ensure that LEAF NODES
 * are the candidates for best values. This is another trick: what distinguishes an island that can't be
 * exited from other nodes? (outdegree = 0)
 * Also, we could have done this with the indegree technique, but i wanted to try the dfs way (pretty clean)
 */

public class lonelyIsland {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int start = sc.nextInt();

        // Fill up adjacency list. Ugh 1-indexed
        ArrayList<Integer>[] adjList = new ArrayList[N+1];
        for (int i = 1; i <= N; i++)
            adjList[i] = new ArrayList<>();

        // int[] inDegree = new int[N+1];

        for (int i = 0; i < M; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            adjList[from].add(to);
            // inDegree[to]++;
        }

        // First way, using the in-degree method
        //for (int i = 0; i <= N; i++)
            //System.out.println(adjList[i]);
        ArrayList<Integer> res = new ArrayList<>();
        p = new double[N+1]; // Will store the probability of ending up at each location ultimately
        p[start] = 1;

        visited = new int[N+1];
        dfs(start, adjList, res);
        Collections.reverse(res); // KEY bug rip

        for (int node: res) {
            for (int adj: adjList[node]) {
                p[adj] += p[node] / adjList[node].size();
            }
        }

        ArrayList<Integer> bestList = new ArrayList<>();
        double bestVal = 0;
        for (int i = 1; i <= N; i++) {
            if (adjList[i].size() == 0) { // it must be a leaf as a requirement
                bestVal = Math.max(bestVal, p[i]);
                //System.out.println(res.get(i-1) + " ");
            }
            //  System.out.println(i + " " + p[i]);
        }

        for (int i = 2; i <= N; i++) {
            if (Math.abs(p[i] - bestVal) <= 1e-9 && adjList[i].size() == 0) {
                // System.out.println(i + " " + p[i]);
                bestList.add(i);
            }
        }
        // System.out.println(res.get(N-1) + " " + p[res.get(N-1)]);
        int len = bestList.size();
        for (int i = 0; i < len - 1; i++) {
            System.out.print(bestList.get(i) + " ");
        }
        System.out.println(bestList.get(len - 1));
    }
    static double[] p;
    static int[] visited;
    // This topological sort allows us to run O(E+N)
    public static void dfs(int current, ArrayList<Integer>[] adjList, ArrayList<Integer> res) {
        if (visited[current] != 0) {
            return;
        }
        visited[current] = 1;
        int len = adjList[current].size();
        for (int adj: adjList[current]) {
            dfs(adj, adjList, res);
        }
        // visited[current] = 2;
        res.add(current); // This will eventually be reversed, since we have added the farthest leaves first
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

/*
6 8 6
1 2
1 3
1 4
1 5
2 4
2 5
3 4
6 1
 */