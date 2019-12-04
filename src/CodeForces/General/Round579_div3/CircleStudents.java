/*
 * @author derrick20
 * Key issue. Needed to be more greedy, don't try to work around
 * the problem statement. Do exactly what it is asking, and usually
 * you'll see that there is a simpler idea/heuristic  that enables us
 * to draw out the answer just be looping through greedily!
 */
import java.io.*;
import java.util.*;

public class CircleStudents {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int T = sc.nextInt();
        while (T-->0) {
            int N = sc.nextInt();
            ArrayList<Integer> arr = new ArrayList<>();
            int start = 0;
            for (int i = 0; i < N; i++) {
                arr.add(sc.nextInt());
                if (arr.get(i) == 1) {
                    start = i;
                }
            }
            Collections.rotate(arr, -start);
            boolean up = true;
            boolean down = true;
            for (int i = 1; i < N; i++) {
                if (arr.get(i) != i + 1) {
                    up = false;
                }
            }
            for (int i = 1; i < N; i++) {
                if (arr.get(i) != N - i + 1) {
                    down = false;
                }
            }
            out.println(up | down ? "YES" : "NO");
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
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}