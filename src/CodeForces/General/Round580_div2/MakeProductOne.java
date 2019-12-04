/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class MakeProductOne {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        long[] arr = new long[N];
        long cost = 0;
        int neg = 0;
        int zero = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
            if (arr[i] < 0) {
                cost += Math.abs(-1 - arr[i]);
                neg++;
            }
            else if (arr[i] > 0) {
                cost += Math.abs(arr[i] - 1);
            }
            else {
                zero++;
            }
        }
        if (neg % 2 == 1) {
            if (zero == 0) {
                cost += 2; // move a neg to pos
            }
        }
        cost += zero;
        out.println(cost);
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