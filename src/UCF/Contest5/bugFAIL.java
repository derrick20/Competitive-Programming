/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class bugFAIL {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int C = sc.nextInt();
        while (C-->0) {
            int N = sc.nextInt();
            int[] x = new int[N];
            int[] y = new int[N];

            double max = 0;
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextInt();
                y[i] = sc.nextInt();
            }

            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    double distance = dist(x[i], y[i], x[j], y[j]);
                    if (distance > max) {
                        max = distance;
                    }
                }
            }
            out.printf("%.2f\n", max);
        }
        out.close();
    }

    static double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
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
