/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class goomba {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int C = sc.nextInt();
        while (C-->0) {
            N = sc.nextInt();
            int Q = sc.nextInt();

            value = new int[N];
            for (int i = 0; i < N; i++) {
                value[i] = sc.nextInt();
            }
            visited = new boolean[N];
            adjList = new ArrayList[N];
            for (int i = 0; i < N - 1; i++) {
                int u = sc.nextInt() - 1;
                int v = sc.nextInt() - 1;
                adjList[u].add(v);
                adjList[v].add(u);
            }

        }
    }

    static int N;
    static int[] value;
    static boolean[] visited;
    static ArrayList<Integer>[] adjList;
    static HashMap<Pair, Integer> memo;

    static int solve(int node, int par, int divisor) {
        if (value[node] % divisor == 0 || visited[node]) {
            return 0;
        }
        else {
            int ans = 1;
            for (int adj : adjList[node]) {
                if (adj != par) {
                    ans += solve(adj, node, divisor);
                }
            }
            Pair p = new Pair(node, divisor);
            memo.put(p, ans);
            visited[node] = true;
            return ans;
        }
    }

    static int solveTree() {
        int maxSize = 0;
        for (int node = 0; node < N; node++) {
            if (!visited[node]) {
                TreeSet<Integer> divisors = new TreeSet<>();
                for (int i = 1; i <= node / 2; i++) {
                    if (node % i == 0) {
                        divisors.add(i);
                        divisors.add(node/ i);
                    }
                }
                for (int divisor : divisors) {
                    maxSize = Math.max(maxSize, solve(node, -1, divisor));
                }
            }
        }
        return maxSize;
    }

    static class Pair {
        int node, divisor;

        public Pair(int n, int d) {
            node = n;
            divisor = d;
        }

        public String toString() {
            return "(" + node + ", " + divisor + ")";
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
