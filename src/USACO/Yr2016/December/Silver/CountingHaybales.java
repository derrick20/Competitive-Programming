/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class CountingHaybales {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean testing) throws Exception {
        String prefix = testing ? "/Users/derrick/IntelliJProjects/src/USACO/" : "";
        sc = new FastScanner(new FileReader(prefix + problem_name + ".in"));
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner(System.in);
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
        setupIO();
        setupIO("haybales", false);

        int N = sc.nextInt();
        int Q = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }
        Arrays.sort(arr);
        TreeMap<Integer, Integer> prefix = new TreeMap<>();
        int last = 0;
        for (int val : arr) {
            prefix.put(val, ++last);
        }
        prefix.put(-1, 0); // need a lower bound
        // Bruh, this is literally just subtracting the indices of them
        // That represents the space between them
        while (Q-->0) {
            int l = sc.nextInt();
            int r = sc.nextInt();
            int ans = prefix.get(prefix.lowerKey(r + 1)) - prefix.get(prefix.lowerKey(l));
            out.println(ans);
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