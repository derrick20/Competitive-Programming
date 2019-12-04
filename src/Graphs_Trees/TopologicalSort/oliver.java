import java.io.*;
import java.util.*;
/*
 * HackerEarth Oliver and the Game
 * This literally should work. I learned what the value was of arrival and departure time
 * for dfs, so idk i'll move on. Come back to do LCA when you learn that TOO!
 * */

public class oliver {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        adjList = new ArrayList[N+1];
        for (int i = 0; i <= N; i++)
            adjList[i] = new ArrayList<>();


        for (int i = 0; i < N-1; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            // System.out.println(i + " " + from + " " + to);
            adjList[from].add(to);
        }


        time = 0;
        arrTime = new int[N+1];
        depTime = new int[N+1];
        visited = new int[N+1];
        // Don't need visited array, since it's a tree! (no loops)
        dfs(1);

        // Use the idea of arrival time:
        // If something arrives after and departs before, it is on our path.
        // Else. it is in some other branch, and is not reachable by moving towards the root
        /*for (int i = 1; i <= N; i++) {
            System.out.println("ARR:  " + i + " " + arrTime[i]);
            System.out.println("DEP:  " + i + " " + depTime[i]);

        }*/

        int M = sc.nextInt();
        for (int i = 0; i < M; i++) {
            int away = sc.nextInt();
            int x = sc.nextInt();
            int y = sc.nextInt();
            // Bob is Y, so if we are moving towards root, then X must arrive before and depart after
            boolean ret = false;
            if (away == 0 && arrTime[y] > arrTime[x] && depTime[y] < depTime[x]) {
                ret = true; // We can reach him, assuming we are moving toward/upward. When we are a subset of them,
            } // we are within their journey and are inside their branch solely
            else if (away == 1 && arrTime[y] < arrTime[x] && depTime[y] > depTime[x]) {
                ret = true; // inverse the statement. We want y, (Bob), to be above so that he can move down
            }
            System.out.println(ret ? "YES" : "NO");
        }
    }
    static ArrayList<Integer>[] adjList;
    static int time;
    static int[] arrTime, depTime, visited;
    public static void dfs(int curr) {
        arrTime[curr] = time;
        time++;
        visited[curr] = 1;
        // System.out.println(curr);
        // If we have reached a leaf, skip this, and go up the recursion tree
        for (int adj : adjList[curr]) {
            if (visited[adj] != 1) { // it's better to cut visited here, since it saves one level of overhead
                // from the recursion calls
                dfs(adj);
                time++;
            }
        }
        depTime[curr] = time;
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
