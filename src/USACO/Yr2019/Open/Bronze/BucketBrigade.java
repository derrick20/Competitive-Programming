/*
 * @author derrick20
 * @problem BucketBrigade 2019 USACO Bronze
 * Wow took like 40 minutes too slowww
 */
import java.io.*;
import java.util.*;

public class BucketBrigade {
    static int[][] grid = new int[12][12];
    static int[][] visited = new int[12][12];
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new FileReader(new File("buckets.in")));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("buckets.out")));
        int sx = 0;
        int sy = 0;
        int ex = 0;
        int ey = 0;

        for (int i = 1; i <= 10; i++) {
            String line = sc.next();
            for (int j = 1; j <= 10; j++) {
                String c = line.substring(j-1, j);
                visited[i][j] = 0;
                if (c.equals(".")) {
                    grid[i][j] = 1;
                }
                else if (c.equals("B")) {
                    sx = i;
                    sy = j;
                    grid[i][j] = 1;
                }
                else if (c.equals("L")) {
                    ex = i;
                    ey = j;
                    grid[i][j] = 1;
                }
                else if (c.equals("R")) {
                    visited[i][j] = 1; // Again, act as if it was visited
                }
            }
        }
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, -1, 0, 1};
        LinkedList<Pair> q = new LinkedList<>();
        q.add(new Pair(sx, sy, 0));
        int dist = 0;
        while (!q.isEmpty()) {
            Pair top = q.removeFirst();
            //System.out.println(q);
            if (visited[top.x][top.y] == 1) {
                continue;
            }
            visited[top.x][top.y] = 1;
            // Exit case
            if (top.x == ex && top.y == ey) {
                dist = top.d;
                break;
            }
            for (int i = 0 ; i < 4; i++) {
                int x2 = top.x + dx[i];
                int y2 = top.y + dy[i];
                // Try each neighbor, see if unvisited, or if out of bounds (grid unitnitialized)
                if (visited[x2][y2] != 1 && grid[x2][y2] != 0) {
                    Pair nbr = new Pair(x2, y2, top.d+1);
                    q.addLast(nbr);
                }
            }
        }
        pw.println(dist-1);
        pw.close();
    }

    static class Pair {
        int x, y, d;

        public Pair(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }

        public String toString() {
            return "(" + x + ", " + y + ")" + ": " + d;
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