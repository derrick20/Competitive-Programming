/*
 * @author derrick20
 * TODO Hmm I came up with a decent idea for DP but I got stuck on fixing the ranges..
 * Now trying to implement their solution :(
 */
import java.io.*;
import java.util.*;

public class shortColorfulStrip {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        // although N = M for problem F1
        int M = sc.nextInt();
        int[] zeroes = new int[N];
        Arrays.fill(zeroes, 0);
        base = new State(zeroes);
        memo = new HashMap<>();

        int[] start = new int[N];
        for (int i = 0; i < N; i++) {
            start[i] = i + 1;
        }
        long ans = solve(new State(start), N, N - 1, N - 1);
        out.println(ans);
        out.close();
    }
    static int N;
    static State base;
    static long mod = 998244353;

    static HashMap<State, Long> memo;

    static long solve(State state, int color, int left, int right) {
        if (state.compareTo(base) == 0) {
            // only 1 way to finish it off
            return 1;
        }
        if (memo.containsKey(state)) {
            return memo.get(state);
        }
        HashSet<Integer> choices = new HashSet<>();
        if (right == N - 1 || left == 0) {
            for (int c = 1; c < color; c++) {
                choices.add(c);
            }
        }
        else {
            choices.add(state.color[left - 1]);
            choices.add(state.color[right + 1]);
        }
        long ans = 0;
        // we can go to any color from here
        for (int c : choices) {
            // move on to the next color
            int newL = left - 1;
            // TODO THIS IS THE ONLY PROBLEM WITH MY CODE
            // TODO OTHERWISE THE DP SHOULD WORK...
            int newR = c == color ? right : right - 1;
            if (left > 0 && state.color[left - 1] == c) {
                newL--;
            }
            if (right < N - 1 && state.color[right + 1] == c) {
                newR++;
            }
            State copy = new State(state.color);
            for (int i = left; i <= right; i++) {
                copy.color[i] = c;
            }
            ans = (ans + solve(copy, c, newL, newR)) % mod;
        }
        memo.put(state, ans);
        return ans;
    }

    static class State implements Comparable<State> {
        int[] color;

        public State(int[] c) {
            color = c;
        }

        public int compareTo(State s2) {
            return Arrays.compare(color, s2.color);
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
