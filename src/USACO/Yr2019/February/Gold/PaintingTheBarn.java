/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class PaintingTheBarn {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean testing) throws Exception {
        String prefix = testing ? "/Users/derrick/IntelliJProjects/src/USACO/" : "";
        sc = new FastScanner(new FileReader(prefix + problem_name + ".in"));
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner(System.in);
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
        setupIO();
//      setupIO("paintbarn", false);

        int N = sc.nextInt();
        int K = sc.nextInt();
        int size = 200;
        int[][] grid = new int[size + 1][size + 1];

        // 1 extra space, because 2D range updates may have out of bounds (201)
        // We'll only be looking within 0->200, this just avoids if statements later
        for (int i = 0; i < N; i++) {
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();
            grid[x1][y1]++;
            grid[x2 ][y1]--;
            grid[x1][y2]--;
            grid[x2][y2]++;
        }
        // Update all the grid.
        for (int x = 0; x <= size; x++) {
            for (int y = 0; y <= size; y++) {
                if (x > 0) {
                    grid[x][y] += grid[x - 1][y];
                }
                if (y > 0) {
                    grid[x][y] += grid[x][y - 1];
                }
                if (x > 0 && y > 0) {
                    grid[x][y] -= grid[x - 1][y - 1];
                }

            }
        }
        int good = 0;
        for (int x = 0; x <= size; x++) {
            for (int y = 0; y <= size; y++) {
                if (grid[x][y] == K) {
                    good++;
                    grid[x][y] = -1; // If we cover this later, it's a negative cost
                }
                else if (grid[x][y] == K - 1) {
                    grid[x][y] = 1; // This is a positive place to put a rectangle
                }
                else {
                    grid[x][y] = 0; // Otherwise, we can't do anything with it
                }
            }
        }
        out.println(good);
//        for (int i = 0; i <= 10; i++) {
//            for (int j = 0; j <= 10; j++) {
//                if (grid[i][j] == 1)
//                    System.out.print("G  ");
//                else if (grid[i][j] == 0)
//                    System.out.print("0  ");
//                else
//                    System.out.print("B  ");
//            }
//            System.out.println();
//        }

        // Now, the question is reduced to finding the largest rectangle sum,
        // then update the board
        // ok, couldn't figure out the last part after. How do we determine
        // the best possible sum of two rectangles? This must be DP, since we cannot
        // greedily take the best first rectangle, as this may lead to a bad second
        // rectangle. The optimum may be a balance of two similar valued rectangles.
        // The key idea is that they give us the info that there are 2! rectangles, which
        // are disjoint. The definition of disjoint allows us to organize our answer as the
        // sum of two rectangles opposite to one line.

        for (int rowStart = 0; rowStart <= size; rowStart++) {
            for (int rowEnd = rowStart; rowEnd <= size; rowEnd++) {
                int runningSum = 0;
                for (int col = 0; col <= size; col++) {
                    while (runningSum > 0) {
                        runningSum += grid[rowEnd][col];
                        if (rowStart > 0) {
                            runningSum -= grid[rowStart - 1][col];
                        }
                        col++;
                    }
                }
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