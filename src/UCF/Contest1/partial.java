/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class partial {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int i = 0; i < C; i++) {
            int N = sc.nextInt();
            String[] arr = new String[N];

            for (int j = 0; j < N; j++) {
                arr[j] = sc.next();
            }
            int ct = 0;
            for (String str : arr) {
                char[] letters = str.toCharArray();
                boolean poss = false;
                if (letters.length >= 2) {
                    for (int p = 0; p < letters.length - 1; p++) {
                        if (letters[p] == letters[p + 1]) {
                            ct++;
                            p = letters.length;
                            poss = true;
                        }
                    }
                    if (!poss) {
                        for (int p = 1; p < letters.length - 1; p++) {
                            if (letters[p - 1] == letters[p + 1]) {
                                ct++;
                                p = letters.length;
                            }
                        }
                    }
                }
            }
            out.println(ct);
        }
        out.close();
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
