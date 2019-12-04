/*
 * @author derrick20
 * @problem USACO 2015 February Silver 1
 * Uses ROLLING HASH and delete/add front/back
 * Use double hashing
 */
import java.io.*;
import java.util.*;
/*
I originally re-derived the Rabin-Karp Algorithm LOL
Basically my idea was to use prefix sums to see if we could have found
the match, but that won't work upon deletion, since we'll get some weird
interspersing that breaks that
 */

public class censorsilverFAIL {

    static long mod = (long) 1e9 + 7;
    static long base = 29;
    static long[] power;
    static long[] invPower;
    static ArrayList<Long> prefix;
    static long inverse = modularInverse();

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
//        Scanner sc = new Scanner(new FileReader("censor.in"));//
//        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));
        String str = sc.next();
        String sub = sc.next();

        power = new long[str.length()];
        invPower = new long[str.length()];

        for (int i = 0; i < str.length(); i++) {
            if (i == 0) {
                power[i] = 1;
                invPower[i] = 1;
            } else {
                power[i] = (power[i - 1] * base) % mod;
                invPower[i] = (invPower[i - 1] * inverse) % mod;
            }
//            System.out.println(power[i] + " " + invPower[i]);
//            System.out.println(power[i] * invPower[i] % mod);
        }

        long subHash = hash(sub);
//        System.out.println(sub);
        long rollingHash = 0;
        String curr = "";
//        System.out.println(subHash);
//        System.out.println(hash(str.substring(0, sub.length())));
        /*System.out.println("moo " + addLast(hash("mo"), 'o', "mo"));
        System.out.println("moo " + hash("moo"));
        System.out.println(modularInverse() * base % mod);
        //String x = "";
        rollingHash = addLast(rollingHash, 'm', x);
        x += "m";
        System.out.println(x + " " + rollingHash);

        rollingHash = addLast(rollingHash, 'm', x);
        x += "m";
        System.out.println(x + " " + rollingHash);

        rollingHash = addLast(rollingHash, 'm', x);
        x += "m";
        System.out.println(x + " " + rollingHash);

        rollingHash = pollFirst(rollingHash, x);
        x = x.substring(0, x.length() - 1);
        System.out.println(x + " " + rollingHash);

        rollingHash = pollFirst(rollingHash, x);
        x = x.substring(0, x.length() - 1);
        System.out.println(x + " " + rollingHash);

        String x = "";
        rollingHash = addLast(rollingHash, 'm', x);
        x += "m";
        System.out.println(x + " " + rollingHash);

        rollingHash = addLast(rollingHash, 'o', x);
        x += "o";
        System.out.println(x + " " + rollingHash);

        rollingHash = addLast(rollingHash, 'o', x);
        x += "o";
        System.out.println(x + " " + rollingHash);

        rollingHash = pollFirst(rollingHash, x);
        x = x.substring(1);
        System.out.println(x + " " + rollingHash);

        rollingHash = pollFirst(rollingHash, x);
        x = x.substring(1);
        System.out.println(x + " " + rollingHash);

        rollingHash = pollFirst(rollingHash, x);
        x = x.substring(1);
        System.out.println(x + " " + rollingHash); // */

        int[] jumpTo = new int[str.length()];
        int left = 0;
        int right = 0;
        /*for (int i = 0; i < str.length(); i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < str.length(); i++) {
            System.out.print(str.charAt(i) + " ");
        }
        System.out.println();*/
        StringBuilder sb = new StringBuilder(str);
        prefix = new ArrayList<>();
        // make it so we can override parts
        for (int i = 0; i < str.length() + 1; i++) {
            prefix.add(0L);
        }

        while (right < sb.length()) {
            char c = sb.charAt(right);
            long nextPrefix = (prefix.get(right) + ((c - 'a' + 1) * power[right]) % mod) % mod;
            prefix.set(right + 1, nextPrefix);
//            System.out.println("Prefix " + prefix.get(right + 1));
//            System.out.println("Hash " + hash(sb.substring(0, right + 1)));
//            if (jumpTo[right] != 0) {
////                right = jumpTo[right];
////                continue;
////            }
////            if (jumpTo[left] != 0) {
////                left = jumpTo[left];
////                continue;
////            }
//            System.out.println(curr);
            rollingHash = addLast(rollingHash, c, curr);
            curr += c;
//            res += c;
            right++;
            if (curr.length() < sub.length()) {
                continue;
            }
            else {
                // if the back end matches what we are looking for, then remove it
                // and move on
//                System.out.println(left + " "  + right + " " + res + " " + hash(curr) + " " + rollingHash);
                if (subHash == rollingHash) {
                    curr = "";
                    // We have to go back, but X~twice~X because we deleted, and also in case this deletion
                    // enabled the creation of a new substring!!!!
//                    res = res.substring(0, res.length() - 2 * sub.length());
//                    System.out.println(res);
                    // subtract that end part now
                    // use prefix sums to not have to recompute hashes
                    int newLeft = Math.max(0, left - sub.length());
                    rollingHash = (prefix.get(left) - prefix.get(newLeft) + mod) % mod;
                    rollingHash = (rollingHash * invPower[newLeft]) % mod;
//                    jumpTo[left] = right;
                    sb.delete(left, right);
                    left = newLeft;
                    right = left + sub.length();
                    curr = sb.substring(left, right);
                    // Automatically jump up back to the right size
                }
                if (curr.length() >= sub.length()) {
                    rollingHash = pollFirst(rollingHash, curr);
                    curr = curr.substring(1);
                    left++;
                }
            }
        }
//        StringBuilder res = new StringBuilder();
        /*int i = 0;
        while (i < str.length()) {
            if (jumpTo[i] != 0) {
                i = jumpTo[i];
            }
            else {
                out.print(str.charAt(i));
                i++;
            }
        }*/
//        out.println();
        out.println(sb);
        out.close();
    }

    static long hash(String str) {
        long ans = 0;
        for (int i = 0; i < str.length(); i++) {
            long next = ((str.charAt(i) - 'a' + 1) * power[i]) % mod;
            ans = (ans + next) % mod;
        }
        return ans;
    }

    static long addLast(long rollingHash, char c, String curr) {
        return (rollingHash + (power[curr.length()] * (c - 'a' + 1)) % mod) % mod;
    }

    static long addFirst(long rollingHash, char c) {
        return ((rollingHash * power[1]) % mod + (c - 'a' + 1)) % mod;
    }

    static long pollLast(long rollingHash, String curr) {
        long last = (curr.charAt(0) - 'a' + 1) * power[curr.length() - 1] % mod;
        rollingHash = (rollingHash - last + mod) % mod;
        return rollingHash;
    }

    static long pollFirst(long rollingHash, String curr) {
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
