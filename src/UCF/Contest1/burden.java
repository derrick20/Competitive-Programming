/*
 * @author derrick20
 * SUBSET SUM DP - wow I didn't realize that but made it myself
 * Based on the solution, we technically did not need to binary search
 * for the minimum weight using the map.floorKey method, but basically,
 * we could've started from that highest weight and tried going down one-by-one
 * until we found that halfway sum point. Since the bound sizes aren't that
 * big, we can do this fine as well.
 *
 * AHH Issue was LONGGGGS
 * AHHH DIDN"T REMOVE EXTRANEOUS SOLUTIONS!!!
 */

import java.io.*;
import java.util.*;

public class burden {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        while (C--> 0) {
            N = sc.nextInt();
            arr = new int[N];
            total = 0;
            for (int j = 0; j < N; j++) {
                arr[j] = sc.nextInt();
                total += arr[j];
            }
            memo = new TreeMap<>();
            // need to fill it up since non-primitive!!
            for (int j = 0; j < total + 1; j++) {
                memo.put(j, 0L);
            }
            Arrays.sort(arr);
//            int half = 0;
//            int p = 0;
//            while (half + arr[p] <= total / 2) {
//                half += arr[p];
//                p++;
//            }
            memo.put(0, 1L);
            solve();
            // TODO GAHHGAHHAH FInally fixed this. Basically we had a bunch of zeroes there,
            // TODO so the edge case was that there could be no way to get many sums, but I wasn't accounting for that
            for (int j = 0; j < total + 1; j++) {
                if (memo.get(j) == 0) {
                    memo.remove(j);
                }
            }
            int half = memo.floorKey(total / 2);
            long ways = memo.get(half);
            if (half * 2 == total) {
                ways /= 2;
                // This is because we can get exactly the amount to reach half
                // thus, they are indistinguishable, so overcounted
            }
            out.println((total - 2 * half) + " " + ways);
        }
        out.close();
    }
    static int N, total;
    static int[] arr;
    static TreeMap<Integer, Long> memo;
    // Crucial change - treemaps have built in binary search, so we can find
    // the best point. Map of weigh to the number of ways. Binary search so
    // we reach the least weight that exceeds half of total

    // Stores the ways to reach sum of a certain value
    static void solve() {
        // then this is a way to do it
        // Knapsack along the way
        // Bottom-up: the state is the current index and running sum
        // we transition by adding the number of ways to reach the current sum
        // and add that to the number of ways to reach a sum + our value,
        // since it's essentially a step in that direction
        for (int i = 0; i < N; i++) {
            for (int s = total; s >= 0; s--) {
                int val = arr[i];
                int currSum = s; //memo.get(s);
                // If we can reach curr, then using the value at the current
                // index within the array, we can jump to the above, lending
                // them all of the ways we were able to reach our position
                if (val + currSum <= total) {
                    int above = currSum + val;
                    memo.put(above, memo.get(above) + memo.get(currSum));
                }
            }
        }

        /*
        if (sum == 0) {
            return 1;
        }
        // ran out of options, so give up
        if (i >= N) {
            return 0;
        }
        if (memo[sum]!= 0) {
            return memo[sum];
        }
        // either use this value to subtract from sum or don't, add these ways
        int ans = solve(i + 1, sum + arr[i]) + solve(i + 1, sum);
        return memo[sum] = ans;*/
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
