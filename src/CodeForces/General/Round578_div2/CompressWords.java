/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class CompressWords {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        String[] words =  new String[N];

        for (int i = 0; i < words.length; i++) {
            words[i] = sc.next();
        }

        int pointer = 0;
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < N; i++) {
            int nextPointer = 0;
            boolean cut = false;
            StringBuilder temp = new StringBuilder();
            for (int j = words[i].length() - 1; j >= pointer; j--) {
                char c = words[i].charAt(j);
                temp.append(c);
                // Contiguous adding
                if (!cut && i != N - 1 && c == words[i + 1].charAt(nextPointer)) {
                    nextPointer++;
                }
                else {
                    cut = true;
                }
            }
            pointer = nextPointer;
            ans.append(temp.reverse());
        }
        out.println(ans);
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