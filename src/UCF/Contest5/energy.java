/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class energy {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int C = sc.nextInt();
        while (C-->0) {
            N = sc.nextInt();
            memo = new int[N];
            value = new int[N];
            for (int i = 0; i < N; i++) {
                value[i] = sc.nextInt();
            }
            out.println(solve(0));
        }
        out.close();
    }
    static int N;
    static int[] memo;
    static int[] value;

    static int solve(int i) {
        if (i >= N) {
            return 0;
        }
        if (memo[i] != 0) {
            return memo[i];
        }
        int ans = value[i]; // no more jumping
        for (int jump = 3; jump + i < N; jump++) {
            ans = Math.max(ans, value[i] + solve(i + jump));
        }
        for (int skip = 1; skip + i < N; skip++) {
            ans = Math.max(ans, solve(i + skip));
        }
        return memo[i] = ans;
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
