/*
 * @author derrick20
 * Wow i did brute force during contest, but later we learn that
 * you can apply tons of interesting concepts: binary search,
 * transforming the coordinate space, and 2D prefix sum queries
 */
/*

 */

import java.io.*;
import java.util.*;

public class groups {
    static int maxSize = 3001;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int c = 0; c < C; c++) {
            int N = sc.nextInt();
            X = new int[N];
            Y = new int[N];
            int[][] grid = new int[maxSize][maxSize];
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                // This is a combination of rotating clockwise 45 degrees
                // then scaling up by sqrt(2)!!
                int transformedX = x - y + 1000;
                int transformedY = x + y + 1000;
                X[i] = transformedX;
                Y[i] = transformedY;
                grid[transformedX][transformedY] += 1; // Now there is a node there
            }
            prefix = new int[maxSize][maxSize];
            for (int i = 0; i < prefix.length; i++) {
                for (int j = 0; j < prefix.length; j++) {
                    if (i == 0 & j == 0) {
                        prefix[i][j] = grid[i][j];
                    }
                    else if (i == 0) {
                        prefix[i][j] = prefix[i][j - 1] + grid[i][j];
                    }
                    else if (j == 0) {
                        prefix[i][j] = prefix[i - 1][j] + grid[i][j];
                    }
                    else {
                        prefix[i][j] = prefix[i - 1][j] + prefix[i][j - 1] - prefix[i - 1][j - 1] + grid[i][j];
                    }
                }
            }
            for (int node = 0; node < N; node++) {
                // We don't need to worry, the bounds for x and y are <= 2000
                // so the radius is always <= 3000 (more like 1500, but i'll be safe)
                // Binary search down until we reach it
                int minDist = binarySearch(0, maxSize, X[node], Y[node]);
                out.println(minDist);
            }
        }
        out.close();
    }
    static int[][] prefix;
    static int[] X, Y;

    static int countNodes(int cX, int cY, int d) {
        int ans = 0;
        // the square around, using PIE
        if (cX + d < maxSize && cX - d - 1 >= 0 && cY + d < maxSize && cY - d - 1 >= 0) {
            ans += prefix[cX + d][cY + d];
            ans -= prefix[cX - d - 1][cY + d];
            ans -= prefix[cX + d][cY - d - 1];
            ans += prefix[cX - d - 1][cY - d - 1];
        }
        else {
            ans = 1000;
        }
        return ans;
    }

    // We binary search from the maximum distance out from the center
    // point from which we are hoping to find a neighbor to pair us off with
    // So, N log N solution! (for each node, do log N using the prefix
    // sum table
    // Better binarySearch technique. The hi is exclusive, the
    // lo is inclusive (so use 0, N)
    static int binarySearch(int lo, int hi, int cX, int cY) {
        int ans = 0;
        while (lo <= hi) {
            // While the bounds haven't passed each other, keep going
            int mid = (hi - lo) / 2 + lo;
            int count = countNodes(cX, cY, mid);
            if (count == 1) {
                // Only contains the current node, so go bigger
                lo = mid + 1;
            }
            else {
                hi = mid - 1;
                // We know for CERTAIN, that this answer works
                // However, we want to try lowering more
                ans = mid;
            }
        }
        return ans;
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
