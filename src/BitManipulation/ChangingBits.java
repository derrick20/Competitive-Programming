import java.io.*;
import java.util.*;
import java.math.BigInteger;
/*l
 * HackerRank ChangingBits
 * OK i learned a lot in terms of bit manipulation, but the stupid big integer is too slow.
 * don't feel like redoing it...
 */

public class ChangingBits {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();
        BigInteger a = new BigInteger(sc.next(), 2); // Radix is the base, to which we need to understand the number
        BigInteger b = new BigInteger(sc.next(), 2); // and will convert to base 10 from
        while (q-->0) {
            String cmd = sc.next();
            if (cmd.substring(0, 3).equals("set")) {
                switch (cmd.charAt(4)) {
                    case 'a':
                        a = setBit(a, sc.nextInt(), sc.nextInt());
                        break;
                    case 'b':
                        b = setBit(b, sc.nextInt(), sc.nextInt());
                        break;
                }
            }
            else { // It's a get_c, then
                int idx = sc.nextInt();
                BigInteger c =  a.add(b);

                System.out.print(new BigInteger("1").shiftLeft(idx).and(c).shiftRight(idx));
            }
        }
    }

    public static BigInteger setBit(BigInteger x, int idx, int val) {
        BigInteger bit = new BigInteger("1").shiftLeft(idx);
        if (val == 1) {
            return x.or(bit);
        }
        else {
            return x.xor(bit); // Notice that XOR only toggles if it's a 1, and leaves it the same if 0
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
