
import java.io.*;
import java.util.*;
/*
my 6.in
15374341 9590
12154901 362645250
830170741 303256124
371102715 689260712
23292404 975987317
944283080
 */

public class MoorioKart2 {
    static String PROBLEM_NAME = "mooriokart";
    static boolean TESTING = true;

    static final long MOD = (1000 * 1000 * 1000) + 7;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner;
        PrintWriter writer;
        if (TESTING) {
            scanner = new Scanner(new FileReader("src/USACO/Yr2019/February/Platinum/mooriokart_platinum_feb19/6.in"));;
            writer = new PrintWriter(System.out);
        } else {
            scanner = new Scanner(new FileInputStream(PROBLEM_NAME + ".in"));
            writer = new PrintWriter(new FileOutputStream(PROBLEM_NAME + ".out"));
        }
        solve(scanner, writer);
        writer.flush();
        writer.close();
    }

    static void solve(Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        long x = in.nextInt();
        long y = in.nextInt();
        Meadow[] meadows = new Meadow[n];
        for (int i = 0; i < n; i++) {
            meadows[i] = new Meadow(i);
        }
        for (int i = 0; i < m; i++) {
            int a = in.nextInt() - 1;
            int b = in.nextInt() - 1;
            long d = in.nextInt();
            meadows[a].edges.put(meadows[b], d);
            meadows[b].edges.put(meadows[a], d);
        }
        Map<Integer, List<Long>> pathLists = new HashMap<>();
        for (Meadow root : meadows) {
            List<Long> paths = new ArrayList<>();
            int minIx = root.ix;
            long currLength = 0;
            root.importantEdgeLength = 0;
            Stack<Meadow> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                Meadow meadow = stack.pop();
                if (meadow.counted) {
                    meadow.counted = false;
                    currLength -= meadow.importantEdgeLength;
                } else {
                    meadow.counted = true;
                    currLength += meadow.importantEdgeLength;
                    minIx = Math.min(minIx, meadow.ix);
                    stack.push(meadow);
                    if (meadow.ix > root.ix) {
                        paths.add(currLength);
                    }
                    for (Map.Entry<Meadow, Long> edge : meadow.edges.entrySet()) {
                        if (edge.getKey().counted) {
                            continue;
                        }
                        edge.getKey().importantEdgeLength = edge.getValue();
                        stack.push(edge.getKey());
                    }
                }
            }
            if (pathLists.containsKey(minIx)) {
                pathLists.get(minIx).addAll(paths);
            } else {
                pathLists.put(minIx, paths);
            }
        }
//        for (List<Long> path : pathLists.values()) {
//            Collections.sort(path);
//            out.println(path);
//        }

        int k = pathLists.size();
        long practicalY = Math.max(0, y - (x * k));
        long[][][] pathSums = new long[k][(int) practicalY + 1][2];
        int i = 0;
        // PathSums stores at a given component, a given path length, the real AMOUNT * the frequency, and that frequency
        for (List<Long> pathList : pathLists.values()) {
            for (long path : pathList) {
                pathSums[i][(int) Math.min(practicalY, path)][0] += path + x;
                pathSums[i][(int) Math.min(practicalY, path)][0] %= MOD;
                pathSums[i][(int) Math.min(practicalY, path)][1] ++;
                pathSums[i][(int) Math.min(practicalY, path)][1] %= MOD;
            }
            i++;
        }

        long[][] dpPrev = pathSums[0];
        for (i = 1; i < k; i++) {
            System.out.println(Arrays.toString(dpPrev[(int) practicalY]));
            long[][] dpNext = new long[(int) practicalY + 1][2];
            for (int j = 0; j <= practicalY; j++) {
                // Skip stuff if this value was never achieved
                if (pathSums[i][j][0] == 0 && pathSums[i][j][1] == 0) {
                    continue;
                }
//                System.out.println(i + " " + Arrays.toString(pathSums[i][j]));
//                System.out.println(Arrays.toString(dpPrev[(int) practicalY]) + " ");

                for (int l = 0; l <= practicalY; l++) {
                    dpNext[(int) Math.min(j + l, practicalY)][0] += (pathSums[i][j][1] * dpPrev[l][0]) + (dpPrev[l][1] * pathSums[i][j][0]);
                    dpNext[(int) Math.min(j + l, practicalY)][0] %= MOD;
                    dpNext[(int) Math.min(j + l, practicalY)][1] += dpPrev[l][1] * pathSums[i][j][1];
                    dpNext[(int) Math.min(j + l, practicalY)][1] %= MOD;

                }

            }

            dpPrev = dpNext;
        }
        long answer = dpPrev[(int) practicalY][0];
        for (i = 1; i <= k - 1; i++) {
            answer *= i * 2;
            answer %= MOD;
        }
        out.println(answer);
    }

    static class Meadow {
        final int ix;
        final Map<Meadow, Long> edges = new HashMap<>();
        boolean counted = false;
        long importantEdgeLength = -1;

        Meadow(int ix) {
            this.ix = ix;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Meadow meadow = (Meadow) o;
            return ix == meadow.ix;
        }

        @Override
        public int hashCode() {
            return Objects.hash(ix);
        }
    }
}
