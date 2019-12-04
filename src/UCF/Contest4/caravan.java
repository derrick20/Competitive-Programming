/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class caravan {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        while (C-->0) {
            int N = sc.nextInt();
            int aCap = sc.nextInt();
            int sCap = sc.nextInt();
            int mCap = sc.nextInt();

            int[][] dist = new int[5][5];
            for (int i = 1; i < 5; i++) {
                for (int j = 1; j < 5; j++) {
                    dist[i][j] = sc.nextInt();
                }
            }

            int[][] startTime = new int[6][3];
            int[] remStudents = new int[6];
            int firstDrop = 0;
            // 2-3-4
            startTime[0][0] = dist[1][2] + dist[2][1];
            startTime[0][1] = dist[1][2] + dist[2][3] + dist[3][1];
            firstDrop = startTime[0][2] = dist[1][2] + dist[2][3] + dist[3][4] + dist[4][1];
            remStudents[firstDrop] =
            // 2-4-3
            startTime[1][0] = dist[1][2] + dist[2][1];
            startTime[1][1] = dist[1][2] + dist[2][4] + dist[4][1];
            startTime[1][2] = dist[1][2] + dist[2][4] + dist[4][3] + dist[3][1];
            // 3-2-4
            startTime[2][0] = dist[1][3] + dist[3][1];
            startTime[2][1] = dist[1][3] + dist[3][2] + dist[2][1];
            startTime[2][2] = dist[1][3] + dist[3][2] + dist[2][4] + dist[4][1];
            // 3-4-2
            startTime[3][0] = dist[1][3] + dist[3][1];
            startTime[3][1] = dist[1][3] + dist[3][4] + dist[4][1];
            startTime[3][2] = dist[1][3] + dist[3][4] + dist[4][2] + dist[2][1];
            // 4-2-3
            startTime[4][0] = dist[1][4] + dist[4][1];
            startTime[4][1] = dist[1][4] + dist[4][2] + dist[2][1];
            startTime[4][2] = dist[1][4] + dist[4][2] + dist[2][3] + dist[3][1];
            // 4-3-2
            startTime[5][0] = dist[1][4] + dist[4][1];
            startTime[5][1] = dist[1][4] + dist[4][3] + dist[3][1];
            startTime[5][2] = dist[1][4] + dist[4][3] + dist[3][2] + dist[2][1];


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
