import java.io.*;
import java.util.*;
/*
 * CodeForces New Year and Rating
 * @derrick20
 *  Used hint unfortunately, was trying binary search  to no avail. The idea of upper and lower
 * bound is quite similar to binary search, should've considered the math of the inequalities earlier...!
 */

public class C {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] arr = new int[N];
        int[] prefixSum = new int[N];
        int[] div = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
            div[i] = sc.nextInt();
            if (i == 0)
                prefixSum[i] = arr[i];
            else
                prefixSum[i] = arr[i] + prefixSum[i - 1];
        }
        int max = (int) 1e9;
        int upper = max;
        int lower = -1 * upper;
        if (div[0] == 1)
            lower = 1900;
        else
            upper = 1899;
        for (int i = 1; i < N; i++) {
            if (div[i] == 1) {
                // Basically, whatever the sum of everything up before now was,
                // we know that our division must be restricted to be >= 1900 still,
                // so if adding all that brings us above 1900, we must have started at LEAST
                // 1900 - SUM
                lower = Math.max(lower, 1900 - prefixSum[i - 1]);
            } else {
                // again, to get to this division, all the previous parts had to bring us
                // "down" to this level. It is guaranteed to be less than this, since this is the
                // case where we perfectly struck the ceiling of our division with the help of all the
                // prior contests
                upper = Math.min(upper, 1899 - prefixSum[i - 1]);
            }
        }
        if (upper == max) {
            System.out.println("Infinity");
        } else if (upper < lower) {
            System.out.println("Impossible");
        } else {
            System.out.println(upper + prefixSum[N-1]);
        }
    }

    /*
    int lo, hi;
    int max = (int) (100*2e5);
    int min = -1 * max;
    if (div[0] == 1) {
        hi = max;
        lo = 1900;
    }
    else {
        lo = min;
        hi = 1899;
    }
    int initial = binarySearch(lo, hi, arr, div);
    String ans;
    if (initial == max) {
        ans = "Infinity";
    }
    else if (initial == -1) {
        ans = "Impossible";
    }
    else {
        for (int i = 0; i < arr.length; i++) {
            initial += arr[i];
        }
        ans = "" + initial;
    }
    System.out.println(ans);
}

static int binarySearch(int lo, int  hi, int[] arr, int[] div) {
    int pivot = (hi - lo) / 2 + lo;
    while (!(lo > hi)) {
        pivot = (hi - lo) / 2 + lo;
        if (isValid(pivot, arr, div)) {
            // this means we can definitely keep pushing up that initial rating
            lo = pivot + 1;
        }
        else {
            // Then we need to go down
            hi = pivot - 1;
        }
    }
    // This means we could never find it
    return pivot;
}

static boolean isValid(int rating, int[] arr, int[] div) {
    for (int i = 0; i < arr.length; i++) {
        if (div[i] == 1) {
            // must be high enough before this contest
            if (rating >= 1900) {
                // Now we can increase our rating!
                rating += arr[i];
            }
            else {
                return false;
            }
        }
        else if (div[i] == 2) {
            // must be low enough before this contest
            if (rating <= 1899) {
                // Now we can increase our rating!
                rating += arr[i];
            }
            else {
                return false;
            }
        }
    }
    // If all of the conditions pass, then TRUE!
    return true;
}
*/
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
