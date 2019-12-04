/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class TestGenerator {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean testing) throws Exception {
        String prefix = testing ? "/Users/derrick/IntelliJProjects/src/CodeForces/Round57/" : "";
//        sc = new FastScanner(new FileReader(prefix + problem_name + ".in"));
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    public static void main(String args[]) throws Exception {
        setupIO("InvExp", true);
        int N = 200000;
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < N; i++) {
//            if (Math.random() > 0.5) {
                arr.add(-1);
//            }
//            else {
//                arr.add(i + 1);
//            }
        }
        Collections.shuffle(arr);
        out.println(N);
        for (int val : arr) {
            out.print(val + " ");
        }
        out.println();
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