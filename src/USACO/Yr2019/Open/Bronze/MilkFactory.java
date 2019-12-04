/*
 * @author derrick20
 * @problem MilkFactory 2019 USACO Bronze
 */
import java.io.*;
import java.util.*;

public class MilkFactory {

    static ArrayList<Integer>[] adjList;
    static TreeSet<Integer> unvisited;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new FileReader(new File("factory.in")));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("factory.out")));
        int N = sc.nextInt();
        unvisited = new TreeSet<>();
        adjList = new  ArrayList[N+1];
        for (int i = 1; i <= N; i++) {
            unvisited.add(i);
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < N-1; i++) { // N-1 lines follow, it's a tree
            int a = sc.nextInt();
            int b = sc.nextInt();
            adjList[b].add(a); // reverse direction
        }
        int lastSource = 0;
        while (!unvisited.isEmpty()) {
            lastSource = unvisited.pollFirst();
            dfs(lastSource);
        }
        // System.out.println(lastSource+ " " +dfs(lastSource) + " " + N);
        if (dfs(lastSource) == N)
            out.println(lastSource);
        else
            out.println(-1);
        out.close();
    }

    public static int dfs(int curr) {
        unvisited.remove(curr);
        int ans = 0;
        for (int adj : adjList[curr]) {
            ans += dfs(adj);
        }
        return 1 + ans;
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