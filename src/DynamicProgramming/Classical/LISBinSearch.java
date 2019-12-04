/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class LISBinSearch {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
//        int[] arr = new int[N];
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = sc.nextInt();
//        }
        int[] arr = {10,9,2,5,3,7,101,18};
        HashMap<Integer, Integer> pred = new HashMap<>();
        int[] LIS = new int[arr.length];
        LIS[0] = arr[0];
        int length = 1;
        for (int i = 1; i < arr.length; i++) {
            // the last element is small enough, so tack it on
            if (arr[i] > LIS[length - 1]) {
                LIS[length] = arr[i];
                pred.put(LIS[length], LIS[length - 1]);
                length++;
            }
            else {
                // low bound inclusive, high bound inclusive.
                // Try to find where the EARLIEST location where our number
                // is less than the current sequence's values. That's thte optimal
                // location to add us. This means we are improving the accessibility
                // of the sequence for future add-ons!
                int pos = binarySearch(LIS, arr[i], 0, length - 1);
                LIS[pos] = arr[i];
                if (pos > 0) {
                    pred.put(LIS[pos], LIS[pos - 1]);
                }
            }
        }

        // Ta-da!
        ArrayList<Integer> ans = new ArrayList<>();
        int curr = LIS[length - 1];
        ans.add(curr);
        while (pred.containsKey(curr)) {
            curr = pred.get(curr);
            ans.add(curr);
        }
        Collections.reverse(ans);
        out.println(ans);
        out.println(length);
        out.close();
    }

    static int binarySearch(int[] LIS, int val, int lo, int hi) {
        // The moment that there is less than 1 item in the interval
        // the only remaining possibility is lo!
        int ans = hi;
        while (lo <= hi) {
            int mid = (hi - lo) / 2 + lo;
            if (LIS[mid] < val) {
                lo = mid + 1;
            }
            else {
                // We know for sure that this point is something
                // that can be replaced in a sequence. However,
                // keep moving down to see if we can go further
                ans = mid;
                hi = mid - 1;
            }
        }
        // lo should equal hi now!
        return ans;
    }

    static void coordinateCompress(int[] arr) {
        TreeSet<Integer> values = new TreeSet<>();
        // Get rid of duplicate values
        for (int i = 0; i < arr.length; i++) {
            values.add(arr[i]);
        }
        // Map those onto their index in the sorted order
        HashMap<Integer, Integer> valToIndex = new HashMap<>();
        for (int value : values) {
            valToIndex.put(value, valToIndex.size());
        }
        // Apply that map on the original array to compress it
        for (int i = 0; i < arr.length; i++) {
            arr[i] = valToIndex.get(arr[i]);
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