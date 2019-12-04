import java.io.*;
import java.util.*;
/*
 * CodeForces New Year and Fireworks
 * @derrick20
 * Very hard, but eventually got through by working through someone's solution
 * The missing insight was how many dimensions to track in the dp table.
 * Learned that using boolean arrays saves a lot of time. Avoid using Pair classes
 * and such. Converting the directionalities into the circular 1,2,... 7 representation
 * also saved quite some time. Try to use bottom-up in the future, even if top-down is easier
 * I went too far into the dimensions, when you should try to represent it in as few ways as possible
 * Ideally, in a simulation, go as coarse-grained as possible to save computation time
 */

public class DRedone {
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
        bound = 2*max + 1;
        reached = new boolean[bound][bound];
        visited = new boolean[bound][bound][N][8];
        // Directions:
        // 3 2 1
        // 4   0
        // 5 6 7

        // A state is x, y, current time since explosion, recursive level, direction

        dfs(max, max, 2, 1, 0);
        for (int i = 0; i < bound; i++) {
            for (int j = 0; j < bound; j++) {
                if (reached[i][j]) {
                    ct++;
                }
            }
        }
        System.out.println(ct);
    }
    static int N;
    static int bound;
    static int[] explode;
    // CRUCIAL SPEEDUPS: use boolean arrays for visited ALWAYS
    static boolean[][][][] visited;
    static boolean[][] reached;
    static int ct = 0;
    static int[] dx = {1, 1, 0, -1, -1, -1, 0, 1};
    static int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    static void dfs(int col, int row, int dir, int covered, int level) {
        if (level == N) {
            // If we have visited it with the same velocity, or finished the recursion, then stop
            return;
        }
        // We need to watch out, since if we explode at a location, it's fine to visit with the same spot, since we'll
        // add new locations
        if (visited[row][col][level][dir]) {
            return;
        }
        else {
            // we mark STARTING POINTS FROM EXPLOSION as visited!!
            // The issue before was that I was trying to track how far we've covered,
            // but in reality, the only way someone could achieve the same state, including
            // the cover, is if they had arrived from the same starting state/level
            // With this trick, we can drop a dimension of our dp table.
            visited[row][col][level][dir] = true;

            reached[col][row] = true;
            while (covered < explode[level]) {
                col += dx[dir];
                row += dy[dir];
                reached[col][row] = true;
                covered++;
            }
            // It's time + 1 because we cover one more tile than the current time
            // At the current level, if enough time has passed, we must explode into two parts
            int adjDir1 = (dir + 1) % 8;
            int adjDir2 = (dir - 1 + 8) % 8;
            dfs(col + dx[adjDir1], row + dy[adjDir1], adjDir1, 1, level + 1);
            dfs(col + dx[adjDir2], row + dy[adjDir2], adjDir2, 1, level + 1);
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
