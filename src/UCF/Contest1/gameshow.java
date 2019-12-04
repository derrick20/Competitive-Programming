/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class gameshow {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();
        for (int i = 0; i < C; i++) {
            int N = sc.nextInt();
            long[] num = new long[N];
            long[] denom = new long[N];

            for (int j = 0; j < N; j++) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                int m = sc.nextInt();
                num[j] = (2*a - b) * m;
                denom[j] = b;
            }
            long lcm = denom[0];
            for (int j = 1; j < N; j++) {
                lcm = lcm * denom[j] / (GCD(lcm, denom[j]));
            }

            for (int j = 0; j < N; j++) {
                num[j] = (lcm / denom[j]) * num[j];
            }

            long max = 0;
            long sum = 0;
            for (int j = 0; j < N; j++) {
                sum += num[j];
                if (sum > 0) {
                    max = Math.max(max, sum);
                }
                else {
                    sum = 0;
                }
            }
//            out.println("Case " + i);
            if (max == 0) {
                out.println("0/1");
            }
            else {
                String top = "" + max / GCD(max, lcm);
                String bot = "" + lcm / GCD(max, lcm);
                out.print(top + "/" + bot);
            }
            if (i < C - 1) {
                out.println();
            }
        }
        out.close();
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static long GCD(long a, long b) {
        if (a < b) {
            // do a swap so a >= b
            a += b;
            b = a - b;
            a -= b;
        }

        // the lesser number will always be b in each loop
        while (b != 0) {
            a %= b;

            a += b;
            b = a - b;
            a -= b;
        }
        return a;
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
