/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class spiderman {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int C = sc.nextInt();
        while (C-->0) {
            N = sc.nextInt();
            cap = sc.nextInt();
            memo = new int[N];
            value = new int[N];
            weight = new int[N];
            prefixWt = new long[N + 1];
            long[] prefixVal = new long[N + 1];
            for (int i = 0; i < N; i++) {
                value[i] = sc.nextInt();
                prefixVal[i + 1] = prefixVal[i] + value[i];
            }
            for (int i = 0; i < N; i++) {
                weight[i] = sc.nextInt();
                prefixWt[i + 1] = prefixWt[i] + weight[i];
            }
            long max = 0;
            for (int left = 0; left < N; left++) {
                int right = binarySearch(left, N - 1, left);
                // there are certain cases where it didn't check
                if (prefixWt[right + 1] - prefixWt[left] <= cap) {
                    long value = prefixVal[right + 1] - prefixVal[left];
                    max = Math.max(max, value);
                }
            }
            out.println(max);
        }
        out.close();
    }
    static int N;
    static int cap;
    static int[] memo;
    static int[] value;
    static int[] weight;
    static long[] prefixWt;

    static int binarySearch(int lo, int hi, int left) {
        while (lo < hi) {
            int mid = (lo + hi) / 2 + 1;
            if (prefixWt[mid + 1] - prefixWt[left] > cap) {
                hi = mid - 1; // we know it won't work
            }
            else {
                lo = mid; // this works for sure
            }
        }
        return lo;
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
