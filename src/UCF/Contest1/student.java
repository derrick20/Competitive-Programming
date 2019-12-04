/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class student {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int i = 0; i < C; i++) {
            int locations = sc.nextInt();
            int students = sc.nextInt();

            int seats = 0;
            int minSize = (int) 1e9;
            for (int j = 0; j < locations; j++) {
                int seat = sc.nextInt();
                seats += seat;
                minSize = Math.min(minSize, seat);
            }
            int multiple = (seats - students) / locations;
            out.println(Math.min(minSize, multiple));
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
