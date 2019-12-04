/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class BlockAdventure {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        while (T-->0) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            int K = sc.nextInt();
            int[] height = new int[N];
            for (int i = 0; i < height.length; i++) {
                height[i] = sc.nextInt();
            }
            int bag = M;
            boolean poss = true;
            for (int i = 1; i < N; i++) {
                int diff = height[i] - height[i - 1];
                if (diff < K) {
                    // Either take the remaining, or enough to get to K diff
                    bag += Math.min(K - diff, height[i - 1]);
                }
                else {
                    int placed = diff - K;
                    bag -= placed;
                    if (bag < 0) {
                        poss = false;
                        break;
                    }
                }
            }
            out.println(poss ? "YES" : "NO");
        }
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