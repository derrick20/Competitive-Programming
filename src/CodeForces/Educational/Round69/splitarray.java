/*
 * @author derrick20
 * Ah, the mistake was trying to frame it as a DP problem/applying binary search
 * NEVER MAKE THAT MISTAKE: Don't default to trying to reuse old problems - Tutorial from ucf
 * Although you just tested the dp way, think about the bounds. There are only a few choices:
 * N log N or N. If former - either sorting or binary search most likely. We thought about binary search,
 * but that wouldn't work. Maybe sorting? Okay, but what are we sorting? It's already sorted, but
 * what we want to do is to separate the sequence. Let's try drawing it. Wait, it looks kinda like
 * a slide, with varying slopes. This intuitively suggests snipping the connections with greatest slope
 * Then, the answer is the difference in heights of the top and bot of each distinct slide. If we look at the
 * picture more carefully, we can just find the total drop, then just subtract the k-1 greatest single drops
 * (this leads to k separate slides). If we think a bit more, all we need to do is organize based on these
 * slide drops, so let's create an array with the N-1 differences, then sort and remove the biggest drops!
 * Boom, there's a N log N sorting factor!
 * Another way of realizing/understanding this is by writing out all these differences. In a sort of
 * telescoping fashion, we can add these to find the total drop across a bunch of slides (but that's obvious)
 *
 * Remember, the max memory is like 10^9, so N^2 memory will die. don't even try the DP if you see that (unless
 * somehow optimizable)
 */
import java.util.*;
import java.io.*;

public class splitarray {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int K = sc.nextInt();
        int[] arr = new int[N];
        int[] diff = new int[N];
        int totalDrop = 0;
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
            if (i > 0) {
                diff[i] = arr[i] - arr[i - 1];
                totalDrop += diff[i];
            }
        }
        Arrays.sort(diff);
        int snippedDrops = 0;
        for (int i = (N - 1); i > (N - 1) - K + 1; i--) {
            snippedDrops += diff[i];
        }
        out.println(totalDrop - snippedDrops);
        out.close();
    }
//    static int N, K;
//    static long inf = (long) 1e18;
//    static long[][] memo;
//    static int[] arr;

//    static long cost;
//    static long solve(int i, int g) {
//        if (i == N) {
//            if (g <= 0) {
//                return 0;
//            }
//            else {
//                return inf;
//            }
//        }
//        if (g <= 0) {
//            return inf;
//        }
//
//        if (memo[i][g] != 0) {
//            return memo[i][g];
//        }
//
//        else {
//            long best = inf;
//            for (int j = i; j < N; j++) {
//                best = Math.min(best, arr[j] - arr[i] + solve(j + 1, g - 1));
//            }
//            return memo[i][g] = best;
//        }
//    }

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