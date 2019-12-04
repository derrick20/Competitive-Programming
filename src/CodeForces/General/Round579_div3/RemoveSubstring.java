/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class RemoveSubstring {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        s = sc.next();
        pattern = sc.next();
        int ans = binarySearch(0, s.length());
        out.println(ans);
        out.close();
    }

    static String s, pattern;

    // Binary search the highest length doable
    // 1111000 - 10

    static boolean doable(int len) {
        // Try skipping each possible gap
        boolean poss = false;
        for (int i = 0; i <= s.length() - len; i++) {
            String search = s.substring(0, i).concat(s.substring(i + len));
//            System.out.println(i + " "+ s.substring(0, i)  + s.substring(i + len));
            int pos = 0;
            for (char c : search.toCharArray()) {
                if (pattern.charAt(pos) == c) {
                    pos++;
                    if (pos == pattern.length()) {
                        poss = true;
                        break;
                    }
                }
            }
        }
        return poss;
    }
    static int binarySearch(int lo, int hi) {
        while (lo < hi) {
            int mid = (lo + hi) / 2 + 1;
            if (doable(mid)) {
                lo = mid;
            }
            else {
                hi = mid - 1;
            }
        }
        return lo;
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
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}