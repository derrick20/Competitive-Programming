import java.io.*;
import java.util.*;
/*
 * Codeforces Round 360 Div. 2 A
 */

public class opponents {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int d = sc.nextInt();
        int n = sc.nextInt();
        int[] arr = new int[n];
        int curr = 0;
        int best = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = sc.next().contains("0") ? 1 : 0;
            if (arr[i] == 1) {
                curr++;
            }
            else {
                best = Math.max(best, curr);
                curr = 0;
            }
        }
        best = Math.max(best, curr);
        System.out.println(best);
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
