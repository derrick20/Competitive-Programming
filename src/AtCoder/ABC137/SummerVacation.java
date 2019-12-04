/**
 * @author derrick20
 * Argh. I was stuck on wanting to make it into DP. Always think, can I make it
 * true that my idea will always optimize the solution? In this case, we can
 * consider it this way. If we go from highest value jobs first, and it is possible
 * to place it down into some position. Then it should be true that no other job
 * would be better to place there. However, the caveat that I was missing is that
 * this is untrue if you place it in a poor location that COULD displace later
 * opportunities. In reality you want to keep options open by putting it as late
 * as possible. The way I designed it was a bit ugly, because I had to search for
 * the best available day for each job, seeing if it was possible or not. We
 * can certainly rule out a job if we placed all prior ones in the latest optimal
 * fashion.
 *
 * An alternate approach would be to go through each job, and assign it to a bin containing
 * all jobs that can start any time before a given bin TIME. This means that,
 * if we work from the LEAST RESTRICTIVE, and work our way backwards, we will
 * gradually build up a bag containing all possible jobs for a given day. If
 * it is empty, we naturally move on. This is a nice application of the
 * solution bag idea, except here we do almost reverse of above. We go check
 * each DAY to see the best of what values could go there. For mine,
 * we go through the best jobs, and see if they can go in a certain place or
 * earlier. A job not going in the latest place for my solution is analogous
 * to a job not immediately being taken out from the solution bag, and instead
 * being used on an earlier, more restricted area (it surpassed another job here).
 */
import java.io.*;
import java.util.*;

public class SummerVacation {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int M = sc.nextInt();
        PriorityQueue<Job> jobs = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            int delay = sc.nextInt();
            int val = sc.nextInt();
            jobs.add(new Job(val, delay));
        }
        TreeSet<Integer> spots = new TreeSet<>();
        for (int i = 0; i < M; i++) {
            spots.add(i);
        }
        int day = 0;
        long total = 0;
        while (day < M && jobs.size() > 0) {
            // We want to use either M - DELAY, or something earlier if taken!
            if (spots.lower(M - jobs.peek().delay + 1) != null) {
                int latestDay = spots.lower(M - jobs.peek().delay + 1);
//                System.out.println(latestDay);
                spots.remove(latestDay);
                // maintain invariant of best value on top
                total += jobs.poll().val;
                day++;
            }
            else {
                // That job couldn't go anywhere. We had greedily found better
                // values for all time slots this one could fit. So baby it's worthless
                jobs.poll();
            }
        }
        out.println(total);
        out.close();
    }

    static class Job implements Comparable<Job>{
        int val, delay;

        public Job(int v, int d) {
            val = v; delay = d;
        }

        public int compareTo(Job j2) {
            return j2.val - val; // big val first!
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