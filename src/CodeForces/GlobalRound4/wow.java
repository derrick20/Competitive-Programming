/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class wow {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String s = sc.next();
        long[] prefix = new long[s.length() + 1];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i - 1) == s.charAt(i) && s.charAt(i) == 'v') {
                prefix[i + 1]++;
            }
        }
        for (int i = 1; i < prefix.length; i++) {
            prefix[i] += prefix[i - 1];
        }
        long ans = 0;
        for (int i = 2; i < s.length() - 2; i++) {
            if (s.charAt(i) == 'o') {
                long after = (prefix[s.length()] - prefix[i + 1 + 1]);
                long before = prefix[i];
                ans += after * before;
            }
        }
        out.println(ans);
        out.close();
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
