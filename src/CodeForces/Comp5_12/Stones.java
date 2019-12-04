import java.io.*;
import java.util.*;
/*
 *
 * */

public class Stones {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int x = 0;
        int min = 0;
        for (char c : sc.next().toCharArray()) {
            if (c  == '-') {
                x--;
            }
            else {
                x++;
            }
            if (x < min) {
                min = x;
            }
        }
        System.out.println(x-min);
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