/*
 * @author derrick20
 * Class for rolling hashes
 */
import java.io.*;
import java.util.*;

public class RollingHash {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String str = sc.next();

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

    }
    static int MAX = (int) 1e5 + 1;
    static long base = 29;
    static long mod = (long) 1e9 + 7;
    static long inverse = modularInverse();
    static long[] invPower;
    static long[] power;

    //    static HashMap<Long, Long> inverseMap = new HashMap<>();

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
                // CRUCIAL ERROR: Need to use i - 1 for everything here!!
                prefix[i] = (prefix[i - 1] + ((str.charAt(i - 1) - 'A' + 1 + mod) * power[i - 1]) % mod) % mod;
            }
        }

        // From i to j, inclusive!!!!
        static long hashRange(int i, int j) {
            // j+1, because we take 0 through j (j+1 items)
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
