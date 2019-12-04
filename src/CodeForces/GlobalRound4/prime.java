/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class prime {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int[] arr = new int[N];
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
            if (i > 0) {
                sum += arr[i];
            }
        }

        int a = arr[0];
        ArrayList<Integer> res = new ArrayList<>();
        res.add(1);
        int total = 0;
        for (int i = 1; i < arr.length; i++) {
            if (a >= 2 * arr[i]) {
                res.add(i + 1);
                total += arr[i];
            }
        }
        total += a;
        sum += a;
        if (total * 2 > sum) {
            out.println(res.size());
            for (int i : res) {
                out.print(i + " ");
            }
            out.println();
        }
        else {
            out.println(0);
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
