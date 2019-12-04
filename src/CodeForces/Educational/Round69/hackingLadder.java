/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class hackingLadder {
    public static void main(String args[]) throws Exception {
//        PrintWriter out = new PrintWriter(System.out);
        int end = 10;
        int size = 100000;
        System.out.println(end);
        for (int i = 1; i <= end; i++) {
            System.out.println(size);
            for (int j = 1; j <= size; j++) {
                System.out.print(j);
                if (j != size) {
                    System.out.print(" ");
                }
            }
            if (i != end)
                System.out.println();
        }
        System.out.println();
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
