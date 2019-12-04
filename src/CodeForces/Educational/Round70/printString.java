/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class printString {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        // 11 33 7777777  1111111 33 777777
        //  2      d         a          b
        // 2 * (d + 4C2 * b) + a * b
        // = b * (a + 12) + 2d
        while (T-->0) {
            int X = sc.nextInt();
            int b = (int) Math.sqrt(X);
            int scraps = X - b*b;
            // We need an even number, to get rid of the big weight
            if (scraps % 2 != 0) {
                b--;
                scraps = X - b*b; // delta from the old scraps is an odd number, so parity is good now!
            }
            int a = b - 12;
            scraps /= 2; // now we can safely do this
//            System.out.println("a: " + a + " b: " + b + " d: " + scraps);

            StringBuilder ans = new StringBuilder();
            if (a < 0) {
                ans.append("133");
                for (int i = 0; i < X; i++) {
                    ans.append('7');
                }
            }
            else {
                ans.append("1133");
                for (int i = 0; i < scraps; i++) {
                    ans.append('7');
                }
                for (int i = 0; i < a; i++) {
                    ans.append('1');
                }
                ans.append("33");
                for (int i = 0; i < b; i++) {
                    ans.append('7');
                }
            }
            out.println(ans);
            out.println(count(ans.toString(), "1337", ans.length(), 4));
        }
        out.close();
    }

    static int count(String a, String b, int m, int n)
    {
        // If both first and second string is empty,
        // or if second string is empty, return 1
        if (m == 0 || n == 0)
            return 1;

        // If only first string is empty and
        // second string is not empty, return 0
        if (m == 0)
            return 0;

        // If last characters are same
        // Recur for remaining strings by
        // 1. considering last characters of
        // both strings
        // 2. ignoring last character of
        // first string
        if (a.charAt(m - 1) == b.charAt(n - 1))
            return count(a, b, m - 1, n - 1) + count(a, b, m - 1, n);
        else
            // If last characters are different,
            // ignore last char of first string
            // and recur for  remaining string
            return count(a, b, m - 1, n);
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
