/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class automatic {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        power = new long[MAX];
        invPower = new long[MAX];

        for (int i = 0; i < MAX; i++) {
            if (i == 0) {
                power[i] = 1;
                invPower[i] = 1;
            } else {
                power[i] = (power[i - 1] * base) % mod;
                invPower[i] = (invPower[i - 1] * inverse) % mod;
            }
        }
//        String str = "ABC";
//        rollingHash h = new rollingHash(str);
//        System.out.println(h.hashRange(0, 2));
//        System.out.println(h.hash(str));
        while (true) {
            String str = sc.next();
            if (str.equals("*")) {
                break;
            }

            int Q = sc.nextInt();
            rollingHash hash = new rollingHash(str);
            // O(Q log S), so N log N basically
            while (Q-->0) {
                int i = sc.nextInt();
                int j = sc.nextInt();
                int len = binarySearch(i, j, 0, str.length() - j - 1, hash);
                out.println(len);
            }
        }//*/
        out.close();
    }

    static int MAX = (int) 1e5 + 1;
    static long base = 307;
    static long mod = (long) 1e9 + 7;
    static long inverse = modularInverse();
    static long[] invPower;
    static long[] power;

    // binary search how long the matching prefix is
    static int binarySearch(int start1, int start2, int lo, int hi, rollingHash hash) {
//        int ans = 0;
        while (lo <= hi) {
            int mid = (hi - lo) / 2 + lo;
            long first = hash.hashRange(start1, start1 + mid);
            long second = hash.hashRange(start2, start2 + mid);
            String a = hash.str.substring(start1, start1 + mid + 1);
            String b = hash.str.substring(start2, start2 + mid + 1);

//            System.out.println(first+  " "+ second);
//            System.out.println(a + " " + b + "\n");
            if (first == second) {
//                ans = mid; // TODO CRUCIAL ERROR: I used ans = mid here, which was wrong!!!!#$!$!
                lo = mid + 1;
                // we can try going further, since we match here at the very least
            }
            else {
                hi = mid - 1;
                // we can rule out this position, so try a shorter prefix
            }
        }
        return lo;
    }

    static long modularInverse() {
        return fastExponentiate(base, mod - 2);
    }

    static long fastExponentiate(long n, long power) {
        if (power == 1) {
            return n;
        } else {
            long sqrt = fastExponentiate(n, power / 2) % mod;
            long ret = (sqrt * sqrt) % mod;
            if (power % 2 == 0) {
                return ret;
            } else {
                ret = (ret * n) % mod;
                return ret;
            }
        }
    }

    static class rollingHash {

        static long[] prefix;
        static String str;

        public rollingHash(String str) {
            this.str = str;
            prefix = new long[str.length() + 1];
            for (int i = 1; i <= str.length(); i++) {
                prefix[i] = (prefix[i - 1] + ((str.charAt(i - 1) - 'A' + 1 + mod) * power[i - 1]) % mod) % mod;
            }
        }
/*
ABABABcABABAbAbab
3
0 2
1 6
0 7
SheSellsSeashellsByTheSeaShore
4
8 22
1 20
8 25
0 1
*

*/
        // From i to j
        static long hashRange(int i, int j) {
            long hash = (prefix[j + 1] - prefix[i] + mod) % mod;
            return (hash * invPower[i]) % mod;
        }

        static long hash(String str) {
            long ans = 0;
            for (int i = 0; i < str.length(); i++) {
                long next = ((str.charAt(i) - 'A' + 1 + mod) * power[i]) % mod;
                ans = (ans + next) % mod;
            }
            return ans;
        }

        static long addLast(long rollingHash, char c, String curr) {
            return (rollingHash + (power[curr.length()] * (c - 'A' + 1)) % mod) % mod;
        }

        static long addFirst(long rollingHash, char c) {
            return ((rollingHash * power[1]) % mod + (c - 'A' + 1)) % mod;
        }

        static long pollLast(long rollingHash, String curr) {
            long last = (curr.charAt(0) - 'A' + 1) * power[curr.length() - 1] % mod;
            rollingHash = (rollingHash - last + mod) % mod;
            return rollingHash;
        }

        static long pollFirst(long rollingHash, String curr) {
            rollingHash = (rollingHash - (curr.charAt(0) - 'A' + 1) + mod) % mod;
            rollingHash = (rollingHash * modularInverse()) % mod;
            return rollingHash;
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

        boolean hasNext() throws IOException {
            return st.hasMoreTokens();
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
