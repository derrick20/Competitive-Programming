import java.io.*;
import java.util.*;
/*
* HackerEarth Tutorial Tested Yay
* LEXICOGRAPHICALLY SMALLEST:
* Why? Well, we use a TreeSet to pick out the thing
* with the lowest value, so the order will be small
 */

public class topologicalSortWithInDegree {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        // Fill up adjacency list. Ugh 1-indexed
        ArrayList<Integer>[] adjList = new ArrayList[N+1];
        for (int i = 1; i <= N; i++)
            adjList[i] = new ArrayList<>();

        int[] inDegree = new int[N+1];

        for (int i = 0; i < M; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            adjList[from].add(to);
            inDegree[to]++;
        }

        // First way, using the in-degree method

        ArrayList<Integer> res = new ArrayList<>();
        boolean[] visited = new boolean[N+1];
        TreeSet<Integer> queue = new TreeSet<>(); // Not a queue per se, since we can take anything that has 0 inDeg
        // So in this case, we are handpicking to make it lexographical order
        for (int i = 1; i <= N; i++) {
            if (inDegree[i] == 0) {
                queue.add(i); // This is something that will be at the front of the topo sort
            }
        }
        // Repeatedly remove nodes with 0 inDegree and then update the inDegrees of neighbors
        // Whenever a neighbor becomes 0 inDegree, add to the queue
        // Keep track of visited nodes (those that have been added to the topo sort
        // for (int i = 0; i < N; i++)
        // if queue.isEmpty() return false; // Since we MUST have N pops if it is a valid sort...
        while (!queue.isEmpty()) {
            int front = queue.pollFirst();
            res.add(front);
            visited[front] = true;
            /*for (int i = 1; i <= N; i++) {
                System.out.print(inDegree[i]);
            }
            System.out.println();*/
            for (int neighbor: adjList[front]) {
                if (!visited[neighbor]) {
                    inDegree[neighbor]--; // We have removed something that points to it
                    // It is wasteful to search the whole array to see if something has 0 inDegree
                    // since the only way that that would occur is as a RESULT of an update. So just check
                    // during the update process
                    if (inDegree[neighbor] == 0) { // We found someone who has become useful
                        queue.add(neighbor);
                    }
                }
            }
        }

        for (int i = 0; i < N-1; i++) {
            System.out.print(res.get(i) + " ");
        }
        System.out.println(res.get(N-1));
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
