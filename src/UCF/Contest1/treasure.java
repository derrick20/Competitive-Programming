/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class treasure {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int i = 0; i < C; i++) {
            int N = sc.nextInt();
            int x = 0;
            int y = 0;
            for (int j = 0; j < N; j++) {
                String dir = sc.next();
                int dist = sc.nextInt();
                if (dir.equals("NORTH")) {
                    y += dist;
                }
                else if (dir.equals("SOUTH")) {
                    y -= dist;
                }
                else if (dir.equals("EAST")) {
                    x += dist;
                }
                else if (dir.equals("WEST")) {
                    x -= dist;
                }
            }
            out.println(x + " " + y);
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
