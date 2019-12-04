/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class ksums {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int c = 0; c < C; c++) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            int[] arr = new int[N];
            long[] prefix = new long[N + 1];
            for (int i = 0; i < N; i++) {
                arr[i] = sc.nextInt();
                prefix[i + 1] = prefix[i] + arr[i];
            }
            ArrayList<Pair> windows = new ArrayList<>();
            long max = (long) -1e9;
            for (int i = 1; i <= N - K + 1; i++) {
                // sum from the i th item to the K + i th item (exclude i - 1 and below)
                windows.add(new Pair(i, prefix[i + K - 1] - prefix[i - 1]));
            }
            Collections.sort(windows, new Comparator<Pair>() {
                @Override
                public int compare(Pair p1, Pair p2) {
                    // larger sum is more priority
                    if (p1.sum > p2.sum) {
                        return -1;
                    }
                    else if (p1.sum < p2.sum) {
                        return 1;
                    }
                    else {
                        // earlier is more priority
                        if (p1.pos < p2.pos) {
                            return -1;
                        }
                        else if (p1.pos > p2.pos) {
                            return 1;
                        }
                        else {
                            return 0;
                        }
                    }
                }
            });
            for (Pair p : windows) {
                out.print(p.pos + " ");
            }
            out.println();
        }
        out.close();
    }

    static class Pair {
        int pos;
        long sum;

        public Pair(int a, long b) {
            pos = a;
            sum = b;
        }
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
