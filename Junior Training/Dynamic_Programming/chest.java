import java.io.*;
import java.util.*;

public class chest {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int N = sc.nextInt(), S = sc.nextInt();
            if (N == -1)
                break;
            memo = new int[2][N+1][N+1];
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < N+1; j++)
                    for (int k = 0; k < N+1; k++)
                        memo[i][j][k] = -1;


            System.out.println(solve(1,1,1)); // 1
            System.out.println(solve(0,1,1)); // 0
            System.out.println(solve(1,1,0)); // 1
            System.out.println(solve(0,1,0)); // 2¸
            System.out.println(solve(1, N, S));
            // solve! solve(0, 0, N-1,  N, S);
        }
    }

    static int[][][] memo;
    public static int solve(int above, int rem, int secure) {
        if (memo[above][rem][secure] != -1) {
            return memo[above][rem][secure]; // use the stored value
        }
        else if (secure == 0) { // we have reached the correct number of secure drawers
            return 1;
        }
        else if (rem < secure || rem <= 0) { // This means we won't be able to secure enough of them reach the goal, so it is
            return 0; // impossible. (Prune this essentially)
        }
        else {
            int unlocked, locked; // Looking at the two possibilities, either lock this drawer or don't, then move on
            // to a sub-problem
            if (above == 0) { // unlocked
                unlocked = solve(0, rem-1, secure);
                locked = solve(1, rem-1, secure);
            }
            else { // above = 1, and locked
                unlocked = solve(0, rem-1, secure);
                locked = solve(1, rem-1, secure-1); // This is the only case where we gain a
                // secure drawer. The way I was doing earlier was viewing in the opposite perspective, but this is
                // more intuitive. Of the 4 cases, looking at the one above and the one at this level, we are only safe
                // if the above is safe too.
            }
            return memo[above][rem][secure] = unlocked + locked;
        }
        /*if (index >= end+1 || secure < needed || end - index + 1 < needed - secure) {
            return 0; // We're done, finished solving all of the shelves
        }
        else if (index == end) { // Base case, when we have only one drawer left. (Base cases are in reverse order!)
            if (secure == needed && above == 0) {
                return 1; // We simply lock the current drawer
            }
            else if (secure == needed && above == 1) {
                return 0; // we will automatically have too few secure
            }
            else if (secure == needed + 1 && above == 0) { // We unlock the current drawer
                return 1;
            }
            else { // if (secure == needed + 1 && above == 1) { // No matter what we do, the bottom will be free, so 2 ways
                return 2;
            }
        }
        else if (memo[above][index][secure] != -1)  {
            return memo[above][index][secure]; // Use our stored information
        }
        else {
            if (above == 0) { // Above is locked
                int unlocked = solve(1, index+1, end, secure-1, needed);
                int locked = solve(0, index+1, end, secure, needed);
                return memo[above][index][secure] = unlocked + locked;
            }
            else { // Above is unlocked
                // Thus, regardless of what we choose, the number secure must go down by one, since we are below
                // something that is unlocked. Our logic looks at how we are affected, leaving our effects
                // to the later computation (forming a repeatable structure)
                int unlocked = solve(1, index+1, end, secure-1, needed);
                int locked = solve(0, index+1, end, secure-1, needed);
                return memo[above][index][secure] = unlocked + locked;
            }
        }*/
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