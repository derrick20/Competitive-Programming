/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class enhance {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int x = sc.nextInt();
        String[] hp = {"D", "A", "C", "B"};
        int mod = x % 4;
        int ret = 0;
        if (mod == 0) {
            x += 1;
            ret = 1;
        }
        else if (mod == 1) {
            x += 0;
            ret = 0;
        }
        else if (mod == 2) {
            x += 1;
            ret = 1;
        }
        else if (mod == 3) {
            x += 2;
            ret = 2;
        }
        out.println(ret + " " + hp[x % 4]);
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
