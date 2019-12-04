/*
 * @author derrick20
 * Key Ideas: The indexing and min possible and max possible gets confusing when considering
 * many updates. Rather, it's simpler to notice that we are doing the - K + 1, and max and min
 * methods repeatedly. To simplify this, we can just find the earliest and latest black squares
 * in every line. Then, only at the end when we are actually storing the values do we
 * apply the - K + 1. This is doing the same thing, since earlier we were just minimizing
 * a shifted version of these leftmost and rightmost pointers! Make sure the initial values of the
 * maximizing/minimizing are correct. This can be mitigated again by separating the optimization
 * process into two steps. Finding the two black bounding squares, THEN applying the shifts to them!
 *
 * Another key idea is that we can consider the two searches independently - the grid
 * will have numerous overlapping rectangular ranges within which a click would lead
 * to a cleaned line somewhere, either a row or col. It doesn't matter which though,
 * because when we add it all up we just want to maximize the total count, not worrying about
 * where they are. Also, don't forget about the initial white lines.
 */
import java.io.*;
import java.util.*;

public class WhiteLines {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int K = sc.nextInt();
        int clickRange = N - K + 1;
        int[] rowMin = new int[N];
        int[] rowMax = new int[N];
        int[] colMin = new int[N];
        int[] colMax = new int[N];
        Arrays.fill(rowMax, 2 * N);
        Arrays.fill(colMax, 2 * N);

        for (int r = 0; r < N; r++) {
            String row = sc.next();
            for (int c = 0; c < N; c++) {
                if (row.charAt(c) == 'B') {
                    // We are forced to click at least K within the HIGHEST black square
                    rowMin[r] = Math.max(rowMin[r], c - K + 1);
                    // We can't click beyond the LOWEST BLACK square
                    rowMax[r] = Math.min(rowMax[r], c);
                    colMin[c] = Math.max(colMin[c], r - K + 1);
                    colMax[c] = Math.min(colMax[c], r);
                }
            }
        }
        int initialWhite = 0;
        for (int pos = 0; pos < N; pos++) {
            if (rowMin[pos] == 0 && rowMax[pos] == N) {
                initialWhite++;
            }
            if (colMin[pos] == 0 && colMax[pos] == N) {
                initialWhite++;
            }
        }
        int[][] cleared = new int[N + 1][N + 1];
        // Using the 2D prefix sums, we will add in these deltas,
        // which represent the key corners of a rectangle within which
        // clicking leads to some line removal. We can look at each row
        // and column's zone of clickability, since they will be added independently!
//        System.out.println("ROWS");
        for (int r = 0; r < N; r++) {
            int range = rowMax[r] - rowMin[r] + 1;
            if (1 <= range && range <= K) {
                int minCol = rowMin[r];
                int maxCol = rowMax[r];
                int minRow = Math.max(0, r - K + 1);
                int maxRow = r;
                // Top Left - propagates a one down and right.
                cleared[minRow][minCol]++;
                // Diagonals - cancels the plus ones beyond this in x and y directions
                cleared[minRow][maxCol + 1]--;
                cleared[maxRow + 1][minCol]--;
                // Bottom Right - cleans out the double negative
                cleared[maxRow + 1][maxCol + 1]++;
//                display(cleared);
            }
        }
//        System.out.println("COLUMNS");
        for (int c = 0; c < N; c++) {
            int range = colMax[c] - colMin[c] + 1;
            if (1 <= range && range <= K) {
                int minRow = colMin[c];
                int maxRow = colMax[c];
                int minCol = Math.max(0, c - K + 1);
                int maxCol = c;
                // Top Left - propagates a one down and right.
                cleared[minRow][minCol]++;
                // Diagonals - cancels the plus ones beyond this in x and y directions
                cleared[minRow][maxCol + 1]--;
                cleared[maxRow + 1][minCol]--;
                // Bottom Right - cleans out the double negative
                cleared[maxRow + 1][maxCol + 1]++;
//                display(cleared);
            }
        }
        int maxCleared = cleared[0][0]; // Just base case
        for (int r = 0; r < clickRange; r++) {
            for (int c = 0; c < clickRange; c++) {
                if (r == 0 && c == 0) continue;
                if (r > 0) {
                    cleared[r][c] += cleared[r - 1][c];
                }
                if (c > 0) {
                    cleared[r][c] += cleared[r][c - 1];
                }
                if (r > 0 && c > 0) {
                    cleared[r][c] -= cleared[r - 1][c - 1];
                }
                maxCleared = Math.max(maxCleared, cleared[r][c]);
            }
        }
//        display(cleared);
        out.println(initialWhite + maxCleared);
        out.close();
    }

    static void display(int[][] arr) {
        for (int r = 0; r < arr.length; r++) {
            System.out.println(Arrays.toString(arr[r]));
        }
        System.out.println();
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