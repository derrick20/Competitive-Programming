/*
ID: d3rrickl
LANG: JAVA
PROG: subset
 */
// USACO Training
// wait that was so fucking easy why couldn't i do it like a year ago
import java.io.*;
import java.util.*;

public class subset {

    public static void main(String[] args) throws Exception {
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new FileReader("subset.in")); //new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new FileWriter("subset.out"));
        N = Integer.parseInt(br.readLine());
        if (N*(N+1)/2 % 2 != 0) { // the sum (N*(N+1)/2) is odd, it's impossible
            out.println(0);
            out.close();
        }
        else {
            memo = new long[N + 1][N * (N + 1) / 4 + 1];
            // (Double-counted, divide by 2)
            //out.println(solveTopDown(1, 0) / 2); // since, for each solution, there is the remaining part.
            memo2 = new long[N * (N + 1) / 4 + 1];
            out.println(solveBottomUp());
            out.close();
        }
    }
    static long[][] memo;
    // we don't need an array of the numbers, since we're given it's the consecutive ints from 1 to  N
    static int N;
    static long[] memo2;
    /*
    Bottom-Up DP (memoizing)
    This idea works by starting from some index, seeing how many ways we can construct it.
    Then, add the number here and update future buckets since they can be created based on the number of ways to
    reach us, then adding this extra step
    newbucket[amt + increment] += oldbucket[amt]
     */
    public static long solveBottomUp() {
        // myfailedattempt
//        memo[1][1] = 1; // Base case. For index 1, what sums can we make? (only the sum of 1)
//        for (int i = 2; i <= N; i++) {
//            int sum = 1;
//            // While it is possible to reach this sum given previous computation, perform a step to later levels
//            while (memo[i][sum] != 0) {
//                memo[i]
//                sum++;
//            }
//        }
        memo2[0] = 1;
        int s = (N*(N+1))/4;
        // Woah this is so genius.
        // Basically, we can realize we only need the number of ways, so it's not necessary? why? to
        // memoize the index too. So smaller state space, but what we do, is we back into the precomputed
        // information, seeing if going step away from it with the current increment allows us to reach a position.
        // Example. Start from the max sum, let i = 2. Once we go down to be 2 above the last sum reached, then we know
        // # ways to reach this state = # ways to reach state-2. We continue this cascade of info down
        // However, we can't go the other way, from the bottom up, because that implies we could use the same 2 to jump
        // up the ladder more than once. THIS IS SOOOOO CLEVER
        for (int i = 1; i <= N; i++) {
            for (int j = s; j >= i; j--) {
                memo2[j] += memo2[j-i];
            }
        }
//        for (int i = 0; i < memo2.length; i++)
//            System.out.println(i + " " + memo2[i]);
        return memo2[s] / 2;
    }

    /*
    Top-Down DP, using memoization
    Base case, when we've traversed the whole array, or we've reached the sum, break
    Either use the number at the current index, or move on.
    Importantly, if we've reached the same index, same sum, we can use the number of ways to do the rest
    from prior exploration, saving time.
    Return the number of ways to reach the half sum from an index and current sum
     */
    public static long solveTopDown(int i, int sum) {
        if (sum == N*(N+1)/4) {
            return 1; // we are done, just stopping is the one way to  do it
        }
        if (i > N) { // We are out of bounds (1 <= i <= N)
            return 0;
        }
        // Now, we actually try to solve it (non base-case time)
        if (memo[i][sum] != 0) {
            return memo[i][sum];
        }
        else {
            long ct = solveTopDown(i+1, sum);
            if (sum + i <= N*(N+1)/4)
                ct += solveTopDown(i+1, sum + i);
            return memo[i][sum] = ct;
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