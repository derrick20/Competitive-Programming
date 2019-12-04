/*
 * @author derrick20
 * Second Approach, with Max Queries after the events have been read in.
 * Different thinking style, compounding on top of work we've already done
 * to efficiently get information for each person
 */
import java.io.*;
import java.util.*;

public class alsoGoodWelfare {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] money = new int[N + 1];
        // Person i's money, 1-indexed!
        for (int i = 1; i <= N; i++) {
            money[i] = sc.nextInt();
        }

        int Q = sc.nextInt();
        int[] lastTime = new int[N + 1];
        // WE DO NOT NEED A NEW ARRAY TO STORE EXPENSE AMOUNTS
        // JUST USE THE OLD MONEY ARRAY!!!!
        // Second array stores time of the latest event

        int[] payouts = new int[Q + 1]; // at a certain time, what payout happened?
        for (int time = 1; time <= Q; time++) {
            int type = sc.nextInt();
            if (type == 1) {
                int person = sc.nextInt();
                int amount = sc.nextInt();
                money[person] = amount;
                lastTime[person] = time;
            }
            else {
                int amount = sc.nextInt();
                payouts[time] = amount;
            }
        }

        int[] suffixMax = new int[Q + 1];
        // It is a suffix because a person can only rely
        // on bailouts that happen later in time relative to their
        // last forced spending!
        // Stores the MAX amount of bailout that happens at time >= time t
        suffixMax[Q] = payouts[Q];
        for (int time = Q - 1; time >= 1; time--) {
            // We can either look to a previously discovered maximum
            // OR we could use the max available at the current time period
            // Which could likely be just 0!
            suffixMax[time] = Math.max(suffixMax[time + 1], payouts[time]);
        }
//        System.out.println(Arrays.toString(suffixMax));
//        System.out.println(Arrays.toString(money));
        for (int person = 1; person <= N; person++) {
            // we want to see, after the last forced expense, is there
            // a better payout that we can use!
            int laterPay;
            if (lastTime[person] == 0) {
                laterPay = suffixMax[1]; // Use all the time from 1 on!
            }
            else {
                laterPay = suffixMax[lastTime[person]];
            }
            out.print(Math.max(money[person], laterPay) + " ");
        }
        out.println();
        out.close();
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        FastScanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException {
            return br.readLine();
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
