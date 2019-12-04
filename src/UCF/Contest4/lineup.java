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

public class lineup {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();
        for (int c = 0; c < C; c++) {
            int N = sc.nextInt();
            int H = sc.nextInt();
            A = sc.nextInt();
            adjList = new ArrayList[N];
            visited = new int[N];
            subTreeSize = new int[N];
            height = new int[N];
            height[0] = H;
            for (int i = 0; i < N; i++) {
                adjList[i] = new ArrayList<>();
            }
            for (int i = 1; i <= N - 1; i++) {
                int from = sc.nextInt();
                adjList[from].add(i);
                height[i] = sc.nextInt();
            }

            findSubTreeSizes(0);
            int ans = solve(0, 0);
            out.println(ans);
        }
        out.close();
    }

    static ArrayList<Integer>[] adjList;
    static int[] visited;
    static int[] subTreeSize;
    static int[] height;
    static int A;

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

    static int solve(int node, int h) {
        int curHeight = height[node] + h;
        if (curHeight > A) {
            return subTreeSize[node];
        }
        else {
            int ans = 0;
            for (int adj : adjList[node]) {
                ans += solve(adj, curHeight);
            }
            return ans;
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
