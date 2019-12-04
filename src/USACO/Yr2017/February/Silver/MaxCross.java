/*
 * @author derrick2
 * My immediate intuition is to try to construct a bunch of chains, and have
 * broken lights marked uniquely. Why should we do that? We would have to check
 * if we are above the desired goal count. Instead, it makes more sense to
 * just do things more organizedly. That would involve a while loop moving forward
 * until we found a good enough size. In fact, that way is valid, but is complex.
 * (it could actually be done more effectively by sorting the numbers of the positions
 * with broken lights, and then we would just shift our right pointer up to the next broken
 * one, then shift our left one up). That method is more effective in cases where the
 * position may not just be in the range of 10^9! We relied on the cleverness
 * of just scanning all block ranges to see if they could be doable.
 *
 * Ooh clever I did my own way, but had to rely on the clever optimization of jumping
 * between different broken lights
 */
import java.io.*;
import java.util.*;

public class MaxCross {
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
//        setupIO("maxcross");
        int N = sc.nextInt();
        int K = sc.nextInt();
        int B = sc.nextInt();
        int[] broken = new int[B + 2];
        for (int i = 1; i <= B; i++) {
            broken[i] = sc.nextInt();
        }
        // Let's add one last point to the end and start, since that's necessary in case no break
        // which leaves the end open
        broken[0] = 0;
        broken[B + 1] = N + 1;
        Arrays.sort(broken);
        int left = 0;
        int right = 0;
        int best = (int) 1e9;
        outer: while (right < broken.length) {
            // Preserve the right we solved for, for the previous left pointer.
            // By the two-pointer principle of greediness, this solution MUST USE
            // at least as much as the previous
            while (broken[right] - broken[left] < K) {
                right++;
                if (right == broken.length) {
                    break outer;
                }
            }
            int lights = right - left;
            if (broken[right] - broken[left] - 1 >= K) {
                lights--; // We realize we didn't need that last light.
            }
            best = Math.min(best, lights);
            // This is the clever optimization. If we have an optimal solution
            // the left pointer must be RIGHT adjacent to a broken light.
            // If we shifted it anymore, it'd be wasting free good lights!
            // Similarly, we can let the right must end at a broken light.
            left++;
        }
        out.println(best);
        /* Nice and fast rolling sum method!
        for (int i = 0; i < B; i++) {
            int pos = sc.nextInt() - 1;
            arr[pos] = 1;
        }
        int roll = 0;
        for (int i = 0; i < K; i++) {
            roll += arr[i];
        }
        // Now that we've built up a sufficient ball, let's step forward and pick up one of the previous
        // Kinda like floor is lava community LOL
        int min = roll;
        for (int i = K; i < N; i++) {
            roll -= arr[i - K];
            roll += arr[i];
            min = Math.min(min, roll);
        out.println(min);
        }*/

        out.close();
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