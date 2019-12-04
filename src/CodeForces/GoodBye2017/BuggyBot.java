import java.io.*;
import java.util.*;
/*
 * CodeForces GoodBye2017 BuggyBot
 * @derrick20
 */

public class BuggyBot {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        int x1 = 0, y1 = 0;
        char[][] grid = new char[N+2][M+2];
        for (int i = 0; i < grid.length; i++) {
            if (i == 0 || i == grid.length - 1) {
                for (int j = 0; j < grid[0].length; j++) {
                    grid[i][j] = block;
                }
            }
            else {
                char[] line = sc.next().toCharArray();
                for (int j = 0; j < grid[0].length; j++) {
                    if (j == 0 || j == grid[0].length - 1) {
                        grid[i][j] = block;
                    }
                    else {
                        // it will do this line for j = 1 -> M
                        // so, all we need to do is do: 0 -> M-1 (shift down by 1)
                        grid[i][j] = line[j - 1];
                        if (grid[i][j] == start) {
                            x1 = i; // doesn't matter which is which, just that we are consistent
                            y1 = j;
                            // Really, you could just imagine rotating the grid counterclockwise 90,
                            // and it'd be the origin correctly
                        }
                    }
                }
            }
        }
        String moves = sc.next();
        maps = new ArrayList<>();
        generatePermutations("0123", "");
        //System.out.println(maps);
        int ct = 0;
        for (String map : maps) {
            if (isValid(x1, y1, moves, map, grid)) {
                ct++;
            }
        }
        System.out.println(ct);

    }
    static char start = 'S';
    static char end = 'E';
    static char block = '#';
    static ArrayList<String> maps;
    // Map 0, 1, 2, 3 to the REAL E, N, W, S directions
    // Make an array of maps (strings), and the index returns the
    // mapped direction in the current permutation
    // "3210" means go W if see a 3, S if see a 2, etc.
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    static boolean isValid(int x, int y, String moves, String map, char[][] grid) {
        for (char c : moves.toCharArray()) {
            // oops, really long lol
            // use the map to get the mapped direction at the index of the char
            int move = Character.getNumericValue(map.charAt(Character.getNumericValue(c)));
            x += dx[move];
            y += dy[move];
            if (grid[x][y] == block) {
                return false;
            }
            else if (grid[x][y] == end) {
                return true;
            }
        }
        return false; // Never reached an end
    }

    // Permutations of a string. The idea is to use exactly all remaining
    // characters (stored in "curr"), as the letter at the current position
    // (which is implicitly stored based on how large "done" is)
    static void generatePermutations(String curr, String done) {
        if (curr.length() == 0) {
            maps.add(done);
            return;
        }
        for (int i = 0; i < curr.length(); i++) {
            char c = curr.charAt(i);
            String shrunken = curr.substring(0, i) + curr.substring(i + 1);
            String enlarged = done + c;
            generatePermutations(shrunken, enlarged);
        }
    }

    static void display(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
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

