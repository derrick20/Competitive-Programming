/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class gym {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int C = sc.nextInt();

        for (int i = 0; i < C; i++) {
            int monthly = sc.nextInt();
            int annual =  sc.nextInt();
            int months = sc.nextInt();
            int allAnnual = (int) Math.ceil((double) months / 12) * annual;
            int allMonth = months * monthly;
            out.println(Math.min(allAnnual, allMonth));
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