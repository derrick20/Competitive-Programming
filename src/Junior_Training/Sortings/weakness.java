import java.io.*;
import java.util.*;

public class weakness {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }
        crossed = new int[N];
        crosser = new int[N];
        coordinateCompress(arr);
        int[] buffer = new int[arr.length];

        countInversions(arr, buffer, 0, arr.length - 1);
        long doubleInversions = 0;
        for (int i = 0; i < N; i++) {
            // System.out.println(crossed[i] + " " + crosser[i]);
            doubleInversions += (long) crossed[i] * crosser[i];
        }
        System.out.println(doubleInversions);
    }

    static void coordinateCompress(int[] arr) {
        TreeSet<Integer> set = new TreeSet<>();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]);
        }
        // Clever trick to automatically use the sorted values (treeset), then give them
        // a proper compressed value
        for (int x : set) { // Reverse the mapping, taking value -> index. Compress them
            map.put(x, map.size());
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = map.get(arr[i]);
        }
    }

    // hi is inclusive
    static void countInversions(int[] arr, int[] buffer, int lo, int hi) {
        if (hi - lo <= 0) {
            return;
        }
        int mid = (lo + hi) >> 1;
        // Three types of inversions. Left, right, and splitInversions.
        countInversions(arr, buffer, lo, mid);
        countInversions(arr, buffer, mid +1, hi);
        mergeAndCount(arr, buffer, lo, mid, hi);
    }

    static void fill(int[] B, int n, int[] A, int base)
    {
        for(int i = 0; i < n; ++i)
            B[i] = A[i + base];
        B[n] = (int) 1e9 + 7;
    }

    static int[] crossed;
    static int[] crosser;

    // The arr will continually get sorted, just in separate parts of it
    // Therefore, we just need to keep pointers to different sections of the array,
    // and then combine this information back into the original array
    static void mergeAndCount(int[] arr, int[] buffer, int lo, int mid, int hi) {
        // TODO TODO CRUCIAL ERROR WAS TO COPY ONLY THE SPECIFIC PART OF THE ARRAY NEEDED
        for (int i = lo; i <= hi; i++) {
            buffer[i] = arr[i]; // Keep a quick copy, so that we can modify arr and give it the correct ordering
        }
        /*int n1 = mid - lo + 1, n2 = hi - mid;
        int[] L = new int[n1 + 1], R = new int[n2 + 1];
        fill(L, n1, arr, lo);
        fill(R, n2, arr, mid + 1);
        int t = 0;
        for (int w = lo, i = 0, j = 0; w <= hi; w++) {
            if(L[i] < R[j])
            {
                crossed[L[i]] += t;
                arr[w] = L[i++];
            }
            else
            {
                crosser[R[j]] += n1 - i;
                arr[w] = R[j++];
                ++t;
            }
        } // */

        // It is given that arr[lo:mid] is sorted, and arr[mid+1:hi] is too, and we need to merge.
        // Each time we put down something from the right array, this means that all of the items
        // remaining in the left array are inverted, so increment by that number of remaining items. (mid-lo+1) - (i)
        int i = lo;
        int j = mid + 1;
        int curr = lo;
        // We essentially traverse through all of both the left and right array, and fix those spots within
        // the sorted array
        int numCrossers = 0;
        while (curr <= hi) {
            if (i <= mid && j <= hi && buffer[i] <= buffer[j]) {
                arr[curr] = buffer[i++]; // grab the value from the left part, since it is smaller
                crossed[arr[curr]] += numCrossers; // The line moving to the right will intersect with however
                // many lines have already crossed over to the left
            }
            else if (i <= mid && j <= hi && buffer[i] > buffer[j]){
                arr[curr] = buffer[j++]; // grab from right side, since it is smaller
                // is the number of values that we have crossed over
                // The is the only time where split inversions occur (use the visual of crossing lines)
                crosser[arr[curr]] += mid - i + 1;; // This person crossed all of the people within the left array
                numCrossers++; // The number of lines moving to the left has increased.
            }
            else if (i > mid) {
                arr[curr] = buffer[j]; // grab from right side, since it is smaller
                numCrossers++; // This increases, technically, but it doesn't affect anything. Note that
                // The number of crossovers here won't matter, since no one will ever cross us later. /\ vs \/
                // On the other hand, above, all of the lines coming from the left must hit a line coming from the right \/
                j++;
            }
            else if (j > hi) {
                arr[curr] = buffer[i]; // grab the value from the left part, since it is smaller
                crossed[arr[curr]] += numCrossers;
                i++;
            }
            curr++;
        }
        // clean up whichever side is still leftover, all of it will just enter
        /*while (i <= mid) {
            arr[curr] = buffer[i]; // grab the value from the left part, since it is smaller
            crossed[arr[curr]] += numCrossers;
            i++;
            curr++;
        }
        while (j <= hi) {
            arr[curr] = buffer[j]; // grab from right side, since it is smaller
            numCrossers++; // This increases, technically, but it doesn't affect anything. Note that
            // The number of crossovers here won't matter, since no one will ever cross us later. /\ vs \/
            // On the other hand, above, all of the lines coming from the left must hit a line coming from the right \/
            j++;
            curr++;
        } // */
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
