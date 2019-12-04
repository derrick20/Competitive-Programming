import java.io.*;
import java.util.*;
/*
 *
 * */

public class Expansion {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < N; i++)
            arr.add(sc.nextInt());

        int min = (int) 1e9;
        for (int i = 0; i < N/2; i++) {
            int a = Math.min(arr.get(i), arr.get(N-1));
            min = Math.min(min, a / (N - 1 - i));
        }
        for (int i = N/2; i < N; i++) {
            int a = Math.min(arr.get(i), arr.get(0));
            min = Math.min(min, a / i);
        }
        System.out.println(min);
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