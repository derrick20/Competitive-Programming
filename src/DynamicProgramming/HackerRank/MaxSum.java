import java.io.*;
import java.util.*;
/*
 * HackerRank Max Array Sum DP, But NONADJACENT
 * Interesting idea, but i just thought about recursive case. Either we use this one, or not.
 * What are theimplications of using this one. What does that mean the previous information we already computed can give us?
 */

public class MaxSum {

    static int[] memo; // N states, O(1) transitions)
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++)
            arr[i] = sc.nextInt();
        memo = new int[N];
        // DP part
        memo[0] = Math.max(0, arr[0]);
        memo[1] = Math.max(memo[0], arr[1]);
        for (int i = 2; i < N; i++) {
            // Either use the current, meaning you add to the best sum from -2 before,
            // OR you just use the -1th one, precluding us from including this element
            memo[i] = Math.max(memo[i-1], memo[i-2] + arr[i]);
        }
        System.out.println(memo[N-1]);
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
