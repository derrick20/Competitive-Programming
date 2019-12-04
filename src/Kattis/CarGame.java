/*
 * @author derrick20
 * Wow this is a stupid problem you HAD TO USE INDEXOF
 * IDK why but it's too slow otherwise
 */
import java.io.*;
import java.util.*;

public class CarGame {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        String[] words = new String[N];
        for (int i = 0; i < N; i++) {
            words[i] = sc.next();
        }
        for (int i = 0; i < M; i++) {
            String find = sc.next().toLowerCase();
//            char a = find.charAt(0);
//            char b = find.charAt(1);
//            char c = find.charAt(2);
            boolean found = false;
            for (String word : words) {
                int pointer = 0;
//                int p1 = word.indexOf(a);
//                int p2 = word.indexOf(b, p1 + 1);
//                int p3 = word.indexOf(c, p2 + 1);
//
//                if (p1 == -1 || p2 == -1 || p3 == -1) {
//                    continue;
//                }
//                else {
//                    out.println(word);
//                    found = true;
//                    break;
//                }
                for (char c : word.toCharArray()) {
                    if (c == find.charAt(pointer)) {
                        pointer++;
                    }
                    if (pointer == 3) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    out.println(word);
                    break;
                }
            }
            if (!found) {
                out.println("No valid word");
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