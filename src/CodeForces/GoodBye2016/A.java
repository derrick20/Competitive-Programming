import java.io.*;
import java.util.*;
/*
 * CodeForces New Year and Hurry
 * @derrick20
 */

public class A {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        System.out.println(binarySearch(1, N));
    }
    static int N, K;

    static int binarySearch(int lo, int hi) {
        while (lo <= hi) {
            int pivot = (hi - lo) / 2 + lo;
            if (score(pivot) < 0) {
                lo = pivot + 1;
            }
            else if (score(pivot) > 0) {
                hi = pivot - 1;
            }
            else {
                return pivot;
            }
        }
        int pivot = (hi - lo) / 2 + lo;
        // We should keep going up 0-1 indices to reach the highest
        // However, we can't go over, so go to the last one that is fitting, but can't go past N
        while (score(pivot+1) < 0 && pivot < N) {
            pivot++;
        }
        // We never want to overestimate, so keep decreasing until under, but also we must not have gone past the
        while (score(pivot) > 0 || pivot > N) {
            pivot--;
        }
        return pivot;
    }

    // If positive, too high, if negative, too low
    static int score(int pivot) {
        return 5*pivot*(pivot+1)/2 - (4*60 - K);
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
