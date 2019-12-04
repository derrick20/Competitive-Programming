/**
 * @author derrick20
 * Key: Must use either strings or big integers. The best idea is to simulate it
 * PRECISELY. The other approach, which requires great detail and is hard to
 * do quickly is to think about when there are edge cases of missing the final
 * power of 4. Basically, we have to notice that, testing with small cases,
 * the number of powers of 4 is (len + 1) / 2. This makes sense somewhat,
 * since let's have 4^0 be base case, where len = 1. Each successive power of 4
 * will be 2^2 => len + 2. So, this means something odd, and the even number above
 * it both round to the same thing: (the pattern is (odd)/2 = (odd+1)/2. Thus, we need a +1.
 *
 * This makes a bit more sense if we think about usual rounding, where for odd number x,
 * x/2 and (x-1)/2 are the same, and an even number y: y/2 and (y+1)/2 are the same.
 * The goal pattern we sought is odd/2, (odd+1)/2 are the same.
 * So, we need to shift the odd to become an even number
 * to maintain that pattern. We don't need to translate the scheme luckily, as this works
 * out to be the correct direction. If we shifted so that the odd number goes DOWN to an
 * even number, we'd have to translate the whole thing up by 1.
 */
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class BowWow {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        for (int i = 4; i <= 40; i++) {
            System.out.print("_,");
        }
//        BigInteger S = new BigInteger(sc.next(), 2);
//        int ct = 0;
//        BigInteger curr = BigInteger.ONE;
//        while (curr.compareTo(S) < 0) {
//            ct++;
//            curr = curr.shiftLeft(2);
//        }
        String s = sc.next();
//        int x = Integer.parseInt(s, 2);
        // length  numPowers
        //    4    2
        //    3    2
        //    2    1
        //    1    1
        // (length + 1) / 2 is the formula
        int ct = (s.length() + 1) / 2;
        // If it is odd length, it ends on a power of 4. We need to check whether
        // the final train was exactly overlapping, meaning we couldn't reach that one
        // in time, since it's excluding
        if (s.length() % 2 == 1 && !s.substring(1).contains("1")) {
            ct--; // This means it was a power of 4, so the last one wasn't possible
            // 1 << (s.length() - 1)
        }
        out.println(ct);
        out.close();
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        FastScanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}