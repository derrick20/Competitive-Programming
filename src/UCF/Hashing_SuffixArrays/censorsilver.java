/*
 * @author derrick20
 * @problem USACO 2015 February Silver 1
 * Uses ROLLING HASH and delete/add front/back
 * Use double hashing
 */
import java.io.*;
import java.util.*;

public class censorsilver {

    static long mod = (long) 1e9 + 7;
    static long base = 29;
    static int max = (int) 1e6;
    static long[] power;
    static long[] prefixHash;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
//        Scanner sc = new Scanner(new FileReader("censor.in"));//
//        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));
        String s = sc.next();
        String p = sc.next();
        char[] str = s.toCharArray();
        char[] sub = p.toCharArray();

        power = new long[str.length];
        for (int i = 0; i < str.length; i++) {
            if (i == 0) {
                power[i] = 1;
            } else {
                power[i] = (power[i - 1] * base) % mod;
            }
        }
        long subHash = 0;
        for (int i = 0; i < str.length; i++) {
            str[i] -= ('a' - 1);
        }
        for (int i = 0; i < sub.length; i++) {
            sub[i] -= ('a' - 1);
            subHash = (subHash + sub[i] * power[i]) % mod;
        }
        System.out.println(sub);
        long rollingHash = 0;
        StringBuilder curr = new StringBuilder();
        System.out.println("wh " + addLast(hash("w"), 'h', new StringBuilder("w")));
        System.out.println("wh " + hash("wh"));

        int[] jumpTo = new int[str.length];
        int left = 0;
        int right = 0;
        for (int i = 0; i < str.length; i++) {
            if (jumpTo[i] != 0) {
                i = jumpTo[i];
                left = i;
                right = i;
            }
            curr.append(str[i]);
            System.out.println(curr);
            rollingHash = addLast(rollingHash, str[i], curr);
            right++;
            System.out.println(rollingHash);
            if (curr.length() < sub.length) {
                continue;
            }
            else {
                // if the back end matches what we are looking for, then remove it
                // and move on
//                System.out.println(rollingHash);
                if (subHash == rollingHash) {
                    curr = new StringBuilder(curr.substring(0, left));
                    // subtract that end part now
                    rollingHash = 0;
                    jumpTo[left] = right + 1;
                    left -= sub.length;
                    right = left;
                }
                curr = new StringBuilder(curr.substring(1));
                rollingHash = pollFirst(rollingHash, curr);
                left++;
            }
        }
        out.println(curr.toString());

        out.close();
    }

    static long hash(String str) {
        long ans = 0;
        for (int i = 0; i < str.length(); i++) {
            ans += (str.charAt(i) - 'a' + 1) * power[i];
        }
        return ans;
    }

    static long addLast(long rollingHash, char c, StringBuilder curr) {
        return (rollingHash + (power[curr.length()] * c) % mod) % mod;
    }

    static long addFirst(long rollingHash, char c) {
        return (rollingHash * power[1] + c) % mod;
    }

    static long pollLast(long rollingHash, StringBuilder curr) {
        rollingHash = (rollingHash - curr.charAt(0) * power[curr.length() - 1] + mod) % mod;
        return rollingHash;
    }

    static long pollFirst(long rollingHash, StringBuilder curr) {
        rollingHash = (rollingHash - (curr.charAt(0) - 'a' + 1) + mod) % mod;
        rollingHash = (rollingHash * modularInverse()) % mod;
        return rollingHash;
    }

//    static HashMap<Long, Long> inverseMap = new HashMap<>();

    static long modularInverse() {
//        if (inverseMap.containsKey(degree)) {
//            return inverseMap.get(degree);
//        }
//        else {
        long inverse = fastExponentiate(base, mod - 2);
//            inverse = fastExponentiate(inverse, degree);
//            inverseMap.put(degree, inverse);
        return inverse;
//        }
    }

    static long fastExponentiate(long n, long power) {
        if (power == 1) {
            return n;
        }
        else {
            long sqrt = fastExponentiate(n, power / 2) % mod;
            long ret = (sqrt * sqrt) % mod;
            if (power % 2 == 0) {
                return ret;
            }
            else {
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
