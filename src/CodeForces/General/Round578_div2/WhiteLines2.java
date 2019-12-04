/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class WhiteLines2 {
    public static void main(String args[]) throws Exception {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        in.nextLine();
        int[] lx = new int[n];
        int[] rx = new int[n];
        boolean[] rowHasBlack = new boolean[n];
        int[] ly = new int[n];
        int[] ry = new int[n];
        boolean[] columnHasBlack = new boolean[n];
        for (int x = 0; x < n; x++) {
            ly[x] = 0;
            ry[x] = n - 1;
        }
        for (int y = 0; y < n; y++) {
            lx[y] = 0;
            rx[y] = n - 1;
            String line = in.nextLine();
            for (int x = 0; x < n; x++) {
                if (line.charAt(x) == 'B') {
                    rowHasBlack[y] = true;
                    columnHasBlack[x] = true;
                    // For some black square, we want to bound the region
                    // within which we may click to result in all black squares being
                    // erased
                    // So, we want the left bounds to be anywhere less within K,
                    // or if that's negative, just take this position
                    // We always started with 0, n-1 as the default above.
                    // Thus, anytime we see some kind of square, and it is farther
                    // along than previous, we MUST click at it's lower border. This
                    // is necessary if we are to also erase it. However, if these left and right
                    // bounds pass each other, it becomes impossible

                    // A good way to visualize is having each black square having a sphere of available
                    // locations to clickl. (which starts K before and ends there) If we take the
                    // intersection of all of these available areas, this amounts to seeing if there
                    // exists a range in which we can click to clean them all out. If l > r, this means
                    // the minimum position to click to reach the rightmost edge is FARTHER ALONG THAN
                    // the earliest tile. Thus, we can't haev everything, our range is stretched too thin!
                    lx[y] = Math.max(lx[y], x - k + 1);
                    rx[y] = Math.min(rx[y], x);
                    ly[x] = Math.max(ly[x], y - k + 1);
                    ry[x] = Math.min(ry[x], y);
                }
            }
        }
        int initial = 0;
        for (int i = 0; i < n; i++) {
            if (!rowHasBlack[i]) {
                initial++;
            }
            if (!columnHasBlack[i]) {
                initial++;
            }
            System.out.println("lx[" + i + "] = " + lx[i] + ", rx[" + i + "] = " + rx[i] + ", ly[" + i + "] = " + ly[i] + ", ry[" + i + "] = " + ry[i]);
        }
        int answer = initial;
        System.out.println("initial" + initial);
        for (int y = 0; y <= n - k; y++) {
            // Now we must go along the column of our click space and see what x's
            //
            int[] diffs = new int[n + 1];
            for (int yp = y; yp < y + k; yp++) {
                // Mark the two edges of the valid places where we may
                // click within the row y,
                if (rowHasBlack[yp] && lx[yp] <= rx[yp]) {
                    diffs[lx[yp]]++;
                    diffs[rx[yp] + 1]--;
                }
            }
            System.out.println("y: " + y + " " + Arrays.toString(diffs));

            int curr = initial;
            // Go through the click space along this row, and as long as we are within the required
            // y intersection space for this x (column), that means that we have
            // enough range to snag a line!

            // However, we need to know for all x start points, how many
            // columns do we get! Let's start with a base case for the 0th column as click point
            for (int x = 0; x < k; x++) {
                if (columnHasBlack[x] && ly[x] <= y && y <= ry[x]) {
                    curr++;
                }
            }
            curr += diffs[0];
            System.out.println("y = " + y + ", x = 0, curr = " + curr);
            answer = Math.max(answer, curr);
            // Now, we're going to perform a sliding window to efficiently get the good columns
            // along the range, with our window being size K!
            // We are holding our y fixed for the time being,

            // Technically - all the && statements with the hasBlack are unnecessary - if the
            // range is a valid range [l, r], meaning l <= r, it had to have a black!!
            for (int x = 1; x <= n - k; x++) {
                curr += diffs[x];
                if (columnHasBlack[x - 1] && ly[x - 1] <= y && y <= ry[x - 1]) {
                    curr--;
                }
                if (columnHasBlack[x + k - 1] && ly[x + k - 1] <= y && y <= ry[x + k - 1]) {
                    curr++;
                }
                System.out.println("y = " + y + ", x = " + x + ", curr = " + curr);
                answer = Math.max(answer, curr);
            }
        }
        System.out.println(answer);
    }

    static void display(int[][] arr) {
        for (int r = 1; r < arr.length; r++) {
            System.out.println(Arrays.toString(arr[r]));
        }
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