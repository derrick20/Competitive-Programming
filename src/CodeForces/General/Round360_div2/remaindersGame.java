import java.io.*;
import java.math.BigInteger;
import java.util.*;
/*
 * Codeforces Round 360 Div. 2 D
 */

public class remaindersGame {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k =  sc.nextInt();
        int bucket = 1;
        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            // ok, kinda unfair how C++ allowed the obvious solution I had an kept converting to BigInteger
            // This idea is to use a bucket to store the used up factors already
            //
            int gcd = gcd(k, x / (gcd(x, bucket)));
            k /= gcd;
            bucket *= gcd;
            if (k == 1) {
                System.out.println("Yes");
                System.exit(0);
            }
        }
        System.out.println("No");
    }

    static int gcd(int a, int b) {
        if (a == b) {
            return b;
        }
        if (a == 1 || b == 1) {
            return 1;
        }
        if (a < b) {
            // i love this swap trick
            a += b;
            b = a - b;
            a -= b;
        }
        while (b != 0) {
            a %= b;

            a += b;
            b = a - b;
            a -= b;
        }
        return a; // b will equal 0 now
    }
    /* crapp
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        //BigInteger N = BigInteger.ONE;
        BigInteger k = new BigInteger(sc.next());
        BigInteger lcm = BigInteger.ONE;
        //BigInteger lcm = BigInteger.ONE;
        HashSet<BigInteger> seen = new HashSet<>();
        for (int i = 0; i < n; i++) {
            BigInteger x = new BigInteger(sc.next());
            if (!seen.contains(x)) {
                BigInteger gcd = gcd(lcm, x);
                lcm = (lcm.multiply(x)).divide(gcd);
                if (lcm.mod(k).compareTo(BigInteger.ZERO) == 0) {
                    System.out.println("Yes");
                    System.exit(0);
                }
                seen.add(x);
                //            N = N.multiply(new BigInteger(x + ""));
                //            if (N.mod(k).equals(BigInteger.ZERO)) {
                //                System.out.println("Yes");
                //                System.exit(0);
                //            }
            }
        }
        System.out.println("No");
    }

    static BigInteger gcd(BigInteger a, BigInteger b) {
        if (a.compareTo(b) == 0) {
            return b;
        }
        if (a.compareTo(BigInteger.ONE) == 0 || b.compareTo(BigInteger.ONE) == 0) {
            return BigInteger.ONE;
        }
        if (a.compareTo(b) < 0) {
            BigInteger c = a;
            a = b;
            b = c;
//            // i love this swap trick
//            a += b;
//            b = a - b;
//            a -= b;
        }
        while (b.compareTo(BigInteger.ZERO) != 0) {
            a = a.mod(b);

            a = a.add(b);
            b = a.subtract(b);
            a = a.subtract(b);
        }
        return a; // b will equal 0 now
    }
     */

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
