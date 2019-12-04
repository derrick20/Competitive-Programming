/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class ChooseTwo {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] arr = new int[N];
        TreeSet<Integer> cant = new TreeSet<>();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
            cant.add(arr[i]);
        }
        int M = sc.nextInt();
        int[] arr2 = new int[M];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = sc.nextInt();
            cant.add(arr2[i]);
        }

        outer : for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!cant.contains(arr[i] + arr2[j])) {
                    out.println(arr[i] + " " + arr2[j]);
                    break outer;
                }
            }
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