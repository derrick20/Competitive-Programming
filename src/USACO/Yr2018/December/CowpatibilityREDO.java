/*
 * @author derrick20
 * Arghh idiotic move was the LONG OVERFLOW OF 50,000^2!!!
 * GAHHH also the grader is harsh it can go out of time easily depending
 * on submission
 */
import java.io.*;
import java.util.*;

public class CowpatibilityREDO {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name) throws Exception {
        sc = new FastScanner(new FileReader(problem_name + ".in"));
        out = new PrintWriter(new FileWriter(problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner(System.in);
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
        setupIO();
        setupIO("cowpatibility");

        int N = sc.nextInt();
        HashMap<Integer, Integer> map = new HashMap<>();
        HashMap<String, Integer>[] matchGroups = new HashMap[6];
        for (int i = 1; i <= 5; i++) {
            matchGroups[i] = new HashMap<>();
        }

        ArrayList<Integer>[] lookup = new ArrayList[32];
        for (int j = 1; j <= 31; j++) {
            lookup[j] = new ArrayList<>();
            for (int k = 0; 1 << k <= j; k++) {
                if (((1 << k) & j) > 0) {
                    lookup[j].add(k);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            int[] arr = new int[5];
            for (int j = 0; j < 5; j++) {
                arr[j] = sc.nextInt();
                if (map.containsKey(arr[j])) {
                    arr[j] = map.get(arr[j]);
                }
                else {
                    map.put(arr[j], map.size());
                    arr[j] = map.get(arr[j]);
                }
            }
            Arrays.sort(arr); // quick ordering to make it standardized string key
            // Generate all subsets

            // 2^5 - 1, since no empty set allowed
            // For each subset, create a special key and add it to the map
            for (int j = 1; j <= 31; j++) {
                StringBuilder key = new StringBuilder();
                // k is the digit, keep climbing to count how many 1's there were
                for (int k : lookup[j]) {
                    key.append(arr[k] + "#");
                }
                update(matchGroups[lookup[j].size()], key.toString());
            }
        }

        int[] sign = {0, -1, 1, -1, 1, -1};
        long total = (long) N * (N - 1) / 2;
        for (int size = 1; size <= 5; size++) {
            long groups = 0;
            for (int groupCt : matchGroups[size].values()) {
                if (groupCt > 1) {
                    groups += (long) groupCt * (groupCt - 1) / 2;
                }
            }
            total += sign[size] * groups;
        }
        out.println(total);
        out.close();
    }

    static void update(HashMap<String, Integer> map, String key) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + 1);
        }
        else {
            map.put(key, 1);
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