// package Junior_Training.GPset1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class drink {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] prices = new int[N+1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1 ; i < N+1; i++) {
            prices[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(prices);
        int[] dp = new int[100001];
        int pos = 1;
        for (int curr = 1; curr < dp.length; curr++) {
            dp[curr] = dp[curr-1]; // We are watching the curr position of the dp
            while (pos < prices.length && prices[pos] <= curr) { // recall that && is "andif", so if the first fails, it won't try the second
                pos++;
                dp[curr]++;
            }
        }

        int Q = Integer.parseInt(br.readLine());
        for (int i = 0; i < Q; i++) {
            int x = Integer.parseInt(br.readLine());
            if (x > dp.length)
                System.out.println(dp[dp.length-1]);
            else
                System.out.println(dp[x]);
        }
    }
}
