/*
 * @author derrick20
 * Yay this was an epic solve! Basically, using sub-problems
 * Used the modular inverse and Fermat's little theorem
 * Could also use BigInteger.ModInverse
 * Also, we could've used a Pascal's Triangle table by splitting
 * the multinomials by processing it two at a time
 * So 5, 4, 3 leads to 9C4 * 12C3. Use the table to find the first pair
 * convert to 9, 3 which we then compute.
 */

import java.io.*;
import java.util.*;

public class grid {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();
        for (int c = 0; c < C; c++) {
            ROW = sc.nextInt();
            COL = sc.nextInt();
            grid = new int[ROW][COL];
            visited = new boolean[ROW][COL];
            memo = new long[ROW][COL];

            for (int i = 0; i < ROW; i++) {
                String s = sc.next();
                Arrays.fill(memo[i], -1L);
                for (int j = 0; j < COL; j++) {
                    grid[i][j] = Character.getNumericValue(s.charAt(j));
                }
            }
            possible = false;
            long ans = dfs(0, 0);
            if (ans >= (long) 1e8 || !possible) {
                ans = -1;
            }
            out.println(ans);
        }
        out.close();
    }

    static boolean[][] visited;
    static long[][] memo;
    static int[][] grid;
    static int ROW, COL;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static boolean possible;

    static long dfs(int r, int c) {
        if (r == ROW - 1 && c == COL - 1) {
            possible = true;
            return 0; // 0 more moves needed
        }
        else if (r < 0 || r >= ROW || c < 0 || c >= COL || visited[r][c]) {
            return (long) 1e9;
        }
        else if (memo[r][c] != -1) {
            return memo[r][c];
        }
        else {
            long ans = (long) 1e9;
            int weight = grid[r][c];
            visited[r][c] = true;
            for (int i = 0; i < 4; i++) {
                int r2 = r + weight * dy[i];
                int c2 = c + weight * dx[i];
                ans = Math.min(ans, 1 + dfs(r2, c2));
            }
            visited[r][c] = false;
            return memo[r][c] = ans;
        }
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
