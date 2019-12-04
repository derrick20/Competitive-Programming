import java.io.*;
import java.util.*;
/*
 * CodeForces New Year and Rating
 * @derrick20
 *  Used hint unfortunately, was trying binary search  to no avail. The idea of upper and lower
 * bound is quite similar to binary search, should've considered the math of the inequalities earlier...!
 */

public class D {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        // Store the time at which a given level will explode
        explode = new int[N];
        int max = 0;
        for (int i = 0; i < N; i++) {
            explode[i] = sc.nextInt();
            max += explode[i];
        }
        Pair[] directions = {R, U, L, D, UR, UL, DR, DL};
        int bound = 2*max + 1;
        reached = new int[bound][bound];
        visited = new HashMap[bound][bound][6][N];
        // Track all positions/velocity states, so dfs doesn't waste effort
        for (int i = 0; i < bound; i++) {
            for (int j = 0; j < bound; j++) {
                for (int k = 0; k < 6; k++) {
                    for (int l = 0; l < N; l++) {
                        HashMap<Pair, Boolean> map = new HashMap<>();
                        for (Pair dir : directions) {
                            map.put(dir, false);
                        }
                        visited[i][j][k][l] = map;
                    }
                }
            }
        }

        dfs(max, max, U, 1, 0);
        /*for (int i = 160; i >= 140; i--) {
            for (int j = 140; j <= 160; j++) {
                System.out.print(reached[i][j] != 0 ? reached[i][j] : " ");
            }
            System.out.println();
        } //*/
        System.out.println(ct);
    }
    static int N;
    static int[] explode;
    static HashMap<Pair, Boolean>[][][][] visited;
    static int[][] reached;
    static int ct = 0;
    static Pair R = new Pair(1, 0);
    static Pair U = new Pair(0, 1);
    static Pair L = new Pair(-1, 0);
    static Pair D = new Pair(0, -1);
    static Pair UR = U.add(R);
    static Pair UL = U.add(L);
    static Pair DR = D.add(R);
    static Pair DL = D.add(L);

    static void dfs(int col, int row, Pair dir, int covered, int level) {
        if (level == N) {
            // If we have visited it with the same velocity, or finished the recursion, then stop
            return;
        }
        // We need to watch out, since if we explode at a location, it's fine to visit with the same spot, since we'll
        // add new locations
        if (visited[row][col][covered][level].get(dir)) {
            //System.out.println(col + " " + row + " " + covered);
            reached[row][col]++;
            return;
        }
        else {
            // Now that we've reached here, mark it as visited with the velocity. Future calls that reach this
            // same state will terminate early
            visited[row][col][covered][level].put(dir, true);
            if (reached[row][col] == 0) {
                ct++; // Keep track of the total number of spots reached!
                reached[row][col] += 1;
            }

            // It's time + 1 because we cover one more tile than the current time
            if (explode[level] == covered) {
                // At the current level, if enough time has passed, we must explode into two parts
                for (Pair adjDir : dir.getAdj()) {
                    dfs(col + adjDir.x, row + adjDir.y, adjDir, 1, level + 1);
                }
            }
            else {
                // Otherwise, continue in the direction with more time
                dfs(col + dir.x, row + dir.y, dir, covered + 1, level);
            }
        }
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Pair add(Pair p2) {
            return new Pair(this.x + p2.x, this.y + p2.y);
        }

        boolean equals(Pair p2) {
            return this.x == p2.x && this.y == p2.y;
        }

        Pair[] getAdj() {
            if (this.equals(R))
                return new Pair[] {UR, DR};
            else if (this.equals(L))
                return new Pair[] {DL, UL};
            else if (this.equals(D))
                return new Pair[] {DR, DL};
            else if (this.equals(U))
                return new Pair[] {UL, UR};
            else if (this.equals(UR))
                return new Pair[] {U, R};
            else if (this.equals(UL))
                return new Pair[] {U, L};
            else if (this.equals(DR))
                return new Pair[] {D, R};
            else //if (this.equals(DL))
                return new Pair[] {D, L};
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
