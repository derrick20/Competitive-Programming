/*
 * @author derrick20
 * Holyy cow that was so hard. I ended up only getting 9 cases,
 * test 5 still is wrong. 19 should be 18 on one test. Basically, a lot of things
 * I was doing wrong before: this is a clear BFS problem. We have a bunch of
 * end states, and we want to reach them from any of the first N nodes. We can more
 * simply flip the problem, since it doesn't matter which of the end states it is. Essentially
 * our problem asks what is the shortest distance from any of the N bessie pies to ANY
 * end state of 0 pie. That is equivalent to starting from any source (a 0 pie) and BFS'ing,
 * and we want the shortest distance from any of those sources.
 * The tricky part was designing the structure of my arrays and my objects.
 */
import java.io.*;
import java.util.*;

public class PieForPieBFS {
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
//        setupIO();
        setupIO("piepie");

        N = sc.nextInt();
        D = sc.nextInt();

        // We sort them by the opposite value, because we want to know,
        // for some pie value say Bessie gave, what Elsie pie could she have received?
//        TreeSet<Pie> bPies = new TreeSet<Pie>((Pie o1, Pie o2) -> o1.eValue - (o2.eValue));
//        TreeSet<Pie> ePies = new TreeSet<Pie>((Pie o1, Pie o2) -> o1.bValue - (o2.bValue));
        pies = new Integer[2 * N];
        bVal = new int[2 * N]; // The rating of Bessie for each pie
        eVal = new int[2 * N];

        int[] distance = new int[2 * N];
        boolean[] visited = new boolean[2 * N];
        ArrayDeque<Pair> pq = new ArrayDeque<>();
        for (int i = 0; i < 2 * N; i++) {
            int b = sc.nextInt();
            int e = sc.nextInt();
            bVal[i] = b;
            eVal[i] = e;
            pies[i] = i;
            distance[i] = oo;
            if (i < N) {
                if (e == 0) { // Elsie can receive this pie from Bessie (i < N) and be happy
                    pq.add(new Pair(1, i));
                    visited[i] = true;
                    distance[i] = 1;
                }
            }
            else {
                if (b == 0) {
                    pq.add(new Pair(1, i));
                    visited[i] = true;
                    distance[i] = 1;
                }
            }
        }
        Arrays.sort(pies, 0, N, (Integer id1, Integer id2) -> eVal[id1] - eVal[id2]);
        Arrays.sort(pies, N, 2 * N, (Integer id1, Integer id2) -> bVal[id1] - bVal[id2]);
//        for (int i = N; i < 2 * N; i++) {
//            System.out.println(bVal[pies[i]]);
//        }

        // From ANY of the multiple sources
        // which have a 0. This is screaming multi-source
        // BFS, so let's do it. Our dfs would fail since we
        // might have many ways to reach something!

        while (!pq.isEmpty()) {
            Pair top = pq.poll();
            int dist = top.dist;
            int id = top.id;
//            System.out.println(bVal[id] + " " + eVal[id]);

            if (id < N) {
                // This represents a pie that was received by Elsie. So, we go back
                // to the owner, Bessie, by searching for pies that were D less than us
                // that Elsie would have given Bessie to cause her to give Elsie this
                int bpie = id;
                // Once we've removed a bunch, we can't keep looping back to them

                    // We have a range of B Values that would've caused Bessie to give that (X, 0) pie to Elsie
                    // Given this knowledge, we need to look for an Elsie pie that would've triggered Bessie to
                    // give Elsie such a pie
                int pos = binarySearch(bVal[bpie] - D, 1, N, 2 * N - 1);

                while (pos < 2 * N && bVal[pies[pos]] <= bVal[bpie]) {
                    int nextPie = pies[pos++];
                    if (!visited[nextPie]) {
                        visited[nextPie] = true;
                        distance[nextPie] = dist + 1;
                        pq.add(new Pair(distance[nextPie], nextPie));
                    }
                }
            }
            else {
                int epie = id;
                // Once we've removed a bunch, we can't keep looping back to them
                int pos = binarySearch(eVal[epie] - D, 0, 0, N - 1);
                while (pos < N && eVal[pies[pos]] <= eVal[epie]) {
                    int nextPie = pies[pos++];
                    if (!visited[nextPie]) {
                        visited[nextPie] = true;
                        distance[nextPie] = dist + 1;
                        pq.add(new Pair(distance[nextPie], nextPie));
                    }
                }
            }
        }
        for (int i = 0; i < N; i++) {
            out.println(distance[i] == oo ? -1 : distance[i]);
        }
        out.close();
    }

    static int N, D;
    static int[] bVal, eVal;
    static Integer[] pies;
    // Technically, bVisited and bMemo actually store, for a pie from ELSIE, whether visited and what best path
    // length is. But as long as consistent, it's fine
    static int oo = (int) 2e9;

    // Find the earliest pie that has >= value of val:
    // 0001111 Find the earliest 1, essentially.
    static int binarySearch(int val, int type, int lo, int hi) {
        while (lo < hi) {
            int mid = (hi - lo) / 2 + lo;
            // 0 represents Bessie search. 1 represents Elsie search
            int test = type == 0 ? eVal[pies[mid]] : bVal[pies[mid]];
            // Once we hit the 01 case, we want our mid to go TOO LOW,
            // so that once we've latched onto a proper hi, (the last one possible)
            // the lo will keep stepping upward.
            // If it were 111000, then we would want our upper bound to be creeping down
            // and our lo to latch on (no +1). In that case, our mid would have to have a +1 to
            // ensure
            if (test >= val) {
                hi = mid;
                // It is not find to throw mid inclusive, so no -1.
            } else {
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

    static class Pair implements Comparable<Pair> {
        int dist;
        int id;

        public Pair(int d, int i) {
            dist = d;
            id = i;
        }

        public int compareTo(Pair p2) {
            return dist - p2.dist;
        }
    }

    static class Pie {
        int bValue, eValue;
        int idx;

        public Pie(int i, int b, int e) {
            idx = i;
            bValue = b;
            eValue = e;
        }

        public String toString() {
            return "(" + idx + ": " + bValue + ", " + eValue + ")";
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
