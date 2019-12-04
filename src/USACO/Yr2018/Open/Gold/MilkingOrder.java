/*
 * @author derrick20
 * @problem MilkingOrder USACO 2018 Gold Open
 * Ok, got like 80% of the hard insights down. Just didn't realize the binary search idea hmmm
 * Also, read the problem statement way wrong, so that would've been a big time sink. If you figured
 * out something smart/observation, i think it's fine to cut to the answers because you did the thinking part
 * The implementation part/skill should come naturally along later, the hard part is the insight.
 * Need to think harder about the big O efficiency to determine how to approach the problem
 *
 * If you EVER see anything about finding the optimum within a range of 0 to N, then you can do binary sort,
 * if it is increasing. DO IT LATER!
 *
 * Ayy came back and I knew EXACTLY HOW TO DO IT
 * ARGH I was stupid though NEVER USE TOP SORT WITH DFS. Always do inDegree!!!
 */
import java.io.*;
import java.util.*;

public class MilkingOrder {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name) throws Exception {
        sc = new FastScanner(new FileReader(problem_name + ".in"));
        out = new PrintWriter(new FileWriter(problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner(System.in);
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
//        setupIO();
        setupIO("milkorder");

        N = sc.nextInt();
        int M = sc.nextInt();
        ordering = new ArrayList[M];

        for (int i = 0; i < M; i++) {
            ordering[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            int size = sc.nextInt();
            for (int j = 0; j < size; j++) {
                int node = sc.nextInt() - 1;
                ordering[i].add(node);
//                if (j > 0) {
//                    ordering[nodes[j - 1]].add(nodes[j]);
//                }
            }
        }

        int obs = binarySearch(0, M - 1); // This automatically sets up the adjList
//        System.out.println(adjList);
        System.out.println(obs);
        isValid(obs);
        for (int i = 0; i < N; i++) {
            Collections.sort(adjList[i], Collections.reverseOrder());
        }
//        System.out.println(obs + 1);
//        topologicalSort();

        int[] inDegree = new int[N];

        for (int i = 0; i < N; i++) {
            for (int adj : adjList[i]) {
                inDegree[adj]++;
            }
        }

        // First way, using the in-degree method

        ArrayList<Integer> res = new ArrayList<>();
        boolean[] visited = new boolean[N];
        TreeSet<Integer> queue = new TreeSet<>(); // Not a queue per se, since we can take anything that has 0 inDeg
        // So in this case, we are handpicking to make it lexographical order
        for (int i = 0; i < N; i++) {
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

        for (int i = 0; i < res.size(); i++) {
            int node = res.get(i) + 1;
            out.print(node);
            if (i < res.size() - 1) {
                out.print(" ");
            }
        }
        out.println();
        out.close();
    }

    static int N;
    static ArrayList<Integer> res;
    static ArrayList<Integer>[] ordering;
    static ArrayList<Integer>[] adjList;
    static int[] visited;

    static boolean containsCycle(int node) {
        boolean ret = false;
        if (visited[node] == 1) {
            // We intersected something still dfs'ing, so FAIL
            return true;
        }
        else if (visited[node] == 2) {
            return false;
        }
        else {
            visited[node] = 1;
            for (int adj : adjList[node]) {
                ret |= containsCycle(adj);
            }
            visited[node] = 2;
        }
        return ret;
    }
    /*
4 5
3 1 2 3
2 4 2
1 4
2 1 4
3 4 3 2
     */

    static boolean isValid(int k) {
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            // We want our ultimate topo sort to go to the big things first, so that when we flip
            // the dfs order they'll be at the back
            adjList[i] = new ArrayList<>();
        }
        for (int i = 0; i <= k; i++) {
            for (int j = 1; j < ordering[i].size(); j++) {
                int par = ordering[i].get(j - 1);
                int curr = ordering[i].get(j);
                adjList[par].add(curr);
            }
        }
        visited = new int[N];
        for (int node = 0; node < N; node++) {
            if (visited[node] == 0) {
                // No need to pass around the visited/adjList since the last one CREATED will be the one we're using
                if (containsCycle(node)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Goal: find the LAST K that allows it to be valid
    // 11110000. Find the last 1
    static int binarySearch(int lo, int hi) {
        while (lo < hi) {
            int mid = (hi - lo) / 2 + lo + 1; // CRUCIAL +1 here. Once we reach a 10 case,
            // we want to be at the 0, then shrink the interval to be size 1 -- meaning lo = hi!

            // Allow us to use UP TO the k-indexed observation
            // Our goal is to converge on an interval that holds the LAST correct answer. Our high keeps dropping
            // but we preserve a valid answer in our lo
            if (isValid(mid)) {
                lo = mid;
            }
            else {
                // Otherwise, too high, so go down strictly
                hi = mid - 1;
            }
        }
        return lo;
    }

    static void topologicalSort() {
        res =  new ArrayList<>();
        // Once we reach the point where we binary searched, our topo sort will have the right
        // adjList globally available
        visited = new int[N];
        // This ordering ensure lexicographically smallest first!
        for (int node = N - 1; node >= 0; node--) {
            if (visited[node] == 0) {
                dfs(node);
            }
        }
        Collections.reverse(res);
    }

    static void dfs(int node) {
        // since adjList is tree set, it's gonna sort for us
        if (visited[node] == 0) {
            visited[node] = 1;
            for (int adj : adjList[node]) {
                dfs(adj);
            }
            res.add(node);
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
