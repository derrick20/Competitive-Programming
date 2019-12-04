import java.io.*;
import java.util.*;
/*
 * USACO Silver 2018 January Lifeguards
 * Wow i literally did the same thing a year and a half later. WHY doesn't it workk!
 * @derrick20
 *
 * Arup's new idea: DP on how many ways you can either be the one removed
 * (using all of the things before, or remove this one for the first time)
 * DP[index][used or not]
 */

public class Lifeguards {
    public static void main(String[] args) throws Exception {
//        Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(new FileReader("lifeguards.in"));
        PrintWriter out = new PrintWriter(new FileWriter("lifeguards.out"));
        int N = sc.nextInt();
        ArrayList<Pair> arr = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int start = sc.nextInt();
            int end = sc.nextInt();
            int len = end - start;
            arr.add(new Pair(start, len));
        }
        Collections.sort(arr, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return o1.start - o2.start;
            }
        });
        long total = 0;

        long minLoss = (long) 1e9;
        for (int i = 0; i < N; i++) {
            Pair curr = arr.get(i);
            // Compress everything to be non-intersecting
            long loss = curr.len;

            if (i >= 1) {
                Pair prev = arr.get(i - 1);
                int prevEnd = prev.start + prev.len;
                // If previous overlaps with us, shorten our loss too
                if (prevEnd > curr.start) {
                    loss -= (prevEnd - curr.start);
                }
            }
            if (i < N - 1){
                Pair next = arr.get(i + 1);
                int currEnd = curr.start + curr.len;
                // Also, track the total coverage

                // If we are overlapping with the next, shorten our loss
                if (currEnd > next.start) {
                    loss -= (currEnd - next.start);
                    total -= (currEnd - next.start);
                }
                total += curr.len;
            }
            minLoss = Math.min(minLoss, loss);
        }
        total += arr.get(N - 1).len; // since we just arbitrarily chose to update based on overlapping with NEXT
        // so the N-1th has no next. So just add it
        // Now, total will have the total union-ed area

        out.println(total - minLoss);
        out.close();
    }

    static class Pair {
        int start, len;

        public Pair(int start, int len) {
            this.start = start;
            this.len = len;
        }

        public String toString() {
            return "(" + start + ", " + len + ")";
        }
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

