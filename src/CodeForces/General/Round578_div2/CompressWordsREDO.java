/*
 * @author derrick20
 * Class for rolling hashes
 */
import java.io.*;
import java.util.*;

public class CompressWordsREDO {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        String[] words = new String[N];
        int len = 0;
        for (int i = 0; i < words.length; i++) {
            words[i] = sc.next();
            len += words[i].length();
        }
        len++; // go more just in case

        // Precompute the powers of the base for speed
        power = new long[len];
        power2 = new long[len];

        for (int i = 0; i < len; i++) {
            if (i == 0) {
                power[i] = 1;
                power2[i] = 1;
            } else {
                power[i] = (power[i - 1] * base) % mod;
                power2[i] = (power2[i - 1] * base) % mod2;
            }
        }
        StringBuilder res = new StringBuilder();
        long[] rollingHash = new long[len + 1]; // 1-INDEXED!
        long[] rollingHash2 = new long[len + 1];
        int pos = 1;
        int shift = 0;
        for (int i = 0; i < words.length - 1; i++) {
            String word = words[i];

            // however much we matched, we shift by. Now we'll be not matching again!
            for (int start = shift; start < word.length(); start++) {
                char c = word.charAt(start);
                rollingHash[pos] = (c + rollingHash[pos - 1] * base) % mod;
                rollingHash2[pos] = (c + rollingHash2[pos - 1] * base) % mod2;
                res.append(c);
                pos++;
            }


            // Keep walking forward and checking if the prefix and suffixes match
            long fullHash = rollingHash[pos - 1];
            long fullHash2 = rollingHash2[pos - 1];
            char[] nextWord = words[i + 1].toCharArray();
            // We attempt the greedy biggest match, then go down.
            // The moment we achieve a match, we're done! Hashing is
            // necessary, because we don't have a way to use answers to earlier checks
            // so we need a fast way to compare strings!
            long[] prefix = new long[nextWord.length];
            long[] prefix2 = new long[nextWord.length];
            for (int j = 0; j < nextWord.length; j++) {
                if (j == 0) {
                    prefix[j] = nextWord[j];
                    prefix2[j] = nextWord[j];
                }
                else {
                    prefix[j] = (prefix[j - 1] * base + nextWord[j]) % mod;
                    prefix2[j] = (prefix2[j - 1] * base + nextWord[j]) % mod2;
                }
            }
            // Go until we had 1 match, (once shift == 0, this is our last chance of getting a single char match)
            shift = Math.min(pos - 1, nextWord.length);
            for (; shift > 0; shift--) {
                long prefixHash = prefix[shift - 1];
                long prefixHash2 = prefix2[shift - 1];

                long frontHash = (rollingHash[pos - 1 - shift] * power[shift]) % mod;
                long frontHash2 = (rollingHash2[pos - 1 - shift] * power2[shift]) % mod2;
                long suffixHash = (fullHash - frontHash + mod) % mod;
                long suffixHash2 = (fullHash2 - frontHash2 + mod2) % mod2;
                if (prefixHash == suffixHash && prefixHash2 == suffixHash2) {
                    // Once we get our first match, break!
                    // We want to skip over that last matched position, so ++ once
                    break;
                }
            }
        }
        // After finished, f the hash value
        for (int start = shift; start < words[N - 1].length(); start++) {
            char c = words[N - 1].charAt(start);
            res.append(c);
            pos++;
        }
        out.println(res);
        out.close();
    }
    // Use something above the max possible. Be lazy, not many of the residues will
    // even be used actually!
    static long base = 137;
    static long mod = (long) 1e9 + 7;
    static long mod2 = (long) 1e9 + 9;
    static long inverse = modularInverse();
    static long[] invPower;
    static long[] power, power2;

    //    static HashMap<Long, Long> inverseMap = new HashMap<>();

    static long modularInverse() {
        return fastExponentiate(base, mod - 2);
    }

    static long fastExponentiate(long n, long power) {
        if (power == 0) {
            return 1;
        }
        else if (power == 1) {
            return n;
        }
        else {
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
