/*
 * @author derrick20
 * @problem UVa 10036 Divisibility
 * Pretty easy, just had runtime errors because of package import
 */
import java.io.*;
import java.util.*;

public class divSeq {
    static int N, K;
    static int[] arr;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        while (M-->0) {
            N = sc.nextInt();
            K = sc.nextInt();
            arr = new int[N];
            memo = new int[N][K];
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                arr[i] = (x % K + K) % K; // It's guaranteed to be from -K+1... K-1, so if negative it'll fix it
                for (int j = 0; j < K; j++) {
                    memo[i][j] = -1;
                }
            }
            System.out.println(solve(1, arr[0]) == 1 ? "Divisible" : "Not divisible");
//            for (int i = 0; i < N; i++) {
//                for (int j = 0; j < K; j++) {
//                    System.out.println("index: " + i + " rem: " + j + " possible? " + memo[i][j]);
//                }
//            }
        }
    }
    static int[][] memo;
    public static int solve(int index, int rem) {
        //System.out.println("Index: " + index + " Rem: " + rem);
        if (index == N) { // we just finished the N-1th (last thing, and we've reached the end of the array
            if (rem == 0) return 1;
            else return 0;
        }
        if (memo[index][rem] != -1) {
            return memo[index][rem];
        }
        // Nice trick to use the fact that 0's and 1's can be OR'd, which fits what is stored: whether it can be divisible or not
        return memo[index][rem] = solve(index + 1, (arr[index] + rem) % K) | solve(index + 1, ((-arr[index] + rem) % K + K) % K);
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
