import java.io.*;
import java.util.*;
/*
 * HackerEarth Tutorial Tested Yay
 * * LEXICOGRAPHICALLY SMALLEST:
 * Why? Well, in this case, we pick the last node to be
 * the least priority, since the end things
 */

public class TopologicalSortWithDFS {

    static ArrayList<Integer>[] adjList;
    static int[] visited;
    static LinkedList<Integer> res;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        visited = new int[N+1];

        // Fill up adjacency list. Ugh 1-indexed
        adjList = new ArrayList[N+1];
        for (int i = 1; i <= N; i++)
            adjList[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            adjList[from].add(to);
        }
        // EXTRA PART REQUIRED BECAUSE LEXICOGRAPHICAL ORDER!!!
        // We want to have higher numbers as late as possible,
        // and since we are traversing in reverse order, we will
        // thus be adding the largest parts to the linked list earlier
        for (int i = 1; i <= N; i++)
            adjList[i].sort(Comparator.reverseOrder());
        res = new LinkedList<>();

        // Since we want LEXICOGRAPHICALLY smallest TopoSort, go from the back to front,
        // That way node 1 will always come last, and thus be placed as early as possible
        for (int i = N; i >= 1; i--) {
            dfs(i);
        }

        ListIterator<Integer> it = res.listIterator();

        for (int i = 0; i < N-1; i++) {
            System.out.print(it.next() + " ");
        }
        System.out.println(it.next());
    }


    // Basic idea: to toposort, we dfs through our neighbors, afterwards adding
    // ourselves to the stack of the sorted list. The base case is a leaf, which is guaranteed
    // to be at the end. Also, the nodes with 0 inDegree are also a sort of edge case, since
    // they'll always be at the front area
    public static void dfs(int node) {
        if (visited[node] == 0) {
            visited[node] = 1; // If not visited, visit it and mark that down
            for (int nbr : adjList[node]) {
                dfs(nbr);
            }
            // Once everything's been dfs'd then we can properly add ourselves to the front of the sort
            res.addFirst(node);
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
