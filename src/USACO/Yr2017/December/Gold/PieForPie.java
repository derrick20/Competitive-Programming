/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class PieForPie {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name) throws Exception {
        sc = new FastScanner(new FileReader(problem_name + ".in"));
        out = new PrintWriter(new FileWriter(problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner(System.in);
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
        setupIO();
//        setupIO("piepie");

        N = sc.nextInt();
        D = sc.nextInt();
        bPies = new Pie[N];
        ePies = new Pie[N];
        HashMap<String, Integer> bPieIndex = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int b = sc.nextInt();
            int e = sc.nextInt();
            bPies[i] = new Pie(b, e);
            bPieIndex.put(bPies[i].toString(), i);
        }
        for (int i = 0; i < N; i++) {
            int b = sc.nextInt();
            int e = sc.nextInt();
            ePies[i] = new Pie(b, e);
        }
        Arrays.sort(bPies, Comparator.comparing(Pie::getbValue));
        Arrays.sort(ePies, Comparator.comparing(Pie::geteValue));
        bMemo = new HashMap<>();
        eMemo = new HashMap<>();

        int[] ans = new int[N];
        for (Pie bpie : bPies) {
            // Start with all pies fresh and free!
            bVisited = new HashMap<>();
            eVisited = new HashMap<>();
            int test = eSolve(bpie);
            // If it was infinity, we know it was impossible
            ans[bPieIndex.get(bpie.toString())] = test >= oo ? -1 : test + 1;
        }
        for (int i = 0; i < N; i++) {
            out.println(ans[i]);
        }
        out.close();
    }

    static int N, D;
    static Pie[] bPies, ePies;
    static HashMap<String, Boolean> bVisited, eVisited;
    static HashMap<String, Integer> bMemo, eMemo;
    // Technically, bVisited and bMemo actually store, for a pie from ELSIE, whether visited and what best path
    // length is. But as long as consistent, it's fine
    static int oo = (int) 2e9;

    // Find the earliest pie that has >= value of val:
    // 0001111 Find the earliest 1, essentially.
    static int binarySearch(int val, int type, Pie[] arr) {
        int lo = 0;
        int hi = N - 1;
        while (lo < hi) {
            int mid = (hi - lo) / 2 + lo;
            // 0 represents Bessie search. 1 represents Elsie search
            int test = type == 0 ? arr[mid].bValue : arr[mid].eValue;
            // Once we hit the 01 case, we want our mid to go TOO LOW,
            // so that once we've latched onto a proper hi, (the last one possible)
            // the lo will keep stepping upward.
            // If it were 111000, then we would want our upper bound to be creeping down
            // and our lo to latch on (no +1). In that case, our mid would have to have a +1 to
            // ensure
            if (test >= val) {
                hi = mid;
                // It is not find to throw mid inclusive, so no -1.
            }
            else {
                lo = mid + 1;
                // More logic here: we have a +1 on the lo term
                // because it is valid to throw away everything below mid, inclusive
                // Because of this idea of throwing stuff away, we want it so that
                // the lower bound will be tossing the final 0 out in the 01 case.
                // So, we want mid to favor the lo case of detecting 0's and tossing them out.
            }
        }
        return lo = hi; // They are equal now!
    }

    static int bSolve(Pie epie) {
        String id = epie.toString();
        if (bVisited.containsKey(id)) {
            // Bad state, we can't use old pies!
            return oo;
        }
        if (bMemo.containsKey(id)) {
            // Ah, we've solved this before! Use our old work!
            return bMemo.get(id);
        }
        if (epie.bValue == 0) {
            // We finished!
            return 0;
        }
        bVisited.put(id, true);
        int baseValue = epie.bValue;
        // Search for Bessie's pie that has BVALUE at least as high as the one we JUST RECEIVED
        int index = binarySearch(baseValue, 0, bPies);
        int minSteps = oo;
        // Try all pies that are WITHIN D of our current evaluation, and also avoid out of array bounds!
        while (index < N && bPies[index].bValue <= D + baseValue) {
            String nextPie = bPies[index].toString();
            // Note that this new pie may have been used before! (circular looping)
            if (!bVisited.containsKey(nextPie)) {
                // Now give this pie to Elsie, and e-solve!
                minSteps = Math.min(minSteps, 1 + eSolve(bPies[index]));
            }
            index++;
        }
        bMemo.put(id, minSteps);
        return minSteps;
    }

    // Bessie just gave Elsie a pie, let's see what we can do with it!
    static int eSolve(Pie bpie) {
        String id = bpie.toString();
        if (eVisited.containsKey(id)) {
            // Bad state, we can't use old pies!
            return oo;
        }
        if (eMemo.containsKey(id)) {
            // Ah, we've solved this before! Use our old work!
            return eMemo.get(id);
        }
        if (bpie.eValue == 0) {
            // We finished!
            return 0;
        }
        eVisited.put(id, true);
        int baseValue = bpie.eValue;
        // Search for Bessie's pie that has BVALUE at least as high as the one we JUST RECEIVED
        int index = binarySearch(baseValue, 1, ePies);
        int minSteps = oo;
        // Try all pies that are WITHIN D of our current evaluation, and also avoid out of array bounds!
        while (index < N && ePies[index].eValue <= D + baseValue) {
            String nextPie = ePies[index].toString();
            // Note that this new pie may have been used before! (circular looping)
            if (!eVisited.containsKey(nextPie)) {
                // Now give this pie to Bessie, and b-solve!
                minSteps = Math.min(minSteps, 1 + bSolve(ePies[index]));
            }
            index++;
        }
        eMemo.put(id, minSteps);
        return minSteps;
    }

    static class Pair implements Comparable<Pair> {
        int dist;
        int type; // 0 for bpie, 1 for epie
        Pie pie;

        public Pair(int d, int t, Pie p) {
            dist = d;
            type = t;
            pie = p;
        }

        public int compareTo(Pair p2) {
            return dist - p2.dist;
        }
    }

    static class Pie {
        int bValue, eValue;

        public Pie(int b, int e) {
            bValue = b;
            eValue = e;
        }

        public int getbValue() {
            return bValue;
        }

        public int geteValue() {
            return eValue;
        }

        public String toString() {
            return "(" + bValue + ", " + eValue + ")";
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
