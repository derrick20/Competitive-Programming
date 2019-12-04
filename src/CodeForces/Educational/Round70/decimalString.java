/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class decimalString {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String p = sc.next();

        int[] path = new int[p.length()];
        int i = 0;
        for (char c : p.toCharArray()) {
            path[i++] = Character.getNumericValue(c);
        }
//        int[][] jump = new int[10][10];
//        for (int amt = 0; amt < 10; amt++) {
//            Arrays.fill(jump[amt], -1);
//            jump[amt][0] = 0;
//            for (int delta = 1; delta < 10; delta++) {
//                if (jump[amt][((delta - 1) * amt) % 10] != -1) {
//                    if (jump[amt][(delta * amt) % 10] == -1) {
//                        jump[amt][(delta * amt) % 10] = jump[amt][((delta - 1) * amt) % 10] + 1;
//                    }
//                    else {
//                        jump[amt][(delta * amt) % 10] = Math.min(jump[amt][(delta * amt) % 10], jump[amt][((delta - 1) * amt) % 10] + 1);
//                    }
//                }
//            }
//        }
        int[][] ans = new int[10][10];
        int[][][] jump = new int[10][10][10];
        int oo = (int) 1e9;
//        for (i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                for (int delta = 0; delta < 10; delta++) {
//                    int step = -1;
//                    boolean[] visited = new boolean[10];
//                    // Store a given amt, and delta, the best step!
//                    ArrayDeque<Pair> q = new ArrayDeque<>();
//                    q.add(new Pair(0, 0));
//                    while (!q.isEmpty()) {
//                        Pair top = q.pollFirst();
//                        if (!visited[top.cur]) {
//                            visited[top.cur] = true;
//                            int in = top.in;
//                            if (top.cur == delta) {
//                                step = in; // found it!
//                                break;
//                            } else {
//                                in++;
//                            }
//                            if (!visited[(top.cur + i) % 10]) {
//                                q.add(new Pair((top.cur + i) % 10, in));
//                            }
//                            if (!visited[(top.cur + j) % 10]) {
//                                q.add(new Pair((top.cur + j) % 10, in));
//                            }
//                        }
//                    }
//                    jump[i][j][delta] = step;
//                }
////                System.out.println(i + " " + j + "  "+ Arrays.toString(jump[i][j]));
//            }
//        }


        // for a given step size, and delta, quickest number of steps
        for (i = 0; i < 10; i++) {
            for (int j = 0; j <= i; j++) {
                Arrays.fill(jump[i][j], -1);
                // Generate all combinations of x * i + y * j = some amount
                // and minimize x + y for all amounts
                jump[i][j][i] = 0;
                jump[i][j][j] = 0;
                if (i == 0 && j == 0) {
                    continue;
                }
                for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < 10; y++) {
                        int amount = (x * i + y * j);
                        int size = x + y - 1;
                        if (x + y <= 1) {
                            continue;
                        }
                        if (jump[i][j][amount % 10] == -1) {
                            jump[i][j][amount % 10] = size;
                        }
                        else {
                            jump[i][j][amount % 10] = Math.min(jump[i][j][amount % 10], size);
                        }
                    }
                }
            }
        }

        for (i = 0; i < 10; i++) {
            for (int j = 0; j <= i; j++) {
//                int inserted = 0;
//                for (int x = 1; x < path.length; x++) {
//                    int delta = (path[x] - path[x - 1] + 10) % 10;
//                    int step = -1;
//                    if (jump[i][delta] == -1 && jump[j][delta] == -1) {
//                        inserted = -1;
//                        break;
//                    }
//                    else if (jump[i][delta] != -1 && jump[j][delta] != -1) {
//                        step = Math.min(jump[i][delta], jump[j][delta]);
//                    }
//                    else if (jump[i][delta] != -1) {
//                        step = jump[i][delta];
//                    }
//                    else {
//                        step = jump[j][delta];
//                    }
//                    inserted += step - 1;
                int inserted = 0;
//                }
                for (int pos = 1; pos < path.length; pos++) {
                    int delta = (10 + path[pos] - path[pos - 1]) % 10;
                    if (jump[i][j][delta] == -1) {
                        inserted = -1;
                        break;
                    }
                    inserted += jump[i][j][delta];
//                    if (i + j > 0) {
//                        inserted--; // since 1 too many
//                    }
                }
                ans[i][j] = ans[j][i] = inserted;
            }
        }

        for (i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                out.print(ans[i][j] + " ");
            }
            out.println();
        }

        out.close();
    }

    static class Pair {
        int cur;
        int in;
        public Pair(int cur, int in) {
            this.cur = cur;
            this.in = in;
        }

        public String toString() {
            return "(" + cur + ", " +  in + ")";
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
