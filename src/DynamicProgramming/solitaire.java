/*
 * @author derrick20
 */
import java.awt.*;
import java.io.*;
import java.util.*;

public class solitaire {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();

        StringBuilder cards = new StringBuilder();
        for (int i = 0; i < N; i++) {
            String s = sc.next();
            cards.append(s);
        }

        memo = new HashMap<>();
        boolean ans = solve(cards, 2 * N - 1);
        out.println(ans ? "YES" : "NO");
        out.close();
    }

    static int N;
    // Shoot so hashmaps HAVE TO USE IMMUTABLES
    // SO EITHER STRINGS OR INTS OR LONGS, ETC...
    static HashMap<String, Boolean> memo;
//    static Card ZERO = new Card(0, 0);
    // Using the i indexed card, return whether we can solve it on the remaining cards
    static boolean solve(StringBuilder cards, int i) {
        if (i == 1) {
            // we finished!
            return true;
        }
        if (memo.containsKey(cards.toString())) {
            return memo.get(cards.toString());
        }
        else {
            boolean ans = false;

            StringBuilder copy = new StringBuilder(cards);
            String top = copy.substring(i - 1);
            if (copy.charAt(i) == copy.charAt(i - 2) || copy.charAt(i - 1) == copy.charAt(i - 3)) {
                // Try going placing onto the next card down
                String old = copy.substring(i - 3, i - 2 + 1);
                // delete that card and replace the one below
                copy.replace(i - 3, i - 2 + 1, top);
                ans |= solve(new StringBuilder(copy.substring(0, i - 2 + 1)), i - 2);
                // put it back, since we need to try the next case
                copy.replace(i - 3, i - 2 + 1, old);
            }
            if (i >= 7) {
                if (copy.charAt(i) == copy.charAt(i - 6) || copy.charAt(i - 1) == copy.charAt(i - 7)) {
                    // Try replacing the 3 cards down one
                    String old = copy.substring(i - 7, i - 6 + 1);
                    copy.replace(i - 7, i - 6 + 1, top);
                    // TODO AHH this line needs to be just moving over by one card, so 2 over!
                    ans |= solve(new StringBuilder(copy.substring(0, i - 2 + 1)), i - 2);
                    // put it back, since we need to try the next case
                    copy.replace(i - 7, i - 6 + 1, old);
                }
            }
//            System.out.println(memo);
            memo.put(cards.toString(), ans);
            return ans;
        }
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
