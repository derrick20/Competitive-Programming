/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Girls {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        int[] usable = new int[26];
        for (int i = 0; i < N; i++) {
            String str = sc.next();

            for (char c : str.toCharArray()) {
                usable[c - 'a']++;
            }

        }

        int[] letterCt = new int[26];
        for (int i = 0; i < M; i++) {
            String str = sc.next();
            for (char c : str.toCharArray()) {
                letterCt[(c - 'a' + K) % 26]++;
            }
        }

        boolean poss = true;
        for (int i = 0; i < 26; i++) {
            if (usable[i] < letterCt[i]) {
                poss = false;
            }
        }
        out.println(!poss ? "It is gonna be daijoubu." : "Make her kokoro go doki-doki!");
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
