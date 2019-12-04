import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

// Weird, somehow it doesn't work for SPOJ and HackerRank, something weird with inputs. See inv.in?
public class inversionCount {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);//new FileReader(new File("inv.in"))); //System.in);

        int T = sc.nextInt(); // Number of test cases

        for (int t = 0; t < T; t++) {
            int N = sc.nextInt();
            long[] arr = new long[N];
            for (int i = 0; i < N; i++) {
                arr[i] = sc.nextLong();
            }
            long[] buffer = new long[arr.length];
            long inversions = countInversions(arr, buffer, 0, arr.length - 1);
            System.out.println(inversions);
        }
    }
    // hi is inclusive
    public static long countInversions(long[] arr, long[] buffer, int lo, int hi) {
        if (hi - lo <= 0) {
            return 0;
        }
        else {
            int mid = (lo + hi) / 2;
            // Three types of inversions. Left, right, and splitInversions.
            long ct = 0;
            ct += countInversions(arr, buffer, lo, mid);
            ct += countInversions(arr, buffer, mid +1, hi);
            ct += mergeAndCount(arr, buffer, lo, mid, hi);
            return ct;
        }
    }

    // The arr will continually get sorted, just in separate parts of it
    // Therefore, we just need to keep pointers to different sections of the array,
    // and then combine this information back into the original array
    public static long mergeAndCount(long[] arr, long[] buffer, int lo, int mid, int hi) {
        for (int i = 0; i < arr.length; i++) {
            buffer[i] = arr[i]; // Keep a quick copy, so that we can modify arr and give it the correct ordering
        }
        // It is given that arr[lo:mid] is sorted, and arr[mid+1:hi] is too, and we need to merge.
        // Each time we put down something from the right array, this means that all of the items
        // remaining in the left array are inverted, so increment by that number of remaining items. (mid-lo+1) - (i)
        long splitInv = 0;
        int i = lo;
        int j = mid + 1;
        int curr = lo;
        // We essentially traverse through all of both the left and right array, and fix those spots within
        // the sorted array
        while (i <= mid && j <= hi) {
            if (buffer[i] <= buffer[j]) {
                arr[curr] = buffer[i]; // grab the value from the left part, since it is smaller
                i++;
            }
            else {
                arr[curr] = buffer[j]; // grab from right side, since it is smaller
                splitInv += mid - i + 1; // The entire length of the left array, minus the amount used so far
                // is the number of values that we have crossed over
                // The is the only time where split inversions occur (use the visual of crossing lines)

                j++;
            }
            curr++;
        }
        // clean up whichever side is still leftover, all of it will just enter
        while (i <= mid) {
            arr[curr] = buffer[i]; // grab the value from the left part, since it is smaller
            i++;
            curr++;
        }
        while (j <= hi) {
            arr[curr] = buffer[j]; // grab from right side, since it is smaller
            j++;
            curr++;
        }
        return splitInv;
    }
}