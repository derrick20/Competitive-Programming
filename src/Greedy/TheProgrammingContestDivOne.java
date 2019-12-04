/*
 * @author derrick20
 * TopCoder Question. Dang that website is buggy af
 * Basically, we want to break down the question. With these different
 * costs and values, we clearly are optimizing. With that in mind,
 * we seek some greedy formulation. If we know that a pair of problems
 * must be solved, then we can show that they should be solved in order
 * of the ratio of time / deductRate = total loss. This makes sense, since
 * we don't want the squander our time on something when another problem
 * is leaking points faster. However, we still don't know WHICH of those
 * problems are actually worth solving. I was stuck on this for a while,
 * but with the algo live vid, I see that this is just like a coin change
 * problem, and we can't proceed merely with a greedy strategy. (hard to
 * guess that, but it's a recognization skill really). Thus, we finish
 * with a DP, and boom. Not sure why mine is missing on some system test,
 * but it passes on basically everything so eh. Lots of dumb long things, maybe?
 */
import java.util.*;

public class TheProgrammingContestDivOne {
    public static void main(String[] args) {
        int T = 	1000;
        int[] a1 = {1000, 1000};
        int[] a2 = {3, 4};
        int[] a3 = {10, 50};
        System.out.println(find(T, a1, a2, a3));
    }
    static class Problem implements Comparable<Problem> {
        long value;
        long deductRate;
        long time;

        public Problem(long v, long d, long t) {
            value = v; deductRate = d; time = t;
        }

        public int compareTo(Problem p2) {
            // We want the extra time spent on the first problem * loss rate for the second problem
            // meanwhile to be smaller.
            return (int) (time * p2.deductRate - p2.time * deductRate);
        }
    }

    public static int find(int T, int[] maxPoints, int[] pointsPerMinute, int[] requiredTime) {
        ArrayList<Problem> problems = new ArrayList<>();
        for (int i = 0; i < maxPoints.length; i++) {
            Problem p = new Problem((long) maxPoints[i], (long) pointsPerMinute[i], (long) requiredTime[i]);
            problems.add(p);
        }
        Collections.sort(problems);
        // Now, we have a coin change sort of problem
        // dp[i] stores the max possible score with time i remaining
        int[] dp = new int[T + 1];
        int ans = 0;
        for (Problem p : problems) {
            for (int time = T; time >= p.time; time--) {
                long plusValue = p.value - p.deductRate * time;
                // After waiting this WHOLE time, how much does this
                // problem offer us
                if (plusValue > 0) {
                    dp[time] = Math.max(dp[time], dp[time - (int) p.time] + (int) plusValue);
                }
                ans = Math.max(ans, dp[time]);
            }
        }
        System.out.println(Arrays.toString(dp));
        return ans;
    }
}