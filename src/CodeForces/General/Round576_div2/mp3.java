/*
 * @author derrick20
 * Key obvious errors slowing me down:
 * overflow of INT during the exponentiatioN!!!! A better thing would
 * be to note that the bounds can go huge. Use a max to watch out for it
 * (Math.pow happens to be safe >:( )
 * Watch out for the end point of for loops: the -1 stuff.
 * Only resort to DP if you know the greedy fails. In this problem,
 * the constraints made it so there was a fixed number of elements to remove
 * Therefore, you would just try each one and find the optimal one.
 * A suffix array is pointless. A prefix array achieves the same thing, just
 * use complementary counting to get suffix.
 * Do an ENTIRE test case thoroughly and systematically, as if you were the computer,
 * and try to notice a pattern.
 * The math of the ceiling was annoying, do it very rigorously to convince yourself before
 * coding.
 */
import java.io.*;
import java.util.*;

public class mp3 {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int I = sc.nextInt(); // BYTES
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            arr.add(sc.nextInt());
        }
//        coordinateCompress(arr);
        Collections.sort(arr);
        TreeSet<Integer> set = new TreeSet<>(arr);
        int K = set.size();
        int[] freqMap = new int[K];
        for (int i = 0, id = 0; i < arr.size(); i++) {
            if (i > 0 && !arr.get(i).equals(arr.get(i - 1))) {
                id++; // this represents a distinct value, so we must go up
            }
            freqMap[id]++;
        }
        int[] prefix = new int[K + 1];
        for (int i = 1; i <= K; i++) {
            prefix[i] = prefix[i - 1] + freqMap[i - 1];
        }
//        System.out.println(Arrays.toString(freqMap));
//        System.out.println(Arrays.toString(prefix));
        int rangeSize = 1 << Math.min(20, (8 * I / N));
        // Importantly, this floors it, which ensure the ceiling doesn't
        // take us above the file memory limnit
        int toRemove = K - rangeSize;
        if (toRemove > 0) {
            int changed = (int) 1e9; // the max possible just for starters
            for (int i = 0; i <= toRemove; i++) {
                // Hard to think about, but visualize the prefix sum of the first "rangeSize" # of items
                // Then, there are still "toRemove" # of items left in the array that aren't added in
                // If we shift over, then we take in rangeSize + i items, so we need to subtract out i items..
                // We should do this until we go to ALL K items, then subtract the front bit.
                int preserved = prefix[rangeSize + i] - prefix[i];
                int tempChange = prefix[K] - preserved;
                if (tempChange < changed) {
                    changed = tempChange; // how much did we have to change
                }
            }
            out.println(changed);
        }
        else {
            out.println(0);
        }
        out.close();
    }

    static void coordinateCompress(ArrayList<Integer> arr) {
        TreeSet<Integer> set = new TreeSet<>(arr);
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int v : set) {
            map.put(v, map.size());
        }
        for (int i = 0; i < arr.size(); i++) {
            arr.set(i, map.get(arr.get(i)));
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
