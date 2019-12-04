/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class letterShop {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int N = sc.nextInt();
        String shop = sc.next();
        int friends = sc.nextInt();

        int[][] letterCount = new int[26][N];
        int i = 0;
        for (char c : shop.toCharArray()) {
            letterCount[c - 'a'][i++]++;
        }
        // prefix counts
        for (i = 0; i < 26; i++) {
            for (int j = 1; j < N; j++) {
                letterCount[i][j] += letterCount[i][j - 1];
            }
        }

        for (int f = 0; f < friends; f++) {
            int[] needs = new int[26];
            String name = sc.next();
            for (char c : name.toCharArray()) {
                needs[c - 'a']++;
            }
            int[] firstPos = new int[26];
            int max = 0;
            for (i = 0; i < 26; i++) {
                if (needs[i] != 0) {
                    // search the first position where we have enough
                    firstPos[i] = Arrays.binarySearch(letterCount[i], needs[i]);
//                    firstPos[i] = binarySearch(letterCount[i], needs[i]);
                    max = Math.max(firstPos[i], max);
                }
            }
            out.println(max + 1);
        }
        out.close();
    }

    static int binarySearch(int[] arr, int goal) {
        int lo = 0;
        int hi = arr.length - 1;
//        int ans = 0;
        while (lo <= hi) {
            int pivot = lo + (hi - lo) / 2;
            if (arr[pivot] < goal) {
                lo = pivot + 1;
            }
            else {
                hi = pivot - 1;
            }
        }
        return lo;
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
