import java.io.*;
import java.util.*;
/*
 * CodeForces GoodBye2017 ArbitraryArrangement
 * @derrick20
 */

public class ArbitraryArrangement {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int ct = 0;
        for (char c : s.toCharArray()) {
            if ("13579aeiou".contains(c+"")) {
                ct++;
            }
        }
        System.out.println(ct);

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

