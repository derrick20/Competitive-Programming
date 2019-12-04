/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class betterWelfareState {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] money = new int[N];
        for (int i = 0; i < N; i++) {
            money[i] = sc.nextInt();
        }

        int Q = sc.nextInt();
        /*
         One approach: Solving with a well-kept time-sequenced set of information
         about the most valuable "raising event" at a given time point.
         Basically, we only want to keep the most important events.
         New payout events necessarily have greater accessibility for prior points (it can
         override more forced updates)
         Thus, if it has greater value than prior raises, we can DEFINITELY delete all of those prior, lesser
         raises. On the other hand, if it has a lower value, then we still can't rule it out
         since it has greater accessibility. What results is a graph that is strictly decreasing,
         and this appropiately provides us with the necessary info to determine the
         amount of money each person has at the end. They will take the highest point to the right of them
         With this construction, any person will always take the thing immediately available in our decreasing graph
         The nearest point to the right!
         */
        int[] lastExpenseTime = new int[N];
        Arrays.fill(lastExpenseTime, -1);
        TreeMap<Integer, Integer> raiseEvents = new TreeMap<>();
        for (int i = 0; i < Q; i++) {
            int type = sc.nextInt();
            if (type == 1) {
                // This is forced, nothing we can do
                int person = sc.nextInt() - 1;
                int amount = sc.nextInt();
                money[person] = amount;
                lastExpenseTime[person] = i;
            }
            else if (type == 2) {
                // Now let's process our events to create a decreasing graph
                int amount = sc.nextInt();
                // We keep polling until we reach the base case where we have more accessibility
                // but possibly less value than previous events
                while (raiseEvents.size() > 0 && amount >= raiseEvents.lastEntry().getValue()) {
                    raiseEvents.pollLastEntry();
                }
                raiseEvents.put(i, amount);
            }
        }

        for (int person = 0; person < N; person++) {
            if (lastExpenseTime[person] == -1) {
                // If never updated, we just take the
                if (raiseEvents.firstEntry() != null) {
                    int highest = raiseEvents.firstEntry().getValue();
                    out.print(Math.max(money[person], highest) + " ");
                }
                else {
                    out.print(money[person] + " ");
                }
            }
            else {
                int timePoint = lastExpenseTime[person];
                // There is a case where we have no access to higher items
                if (raiseEvents.higherKey(timePoint) != null) {
                    int nextHighest = raiseEvents.get(raiseEvents.higherKey(timePoint));
                    out.print(Math.max(money[person], nextHighest) + " ");
                }
                else {
                    out.print(money[person] + " ");
                }
            }
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
