/**
 * @author derrick20
 * Still isn't working too little precision DAMN
 */
import java.io.*;
import java.util.*;

public class LuckyTickets {
    static final double PI = Math.PI;
    static final int mod = 998244353;

    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

//        int N = sc.nextInt();
//        int[] a = new int[N];
//        int[] b = new int[N];
//        for (int i = 0; i < N; i++) {
//            a[i] = sc.nextInt();
//        }
//        for (int i = 0; i < N; i++) {
//            b[i] = sc.nextInt();
//        }
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
    static void FastFourierTransform(Complex[] A, boolean inverse) {
        FastFourierTransformHelper(A, inverse);
        int N = A.length;
        if (inverse) {
            for (Complex c : A) {
                c.scale(1.0 / N);
            }
        }
    }

    static void FastFourierTransformHelper(Complex[] A, boolean inverse) {
        int N = A.length;
        if (N == 1) return;

        // N is forced to be a power of 2 earlier!
        Complex[] even = new Complex[N / 2];
        Complex[] odd = new Complex[N / 2];
        for (int i = 0; i < N / 2; i++) {
            even[i] = A[2 * i];
            odd[i] = A[2 * i + 1];
        }

        FastFourierTransformHelper(even, inverse);
        FastFourierTransformHelper(odd, inverse);
//        System.out.println("Even: " + Arrays.toString(even));
//        System.out.println("Odd: " + Arrays.toString(odd));

        // sign depends on inverse or not
        double theta = 2 * PI / N * (inverse ? -1 : 1);
        Complex principal = new Complex(Math.cos(theta), Math.sin(theta));
        Complex currRoot = new Complex(1);

        // We only need to look at N / 2 parts, since the other half can be derived
        for (int i = 0; i < N / 2; i++) {
            Complex u = even[i];
            Complex v = currRoot.multiply(odd[i]);
            // Butterfly pattern, resulting from the symmetry of roots
            // about the origin
            A[i] = u.add(v);
            A[i + N / 2] = u.subtract(v);
            currRoot = currRoot.multiply(principal);
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
        Complex[] A = new Complex[N];
        Complex[] B = new Complex[N];
        for (int i = 0; i < N; i++) {
            if (i < a.length) {
                A[i] = new Complex(a[i]);
            }
            else {
                A[i] = new Complex(0);
            }
        }
        for (int i = 0; i < N; i++) {
            if (i < b.length) {
                B[i] = new Complex(b[i]);
            }
            else {
                B[i] = new Complex(0);
            }
        }
//        System.out.println(Arrays.toString(A));
//        System.out.println(Arrays.toString(B));
        FastFourierTransform(A, false);
        FastFourierTransform(B, false);
        Complex[] C = new Complex[N];
        for (int i = 0; i < N; i++) {
            C[i] = A[i].multiply(B[i]);
        }
        FastFourierTransform(C, true);
//        System.out.println(Arrays.toString(C));
        long[] result = new long[N];
        for (int i = 0; i < N; i++) {
            result[i] = (long) (C[i].re + 0.5) % mod;
        }
        return result;
    }

    static long fastExponentiate(long x, int k) {
        long ans = 1;
        while (k > 0) {
            if ((k & 1) == 1) {
                // Multiply it by the x ^ (2 ^ current number of shifted bits)
                ans = (x * ans) % mod;
            }
            x = (x * x) % mod; // keep squaring x
            k >>= 1;
        }
        return ans;
    }

    static class Complex {
        double re, im;

        public Complex(double r) {
            re = r;
        }

        public Complex(double r, double i) {
            re = r; im = i;
        }

        public String toString() {
            return "(" + String.format("%.2f", re) + ", " + String.format("%.2f", im) + " i)";
        }

        public void scale(double factor) {
            re *= factor; im *= factor;
        }

        public Complex add(Complex c) {
            return new Complex(re + c.re, im + c.im);
        }

        public Complex subtract(Complex c) {
            return new Complex(re - c.re, im - c.im);
        }

        public Complex multiply(Complex c) {
            return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
        }

    }

    static class FastScanner {
        public int BS = 1<<16;
        public char NC = (char)0;
        byte[] buf = new byte[BS];
        int bId = 0, size = 0;
        char c = NC;
        double num = 1;
        BufferedInputStream in;

        public FastScanner() {
            in = new BufferedInputStream(System.in, BS);
        }

        public FastScanner(String s) {
            try {
                in = new BufferedInputStream(new FileInputStream(new File(s)), BS);
            }
            catch (Exception e) {
                in = new BufferedInputStream(System.in, BS);
            }
        }

        public char nextChar(){
            while(bId==size) {
                try {
                    size = in.read(buf);
                }catch(Exception e) {
                    return NC;
                }
                if(size==-1)return NC;
                bId=0;
            }
            return (char)buf[bId++];
        }

        public int nextInt() {
            return (int)nextLong();
        }

        public long nextLong() {
            num=1;
            boolean neg = false;
            if(c==NC)c=nextChar();
            for(;(c<'0' || c>'9'); c = nextChar()) {
                if(c=='-')neg=true;
            }
            long res = 0;
            for(; c>='0' && c <='9'; c=nextChar()) {
                res = (res<<3)+(res<<1)+c-'0';
                num*=10;
            }
            return neg?-res:res;
        }

        public double nextDouble() {
            double cur = nextLong();
            return c!='.' ? cur:cur+nextLong()/num;
        }

        public String next() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=nextChar();
            while(c>32) {
                res.append(c);
                c=nextChar();
            }
            return res.toString();
        }

        public String nextLine() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=nextChar();
            while(c!='\n') {
                res.append(c);
                c=nextChar();
            }
            return res.toString();
        }

        public boolean hasNext() {
            if(c>32)return true;
            while(true) {
                c=nextChar();
                if(c==NC)return false;
                else if(c>32)return true;
            }
        }
    }
}