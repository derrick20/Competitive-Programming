/*
 * @author derrick20
 * Couldn't solve. Issue was that I didn't limit the search by only traversing
 * the size of the DELETE ARRAY. I was going through to 1e18
 */

import java.io.*;
import java.util.*;

public class chessboardConnected {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int Q = sc.nextInt();
        while (Q-->0) {
            int B = sc.nextInt();
            int W = sc.nextInt();
            int center = Math.min(B, W);
            int edge = Math.max(B, W);
            if (3 * center + 1 < edge) {
                out.println("NO");
            }
            else {
                int cX;
                int cY;
                if (center == B) {
                    cX = 2;
                    cY = 3;
                }
                else {
                    cX = 2;
                    cY = 2;
                }
                out.println("YES");

                int x = cX;
                int y = cY;
                int placed = 0;
                while (placed < center) {
                    out.println(x + " " + y);
                    x += 2;
                    placed++;
                }
                // First place the center pieces

                // Then place the edge pieces
                // going in between each center point
                x = cX - 1;
                y = cY;
                int ct = 0;
                placed = 0;
                while (placed < center + 1 && ct < edge) {
                    out.println(x + " " + y);
                    x += 2;
                    placed++;
                    ct++;
                }
                // going above
                x = cX;
                y = cY + 1;
                placed = 0;
                while (placed < center && ct < edge) {
                    out.println(x + " " + y);
                    x += 2;
                    placed++;
                    ct++;
                }
                // going below, lastly
                x = cX;
                y = cY - 1;
                placed = 0;
                while (placed < center && ct < edge) {
                    out.println(x + " " + y);
                    x += 2;
                    placed++;
                    ct++;
                }
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
