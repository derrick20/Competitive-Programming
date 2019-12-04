/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class cityDay {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int X = sc.nextInt();
        int Y = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }
        int ans = 0;
        for (int i = 0; i < N; i++) {
            boolean fits = true;
            for (int j = i - 1; j >= i - X && j >= 0; j--) {
                if (arr[j] <= arr[i]) {
                    fits = false;
                }
            }
            for (int j = i + 1; j <= i + Y && j < N; j++) {
                if (arr[j] <= arr[i]) {
                    fits = false;
                }
            }
            if (fits) {
                ans = i;
                break;
            }
        }
        out.println(ans + 1);
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
