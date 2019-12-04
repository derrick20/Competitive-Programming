/*
 * @author derrick20
 * Yay this was an epic solve! Basically, using sub-problems
 * Used the modular inverse and Fermat's little theorem
 * Could also use BigInteger.ModInverse
 * Also, we could've used a Pascal's Triangle table by splitting
 * the multinomials by processing it two at a time
 * So 5, 4, 3 leads to 9C4 * 12C3. Use the table to find the first pair
 * convert to 9, 3 which we then compute.
 */

import java.io.*;
import java.util.*;

public class story {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();
        inverse = new TreeMap<>();
        for (int c = 0; c < C; c++) {
            int N = sc.nextInt();
            adjList = new ArrayList[N + 1];
            visited = new int[N + 1];
            subTreeSize = new int[N + 1];

            for (int i = 1; i <= N; i++) {
                adjList[i] = new ArrayList<>();
            }
            // N-1 edges
            for (int i = 1; i <= N - 1; i++) {
                int u = sc.nextInt();
                adjList[u].add(i);
            }
            findSubTreeSizes(N);
            long res = dfs(N);
            out.println(res);
        }
        out.close();
    }

    static ArrayList<Integer>[] adjList;
    static int[] visited;
    static int[] subTreeSize;

    static int findSubTreeSizes(int node) {
        if (adjList[node].size() == 0) {
            return subTreeSize[node] = 1;
        }
        else {
            int totalSize = 0;
            for (int child : adjList[node]) {
                totalSize += findSubTreeSizes(child);
            }
            return subTreeSize[node] = 1 + totalSize;
        }
    }

    static long dfs(int node) {
        if (adjList[node].size() == 0) {
            return 1;
        }
        if (adjList[node].size() == 1) {
            return dfs(adjList[node].get(0));
        }
        else {
            long[] ways = new long[adjList[node].size()];
            int[] size = new int[ways.length];
            long ret = 1;
            int sum = 0; // only 5000 nodes
            for (int i = 0; i < ways.length; i++) {
                int child = adjList[node].get(i);
                ret = (ret * dfs(child)) % mod;
                size[i] = subTreeSize[child];
                sum += size[i];
            }
            ret = (ret * factorial(sum)) % mod;
            for (int i = 0; i < size.length; i++) {
                int f = factorial(size[i]);
                inverse.put(f, (int) fastExponentiate(f, mod - 2));
                ret = (ret * inverse.get(f)) % mod;
            }
            return ret;
        }
    }

    static int mod = (int) 1e9 + 7;
    static TreeMap<Integer, Integer> inverse;

    static long fastExponentiate(int x, int k) {
        if (k == 0) {
            return 1;
        }
        else {
            long root = fastExponentiate(x, k / 2);
            if (k % 2 == 0) {
                return (root * root + mod) % mod;
            }
            else {
                return ((x * ((root * root) % mod)) % mod + mod) % mod;
            }
        }
    }

    static int factorial(int n) {
        long ret = 1;
        while (n >= 2) {
            ret = (ret * n) % mod;
            n--;
        }
        return (int) ret % mod;
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
