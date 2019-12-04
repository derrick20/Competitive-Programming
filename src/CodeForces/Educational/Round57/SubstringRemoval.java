/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class SubstringRemoval {
    static long mod = 998244353;
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        char[] arr = sc.next().toCharArray();

        int left = 0;
        while (left < N && arr[left] == arr[0]) {
            left++;
        }
        int right = 0;
        while (right < N && arr[N - 1 - right] == arr[N - 1]) {
            right++;
        }
        long ans;
        if (arr[0] == arr[N - 1]) {
            ans = ((long) (left + 1) * (right + 1)) % mod;
        }
        else {
            ans = ((long) (left + right) + 1) % mod;
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