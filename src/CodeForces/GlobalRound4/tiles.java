/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class tiles {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int w = sc.nextInt();
        int h = sc.nextInt();

        long row = (4 * fastExponentiate(2, w - 1)) % mod;
        long total = (row * fastExponentiate(2, h - 1)) % mod;
        out.println(total);
        out.close();
    }
    static long mod = 998244353;

    static long fastExponentiate(int x, int p) {
        if (p == 0) {
            return 1;
        }
        else if (p == 1) {
            return x;
        }
        else {
            long root = fastExponentiate(x, p / 2);
            long res = (root * root) % mod;
            if (p % 2 == 0) {
                return res;
            }
            else {
                return (x * res) % mod;
            }
        }
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
