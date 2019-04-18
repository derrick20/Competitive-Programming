import java.io.*;
import java.util.*;

public class superSale {

    static int[][] values;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int t = 0; t < T; t++) {
            int N = sc.nextInt();
            values = new int[N][2]; // Price, Weight for each object i
            for (int i = 0; i < N; i++) {
                values[i][0] = sc.nextInt();
                values[i][1] = sc.nextInt();
            }
            int F = sc.nextInt();
            int[] shoppers = new int[F];
            for (int i = 0; i < F; i++) {
                shoppers[i] = sc.nextInt();
            }

            memo = new int[N][31];
            // max weight is 30, but the number of people is from 1 to 1000, so let's not mess
            // with the value. A state is represented by the index of the item, and the amount of weight left
            for (int i = 0; i < N; i++) {
                memo[i] = new int[31];
                Arrays.fill(memo[i], -1);
            }

            int totalValue = 0;
            // solve each family member's max potential to get the answer
            for (int i  = 0; i < F; i++) {
                totalValue += solve(0, shoppers[i]);
            }
            System.out.println(totalValue);
        }
    }

    static int[][] memo; // Given a certain amount of weight possible, what is the max value we can store?
    // Since this is 0-1 knapsack, we need to know which objects we've already considered
    // thus, an organized way to do this is to add another dimension to our memo table, that is,
    // how far we have checked through all the items. This is a safe and clean solution.
    public static int solve(int itemNum, int remWeight) {
        if (remWeight == 0 || itemNum >= memo.length) {
            return 0;
        }
        // This is the key difference from complete search and DP
        else if (memo[itemNum][remWeight] != -1) {
            return memo[itemNum][remWeight];
        }
        else {
            int unused = solve(itemNum + 1, remWeight); // Represents skipping this item
            int used = 0;
            if (remWeight - values[itemNum][1] >= 0) { // Represents using this item, only if we can bear the weight
                used = values[itemNum][0] + solve(itemNum + 1, remWeight - values[itemNum][1]);
            }
            // Track this newly calculated value into our memo table
            // System.out.println(unused + " " + used + " " + remWeight);
            return memo[itemNum][remWeight] = Math.max(unused, used);
            // This final line is reach N*C times, other times, we use the memo table to lookup values!
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
