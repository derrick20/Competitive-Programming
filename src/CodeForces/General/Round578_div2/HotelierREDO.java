/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class HotelierREDO {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int N = sc.nextInt();
        int[] arr = new int[10];
        char[] chars = sc.next().toCharArray();
        for (char c : chars) {
            System.out.println(Arrays.toString(arr));
            if (c == 'L') {
                for (int i = 0; i < 10; i++) {
                    if (arr[i] == 0) {
                        arr[i] = 1;
                        break;
                    }
                }
            }
            else if (c == 'R') {
                for (int i = 9; i >= 0; i--) {
                    if (arr[i] == 0) {
                        arr[i] = 1;
                        break;
                    }
                }
            }
            else {
                int pos = Character.getNumericValue(c);
                arr[pos] = 0;
            }
        }

        for (int x : arr) {
            out.print(x);
        }
        out.println();

        out.close();
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