/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class stocks {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int c = 0; c < C; c++) {
            int N = sc.nextInt();
            long[] arr = new long[N];
            for (int i = 0; i < N; i++) {
                arr[i] = sc.nextLong();
            }
            long max = 0;
            long sum = 0;
            int bestLeft = 0;
            int bestRight = 0;
            int left = 0;
            int right = 0;
            for (int j = 0; j < N; j++) {
                sum += arr[j];
                if (sum <= 0) {
                    left = j + 1;

                }
                if (sum > 0) {
                    if (sum > max) {
                        max = sum;
                        bestLeft = left;
                        bestRight = right;
                    }
                    right++;
                }
                else {
                    sum = 0;
                    left = j;
                    right = j;
                }
            }
            out.println(max + " " + (bestLeft+1) + " " + (bestRight+1));
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
