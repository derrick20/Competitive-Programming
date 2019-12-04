/**
 * @author derrick20
 * Aw shoot ok, I'm still a bit confused on a few things. How do we get the
 * roots of unity for the mod version? In the normal version, it makes sense
 * since we just take the principle root of unity and keep multiple it
 * to move around the circle. From thhe submissions i'm seeing, it seems like we're
 * supposed to use the powers of 2 roots. It's weird, inverting. Other than
 * that, mine should be pretty close. The other weird thing is if i tried
 * running on the regular FFT, it goes negative and stagnant after a while.
 * I think there may be a trick with >>15 for doubles, somehow shifting
 * down then back up?? Weird indy256 guy...
 */
import java.io.*;
import java.util.*;

public class NumberTheoreticTransformTest {

    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        int K = sc.nextInt();
        long[] polynomial = new long[10];
        // Represents 1 + x + x^2 + ...
        // The degree in the exponent represents the sum achieved.
        // The coefficient represents the number of ways to achieve such a sum
        // The x initial will be 1 as a base case if that value is provided,
        // 0 otherwise. We raise to the N/2th power, representing all the sums
        // for the final ticket. Then, we need to sum the square of all those,
        // which represents the different orderings possible for the other half of
        // the ticket
        for (int i = 0; i < K; i++) {
            int val = sc.nextInt();
            polynomial[val] = 1;
        }
        long[] ans = fastPolynomialExponentiation(polynomial, N / 2);
//        System.out.println(ans.length + "  " + Arrays.toString(ans));
        long total = 0;
        for (long ways : ans) {
            total += (ways * ways) % mod;
            total %= mod;
        }
        System.out.println(total);
        out.close();
    }

    /**
     If inverse is false, perform regular FFT:
     => Given an array of the coefficient representation of polynomial A,
     return an array of the N y-values of the polynomial evaluated at each
     of the x-values we predetermined to be the Nth roots of unity! Since
     we will always hold that assumption, we don't need to track the x-values.

     If inverse is true, we will perform the inverse FFT:
     => From some set of N y-values, take them back to the original
     coefficients in a polynomial such that it would have produced those y-values.
     This makes sense in the context of matrix multiplication and inverting.

     In either case, we are applying the divide and conquer principle and splitting
     the array into even and odd-indexed positions. In doing so, the N x-values we
     must evaluate actually shrinks by a factor of 2 each dividing step, because
     the squaring the N roots of unity map onto the N/2 roots of unity.
     */
    static void FastFourierTransform(long[] A, boolean needInverse) {
        FastFourierTransformHelper(A, needInverse);
        int N = A.length;
        if (needInverse) {
            for (long c : A) {
                c *= (1.0 / N);
            }
        }
    }

    /**
     * Generate the powers of 2 roots of unity for the mod
     * Basically, our mod allows for a high degreeth root - that is,
     * we can go in increments of 1 / 2^23 around the unit circle, but in
     * modular world! There will be 23 available squarings/splitting for
     * our divide and conquer!
     */
    private static long[] findRoots(int k, boolean inverse) {
        long root = fastExponentiate(ROOT, fastExponentiate(2, twoPow - k));
        if (inverse) {
            root = modularInverse(root);
        }
        long[] roots = new long[k];
        if (k > 0) {
            roots[k - 1] = root;
        }
        for (int i = k - 1; i > 0; i--) {
            roots[i - 1] = (roots[i] * roots[i]) % mod;
        }
        return roots;
    }

    static void FastFourierTransformHelper(long[] A, boolean needInverse) {
        int N = A.length;
        if (N == 1) return;
        long[] roots = findRoots(twoPow, needInverse);

        // N is forced to be a power of 2 earlier!
        long[] even = new long[N / 2];
        long[] odd = new long[N / 2];
        for (int i = 0; i < N / 2; i++) {
            even[i] = A[2 * i];
            odd[i] = A[2 * i + 1];
        }

        FastFourierTransformHelper(even, needInverse);
        FastFourierTransformHelper(odd, needInverse);
//        System.out.println("Even: " + Arrays.toString(even));
//        System.out.println("Odd: " + Arrays.toString(odd));

        long currRoot = 1;

        // We only need to look at N / 2 parts, since the other half can be derived
        for (int i = 0; i < N / 2; i++) {
            long u = even[i];
            long v = currRoot * (odd[i]);
            // Butterfly pattern, resulting from the symmetry of roots
            // about the origin
            A[i] = u + v;
            A[i + N / 2] = u - v;
            currRoot = currRoot * roots[i];
        }
    }

    static long[] fastPolynomialExponentiation(long[] a, int power) {
        long[] ans = new long[a.length];
        long[] base = new long[a.length];
        for (int i = 0; i < base.length; i++) {
            base[i] = a[i];
            ans[i] = a[i];
        }
        power--; // We already give ans 1 power of a
        while (power > 0) {
            if ((power & 1) > 0) {
                ans = multiplyPolynomials(base, ans);
            }
            power >>= 1;
            base = multiplyPolynomials(base, base);
            System.out.println(Arrays.toString(base));
        }
        return ans;
    }

    static long[] multiplyPolynomials(long[] a, long[] b) {
        int N = 1;
        while (N < a.length + b.length) {
            N <<= 1; // this forces it to a power of 2 greater than/equal the total length
        }
        long[] A = new long[N];
        long[] B = new long[N];
        for (int i = 0; i < a.length; i++) {
            A[i] = a[i];
        }
        for (int i = 0; i < b.length; i++) {
            B[i] = b[i];
        }
//        System.out.println(Arrays.toString(A));
//        System.out.println(Arrays.toString(B));
        FastFourierTransform(A, false);
        FastFourierTransform(B, false);
        long[] C = new long[N];
        for (int i = 0; i < N; i++) {
            C[i] = (A[i] * B[i]) % mod;
        }
        FastFourierTransform(C, true);
//        System.out.println(Arrays.toString(C));
        return C;
    }

    // 2^23*7*17+1
    static final long mod = 998244353;

    //highest power of 2 that is a factor of MOD-1
    static final int twoPow = 23;

    // any number such that ROOT^(2^twoPow)=1 & ROOT^(2^(twoPow-1))!=1
    // 2^(7*17*2^23) === 1, but actually isn't a primitive root (the second
    // condition above fails. Turns out 3 works though!
    // So, below is 3^(7*17) mod 998244353.
    static final int ROOT = 961777435;

    static long fastExponentiate(long x, long power) {
        long ans = 1;
        while (power > 0) {
            if ((x & 1) > 0) {
                ans = (ans * x) % mod;
            }
            x = (x * x) % mod;
            power >>= 1;
        }
        return ans;
    }

    static long modularInverse(long x) {
        return fastExponentiate(x, mod - 2);
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