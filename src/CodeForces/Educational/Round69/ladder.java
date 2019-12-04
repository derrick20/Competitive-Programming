/*
 * @author derrick20
 * Simple idea: Just go through the sorted and always use the biggest planks for the two supports
 * Then, go through all other planks until we either run out of length (reach min of the support - 1)
 * or we run out of planks
 * WOW F U HACKER GREENCIS he put the case where Arrays.sort fails since
 * quicksort. The best thing to do is to Collections.sort or to use Integer/object sorting
 */
import java.io.*;
import java.util.*;

public class ladder {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int C = sc.nextInt();
        while (C-->0) {
            int N = sc.nextInt();
            int[] arr = new int[N];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = sc.nextInt();
            }
            Arrays.sort(arr);
            int ret = 0;
            if (arr.length >= 3) {
                int l = arr[N - 1];
                int r = arr[N - 2];
                int constraint = Math.min(l, r);
                ret = Math.min(constraint - 1, N - 2);
            }
            else {
                ret = 0;
            }
            out.println(ret);
        }
        out.close();
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
