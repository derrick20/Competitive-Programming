import java.io.*;
import java.util.*;
/*
 * Codeforces Round 360 Div. 2 E
 */

public class makeValues {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }
        Arrays.sort(arr);
        memo = new int[N][K+1][K+1]; // max sum is K
        // Bottom-up DP Time
        // State (O(NK^2): first i coins used, sum j has been constructed, sum k of sum j is constructed currently (possible or not)
        // Base Case: 0, 0, 0 is given as true
        // Transition: Skip coin, use coin to add to j sum, or use to add to both j and k sums.
        memo[0][0][0] = 1; // Use the first
        memo[0][arr[0]][0] = 1;
        memo[0][arr[0]][arr[0]] = 1;
        // so with i = 1 done, let's proceed
        for (int i = 1; i < N; i++) {
            for (int j = 0; j <= K; j++) {
                for (int k = 0; k <= K; k++) {
                    memo[i][j][k] += memo[i-1][j][k];
                    // then we can reach this state from 0 sum
                    if (j - arr[i] >= 0) {
                        memo[i][j][k] += memo[i-1][j - arr[i]][k];
                        if (k - arr[i] >= 0) {
                            memo[i][j][k] += memo[i-1][j - arr[i]][k - arr[i]];
                        }
                    }
                }
            }
        }
        TreeSet<Integer> res = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            int j = K;
            for (int k = 0; k <= K; k++) {
                if (memo[i][j][k] != 0) {
                    res.add(k);
                }
            }
        }

        System.out.println(res.size());
        for (int val : res) {
            System.out.print(val + " ");
        }
    }
    static int N, K;
    static int[] arr;
    static int[][][] memo;

// FATAL ERROR: used a set when really there can obviously be repeat values fuuk
// SHOOT another issue would be 1+5 and 0+6 gives the same state, but they're different still, How'dyou DP it then?!


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
