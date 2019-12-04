import java.io.*;
import java.util.*;
/*
 * HackerEarth Big P and The Road Less Travelled
 *
 * */

public class RoadLessTraveled {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        adjList = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)
            adjList[i] = new ArrayList<>();

        while (true) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            if (a == 0)
                break;
            adjList[b].add(a); // Reverse it, for top-down purposes
        }

        memo = new int[N+ 1];
        memo[1] = 1; // Base case!
        System.out.println(solve(N));
    }

    static int[] memo;
    static ArrayList<Integer>[] adjList;
    // Top-down DP
    public static int solve(int node) {
        if (memo[node] != 0) {
            return memo[node];
        }
        int ans = 0;
        for (int adj : adjList[node]) {
            ans += solve(adj);
        }
        return memo[node] = ans;
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