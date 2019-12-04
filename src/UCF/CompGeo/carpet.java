/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class carpet {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        double a = sc.nextDouble();
        double b = sc.nextDouble();
        double c = sc.nextDouble();
        if (a + b >= c && a + c >= b && b + c >= a) {
            double cosAlpha = (b * b - c * c - a * a) / (-2 * a * c);
            double sinAlpha = Math.sqrt(1 - cosAlpha * cosAlpha);
            double squaredS = a * a + c * c - 2 * a * c * (0.5 * cosAlpha - (Math.sqrt(3) / 2) * sinAlpha);
            out.printf("%.3f\n", squaredS * Math.sqrt(3) / 4);
        }
        else {
            out.printf("%.3f\n", -1.000);
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
