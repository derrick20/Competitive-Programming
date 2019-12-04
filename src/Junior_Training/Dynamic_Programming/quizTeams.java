import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

// Note, we also could've used a 2D array to store the x's and y's


public class quizTeams {
    static NumberFormat df = new DecimalFormat("#.00");

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N;
        int t = 1;
        while ((N =sc.nextInt()) != 0) {
            HashMap<Integer, Pair> map = new HashMap<>();
            for (int i = 0; i < 2*N; i++) {
                sc.next();
                map.put(1 << i, new Pair(sc.nextInt(), sc.nextInt()));
            }
            memo = new double[1 << 2*N];
            double d = solve((1 << 2*N) - 1, map);
            //BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN);
            System.out.println("Case " + t + ": " + df.format(d));
            t++;
        }
    }

    static double[] memo;
    public static double solve(int state, HashMap<Integer, Pair> map) {
        if (memo[state] != 0) {
            return memo[state];
        }
        else if (state == 0) { // Base case, of recursion: nothing left, so the min distance is guaranteed to be 0
            return 0;
        }
        else {
            // Move up the bitstring by doubling. If we ever see that a person is unpaired
            // (i.e. they are a one, then let us select the second member using another for loop. Then dp on a smaller state
            // Essentially trying the whole triangle of combination, but ensuring the if statements
            // to prevent trying to match players in a state when they have been used already (0), so we can't
            // pull out a 1 from it
            double min = 1e9 + 7;
            for (int start = 1; start <= state; start <<= 1) {
                Pair p1 = map.get(start);
                if ((start & state) != 0) {
                    for (int end = start << 1; end <= state; end <<= 1) {
                        if ((end & state) != 0) {
                            Pair p2 = map.get(end);
                            double dist = dist(p1, p2);
                            double x = dist + solve(state ^ start ^ end, map);
                            min = Math.min(min, x);
                        }
                    }
                }
            }
            return memo[state] = min;

        }
    }

    public static double dist(Pair p1, Pair p2) {
        return Math.sqrt((Math.pow(p1.x-p2.x, 2) + Math.pow(p1.y-p2.y, 2)));
    }

    static class Pair {
        int x, y;

        public Pair(int xPos, int yPos) {
            x = xPos;
            y = yPos;
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
