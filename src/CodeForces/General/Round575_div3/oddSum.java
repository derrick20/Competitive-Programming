/*
 * @author derrick20
 * Couldn't solve. Issue was that I didn't limit the search by only traversing
 * the size of the DELETE ARRAY. I was going through to 1e18
 */

import java.io.*;
import java.util.*;

public class oddSum {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int Q = sc.nextInt();
        while (Q-->0) {
            int N = sc.nextInt();
            int[] nums = new int[N];
            int K = sc.nextInt();
            long sum = 0;
            int ct = 0;
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < N; i++) {
                nums[i] = sc.nextInt();
                sum += nums[i];
                // greedily stop if it's odd (but don't stop if we reached threshold already)
                if (sum % 2 == 1 && ct != K) {
                    res.append((i + 1) + " ");
                    sum =  0;
                    ct++;
                }
            }

            if (ct != K) {
                out.println("NO");
            }
            else {
                out.println("YES");
                out.println(res.substring(0, res.length() - 1));
            }
        }
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
