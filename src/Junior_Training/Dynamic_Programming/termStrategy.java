/*
 * @author derrick20
 * @problem UVa 11341 Term Strategy
 * Has a weird wrong answer error, but I learned the key ideas for DP base cases was the takeaway
 * It should work for all the test cases, I tested with the debugger, so idk. Learned about rounding too!
 */
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class termStrategy {
    static DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int t = 0; t < T; t++) {
            int N = sc.nextInt(), M = sc.nextInt();
            arr = new int[N + 1][M + 1];
            memo = new int[N + 1][M + 1];
            int minHours = 0; // how many we need as a minimum
            boolean impossible = false;
            int i = 1;
            while (i <= N) {
                boolean fiveFound = false;
                for (int j = 1; j <= M; j++) {
                    arr[i][j] = sc.nextInt();
                    if (!fiveFound && arr[i][j] >= 5) {
                        fiveFound = true;
                        arr[i][0] = j; // The index at which something greater than a 5 is first seen
                        minHours += j;
                    }
                    memo[i][j] = -1;
                }
                if (!fiveFound || minHours > M) {
                    impossible = true;
                }
                i++;
            }
//            for (int x = 1; x <= N; x++) {
//                for (int j = 1; j <= M; j++) {
//                    System.out.print(arr[x][j]);
//                }
//                System.out.println();
//            }
            if (impossible) {
                System.out.println("Peter, you shouldn't have played billiard that much.");
                continue;
            }
            int ret = solve(1, M);
            if (ret < 0) {
                System.out.println("Peter, you shouldn’t have played billiard that much.");
            }
            else {
                double value = Math.round(100.0*ret/N) / 100.0;
                System.out.println("Maximal possible average mark - " + df.format(value) + ".");
            }
            /*for (int i = 1; i <= N; i++) {
                System.out.println(arr[i][0]);
                for (int j = 1; j <= M; j++) {
                    System.out.println(" index: " + i + ", hours: " + j + ", Value: " + memo[i][j]);
                }
            }//*/
        }
    }

    /*
     * Like knapsack, the key idea is to find a way to recursively write the problem. The two parameters,
     * M (hours) and N (courses) leads to two possibilities. Solving with one fewer hour, or one fewer course
     * However, since one course doesn't influence another, it makes more sense to progress through by course.
     * To split a sub problem, we maximize determine all possible hours used, then add this utility to what
     * the solution of maximizing the sub problem is. This will guarantee the solution is found, assuming that the
     * sub problem is solved properly!
     */
    static int[][] arr;
    static int[][] memo;
    static int solve(int index, int hours) {
        int N = memo.length-1, M = memo[0].length-1; // # of courses and hours
        if (hours < 0 || (hours == 0 && index <= N)) { // We ran out of hours before we reached the end
            return (int) -1e9;
        }
        if (index > N) { // we've gone past the last course, so we're done and can't add anymore utility
            return 0;
        }
        if (memo[index][hours] != -1) {
            return memo[index][hours];
        }
        int max = (int) -1e9;
        int h = arr[index][0];
        while (h <= hours && h <= M) {
            max = Math.max(max, arr[index][h] + solve(index + 1, hours - h));
            h++;
        }
        /*for (int h = arr[index][0]; h <= M; h++) { // we must at least study one hour of each subject
            // We need to have a minimum score of this. If this doesn't end up working, it will end up that
            //  the memo will store a value of -1.
            /*if (hours - h < 0)
                return memo[index][hours] = (int) -1e9;; // h will only increase, so just give up, take whatever max we had
            //
            max = Math.max(max, arr[index][h] + solve(index + 1, hours - h));
            // We try all possible amounts of study time for the current course, and then combine this with the
            // optimal solution of the next state (with one fewer course)
        }*/
        if (max < 0)
            return memo[index][hours] = (int) -1e9;
        return memo[index][hours] = max;
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