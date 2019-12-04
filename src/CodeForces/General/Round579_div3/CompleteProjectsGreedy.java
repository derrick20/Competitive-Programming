/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class CompleteProjectsGreedy {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        start = sc.nextInt();
        // For the positive, just add all of them
        // If, by adding everything within our capability, we can't
        // reach a certain project, then it'd be impossible
        positive = new PriorityQueue<>(Comparator.comparing(Project::getNeed));

        // For negative, we want to subtract the LEAST ACCESSIBLE, highest NEED project (this means REVERSE ORDER)
        // We also, if possible, subtract smaller deltas
        // If it's not accessible now, it'll never be later. So,
        // it is logical to realize this and break. Otherwise, we
        // try subtract it minimally to widen our future frontier of possibilities

        // ^^ that's not quite correct. Really, what's going on is each project can be represented by the
        // minimum value to which it can send your rating (base - |delta|). That
        negative = new PriorityQueue<>((Project p1, Project p2) -> (p2.need + p2.delta) - (p1.need + p1.delta));

        for (int i = 0; i < N; i++) {
            int need = sc.nextInt();
            int delta = sc.nextInt();
            Project p = new Project(need, delta);
            if (delta >= 0) {
                positive.add(p);
            }
            else {
                negative.add(p);
            }
        }
        int minSkipped = binarySearch();
        out.println(N - minSkipped);
        Project i;
        while ((i = positive.poll())!= null) out.print(i + " ");
        out.println();
        while ((i = negative.poll())!= null) out.print(i + " ");
        out.println();
//        out.println(poss ? "YES" : "NO");
        out.close();
    }

    static PriorityQueue<Project> positive, negative;
    static int N, start;

    static boolean doable(int allowed) {
        int skipped = 0;
        // Woww we really out here with not being able to use iterators huh
        PriorityQueue<Project> posCopy = new PriorityQueue<>(positive);
        PriorityQueue<Project> negCopy = new PriorityQueue<>(negative);
        int curr = start;
        // Now, let's go through our nicely ordered queues.
        // Because of the ordering, if we fail at any point, we fail
        // for any point later, so we can stop!
        boolean poss = true;
        while (posCopy.size() > 0) {
            Project next = posCopy.poll();
            if (curr >= next.need) {
                curr += next.delta;
            }
            else if (skipped < allowed) {
                // Now, we take advantage of our lifeline
                // We are forced to, so each skip is deterministic
                skipped++;
            }
            else {
                poss = false;
                break;
            }
        }
        if (poss) {
            while (negCopy.size() > 0) {
                Project next = negCopy.poll();
                if (curr >= next.need && curr + next.delta >= 0) {
                    curr += next.delta;
                }
                else if (skipped < allowed) {
                    skipped++;
                }
                else {
                    poss = false;
                    break;
                }
            }
        }
        return poss;
    }

    // Find the MINIMUM number of projects skipped
    // so that we can finish correctly.
    // Find the EARLIEST 1: 000011111 -> 01 case
    static int binarySearch() {
        int lo = 0;
        int hi = N; // having to skip everything
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (doable(mid)) {
                hi = mid; // latch on
            }
            else {
                lo = mid + 1; // creep up, since the mid will undershoot
            }
        }
        return lo;
    }

    static class Project {
        int need, delta;

        public Project(int n, int d) {
            need = n;
            delta = d;
        }

        public String toString() {
            return "(" + need + ", " + delta + ")";
        }

        public int getDelta() {
            return delta;
        }

        public int getNeed() {
            return need;
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
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}