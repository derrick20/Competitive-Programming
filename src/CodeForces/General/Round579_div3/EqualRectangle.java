/*
 * @author derrick20
 * Again, I was not thinking greedily enough. I was trying to make it
 * have all areas possible, and counting weird cases of how many
 * sticks there are in a group. USE THE NUMBERS THEY GIVE YOU. 4N sticks
 * and N rectangles means all sticks must be in groups of 2 at least.
 * If we be even more greedy, we would imagine that
 */
import java.io.*;
import java.util.*;

public class EqualRectangle {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = sc.nextInt();
        while (T-->0) {
            int N = sc.nextInt();
            HashMap<Integer, Integer> freq = new HashMap<>();
            for (int i = 0; i < 4 * N; i++) {
                int val = sc.nextInt();
                if (freq.containsKey(val)) {
                    freq.put(val, freq.get(val) + 1);
                }
                else {
                    freq.put(val, 1);
                }
            }
            HashMap<Integer, Integer> areaFreq = new HashMap<>();
            int best = 0;
            for (int val : freq.keySet()) {
                int i = freq.get(val);
                for (int val2 : freq.keySet()) {
                    if (val2 >= val) {
                        int j = freq.get(val2);
                        int area = val * val2;
                        int ways = Math.min(i, j) / 2;
                        if (val == val2) {
                            ways /= 2;
                        }
                        if (areaFreq.containsKey(area)) {
                            areaFreq.put(area, areaFreq.get(area) + ways);
                        } else {
                            areaFreq.put(area, ways);
                        }
                        best = Math.max(best, areaFreq.get(area));
                    }
                }
            }
//            System.out.println(areaFreq);
            out.println((best >= N) ? "YES" : "NO");
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