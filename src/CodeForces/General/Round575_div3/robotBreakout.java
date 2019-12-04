/*
 * @author derrick20
 * Issue with code: todo NEVER BREAK FROM LOOPS IF SCANNING INFORMATION
 *
 */

import java.io.*;
import java.util.*;

public class robotBreakout {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();
        while (C-->0) {
            int N = sc.nextInt();
            int xMax = (int) 1e5;
            int yMax = (int) 1e5;
            int xMin = (int) -1e5;
            int yMin = (int) -1e5;
            boolean poss = true;
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();

                int left = sc.nextInt();
                int up = sc.nextInt();
                int right = sc.nextInt();
                int down = sc.nextInt();

                if (left == 0) {
                    if (x > xMax) {
                        poss = false;
                    }
                    xMin = Math.max(xMin, x);
                }
                if (up == 0) {
                    if (y < yMin) {
                        poss = false;
                    }
                    yMax = Math.min(yMax, y);
                }
                if (right == 0) {
                    if (x < xMin) {
                        poss = false;
                    }
                    xMax = Math.min(xMax, x);
                }
                if (down == 0) {
                    if (y > yMax) {
                        poss = false;
                    }
                    yMin = Math.max(yMin, y);
                }
            }
            if (poss) {
                out.println(1 + " " + xMin + " " + yMin);
            }
            else {
                out.println(0);
            }
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
