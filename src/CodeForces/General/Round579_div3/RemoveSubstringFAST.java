/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class RemoveSubstringFAST {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String s = "#" + sc.next() + "$";
        String pattern = "#" + sc.next() + "$";
        int[] first = new int[pattern.length()];
        Arrays.fill(first, (int) 1e6);
        int[] last = new int[pattern.length()];
        int pos = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == pattern.charAt(pos)) {
                first[pos] = Math.min(first[pos], i);
                pos++;
            }
        }
        pos = pattern.length() - 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == pattern.charAt(pos)) {
                last[pos] = Math.max(last[pos], i);
                pos--;
            }
        }
        int ans = 0;
//        System.out.println(Arrays.toString(first));
//        System.out.println(Arrays.toString(last));
        for (int i = 0; i < pattern.length() - 1; i++) {
            int gap = last[i + 1] - first[i] - 1; // How many are between them!
            ans = Math.max(ans, gap);
//            System.out.println(gap);
        }
        out.println(ans);
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
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}