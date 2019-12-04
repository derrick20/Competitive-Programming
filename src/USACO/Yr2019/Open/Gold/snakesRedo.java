import java.io.*;
import java.util.*;

public class snakesRedo {
    static int[] sum;
    static int[] snakes;
    static int N, K;
    static int[][] memo;

    public static void main(String[] args) throws Exception {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new FileReader("snakes.in"));//
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("snakes.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = 1 + Integer.parseInt(st.nextToken()); // we can CHANGE K times, so K+1 nets

        snakes = new int[N];
        sum = new int[N+1]; // sum[i] = sum of the first i terms
        memo = new int[N][K+1];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            snakes[i] = Integer.parseInt(st.nextToken());
            sum[i+1] = sum[i] + snakes[i];
            for (int j = 0; j < K + 1; j++) {
                memo[i][j] = -1;
            }
        }

        int ans = solve(0, K);
        /*for (int i = 0; i < N; i++) {
            for (int j = 0; j < K+1; j++) {
                System.out.print(memo[i][j] + " ");
            }
            System.out.println();
        }*/
        out.println(ans);
        out.close();
    }
    static int inf = (int) 1e9; // Infinity
    static int solve(int i, int nets) {
        if (i == N) {
            return 0;
        }
        if (nets == 0) {
            return inf; // this is bad, so we can't use this state
        }
        // USE INFORMATION STORED!
        if (memo[i][nets] != -1)
            return memo[i][nets];
        int minWaste = inf;
        int max = snakes[i];
        // next is the last position our net will ensnare.
        // We need to find the max snake from i to next inclusive, and calculate
        // the waste using the difference between sum and the nets used
        for (int next = i; next < N; next++) {
            max = Math.max(max, snakes[next]);
            // the prefix sum[next+1] is the sum from 0 to next. ('next' number of terms)
            int waste = max * (next - i + 1) - (sum[next + 1] - sum[i]);
            waste += solve(next + 1, nets - 1);
            // We need to go from next down to i, so +1. Also, the sum should include i, so -1 again
            minWaste = Math.min(minWaste, waste);
        }
        return memo[i][nets] = minWaste;
    }
}