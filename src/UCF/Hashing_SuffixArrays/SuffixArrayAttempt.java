/*
 * @author derrick20
 */

import java.io.*;
import java.util.*;

public class SuffixArrayAttempt {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        while (sc.hasNext()) {
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
            int Q = sc.nextInt();
            char[] charr = str.toCharArray();
            long[] prefix = new long[charr.length];
        }
    }
    // The array will store suffixes of certain size, and place them
    // based on their positions within the string
//        Suffix[][] arr = new Suffix[size][str.length()];
//        for (int blockSize = 1; blockSize <maxBlock; blockSize++)
//            out.print(1);
//        out.close();

    static int MAX = (int) 1e5 + 1;
    static long base = 29;
    static long mod = (long) 1e9 + 7;
    static long inverse = modularInverse();
    static long[] invPower;
    static long[] power;

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

    static void coordinateCompress(int[] arr) {
        HashMap<Integer, Integer> valToIndex = new HashMap<>();
        TreeSet<Integer> copy = new TreeSet<>();
        for (int i = 0; i < arr.length; i++) {
            copy.add(arr[i]);
        }
        // Basically, have a map of the value to its rank within the whole array
        int index = 1;
        for (int value : copy) {
            valToIndex.put(value, index++);
            // but we want to 1-index it
        }
        // Then, use that map to convert the value of
        for (int i = 0; i < arr.length; i++) {
            arr[i] = valToIndex.get(arr[i]);
        }
    }

    //returns an array of integers which represent
    //the suffixes of the given string in their sorted position.
    // O(n * log(n))
    public int[] suffixArray(char[] S) {
        int n = S.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++)
            order[i] = n - 1 - i;

        Arrays.sort(order, (a, b) -> Character.compare(S[a], S[b]));

        int[] sa = new int[n];
        int[] classes = new int[n];
        for (int i = 0; i < n; i++) {
            sa[i] = order[i];
            classes[i] = S[i];
        }

        for (int len = 1; len < n; len *= 2) {
            int[] c = classes.clone();
            for (int i = 0; i < n; i++) {
                classes[sa[i]] = i > 0 && c[sa[i - 1]] == c[sa[i]] && sa[i - 1] + len < n && c[sa[i - 1] + len / 2] == c[sa[i] + len / 2] ? classes[sa[i - 1]] : i;
            }

            int[] cnt = new int[n];
            for (int i = 0; i < n; i++)
                cnt[i] = i;
            int[] s = sa.clone();
            for (int i = 0; i < n; i++) {
                int s1 = s[i] - len;
                if (s1 >= 0)
                    sa[cnt[classes[s1]]++] = s1;
            }

        }
        return sa;
    }

    //returns the Longest Common Prefix (lcp) array.
    //lcp[i] = the number of characters shared between the
    //ith sorted suffix and (i+1)th sorted suffix.
    int[] lcp(int[] sa, char[] s) {
        int n = sa.length;
        int[] rank = new int[n];
        for (int i = 0; i < n; i++)
            rank[sa[i]] = i;
        int[] lcp = new int[n - 1];
        for (int i = 0, h = 0; i < n; i++) {
            if (rank[i] < n - 1) {
                for (int j = sa[rank[i] + 1]; Math.max(i, j) + h < s.length && s[i + h] == s[j + h]; ++h)
                    ;
                lcp[rank[i]] = h;
                if (h > 0)
                    --h;
            }
        }
        return lcp;
    }

    //Make an RMQ object and give it an array.
    //Then call the query method with your Left and Right values
    //and it'll return the minimum on the range.
    class RMQ {
        int[] vs;
        int[][] lift;

        public RMQ(int[] vs) {
            this.vs = vs;
            int n = vs.length;
            int maxlog = Integer.numberOfTrailingZeros(Integer.highestOneBit(n)) + 2;
            lift = new int[maxlog][n];
            for (int i = 0; i < n; i++)
                lift[0][i] = vs[i];
            int lastRange = 1;
            for (int lg = 1; lg < maxlog; lg++) {
                for (int i = 0; i < n; i++) {
                    lift[lg][i] = Math.min(lift[lg - 1][i], lift[lg - 1][Math.min(i + lastRange, n - 1)]);
                }
                lastRange *= 2;
            }
        }

        public int query(int low, int hi) {
            int range = hi - low + 1;
            int exp = Integer.highestOneBit(range);
            int lg = Integer.numberOfTrailingZeros(exp);
            return Math.min(lift[lg][low], lift[lg][hi - exp + 1]);
        }
    }

    /*static void countingSort(int[] rank, int blockSize) {
        int N = rank.length; // str, rank and suffixArr all have length N
        int maxSize = Math.max(N, 300); // since 255 for ascii
        int[] buckets = new int[maxSize];
        // Each bucket will points to the index value i should go
        for (int i = 0; i < N; i++) {
            // that bucket now has one more of that rank value
            if (i + blockSize < N) {
                buckets[rank[i + blockSize]]++;
            } else {
                buckets[0]++;
            }
        }
        for (int i = 1; i < buckets.length; i++) {
            // make it so that each value is pushed behind all of the others
            // that came before it (in terms of rank)
            buckets[i] += buckets[i - 1];
        }
        int[] sorted = new int[N];
        for (int i = 0; i < N; i++) {
            // go through each value in the original array, and place it in
            // the correct bucket. Also, update the index the bucket points too
            // since future objects of the same rank must be placed after it
            int value = rank[i];
            int pos = --buckets[value]; // post-decrement after placing
            // The intuition is that, we have stored in the buckets the total
            // count of all of this one value. So, we know where the last one
            // should go, so shift it down. Also, it was one-indexed, so do it
            // all in one go (why? since the first item was given a 1, as in
            // it would go to the first spot, or 0.
            sorted[pos] = value;
        }
    }

    static void constructSuffixArray(String str) {
        int size = 1;
        while (size <= str.length()) {
            size <<= 1;
        }
        StringBuilder pad = new StringBuilder();
        while (pad.length() + size < str.length()) {
            pad.append("$"); // sentinel
        }
        char[] chars = (str + pad.toString()).toCharArray();
        int N = str.length();
        int[] rank = new int[N]; // Store the rank of each suffix

        int[] suffixArr = new int[N]; // This stores the positions of the
        // suffixes, in sorted order. Notice that the position stored
        // will go all the way to the end

        for (int i = 0; i < N; i++) {
            rank[i] = chars[i]; // the current rank for size 1 groupings
            suffixArr[i] = i; // Just have it in whatever order we start with
        }
        coordinateCompress(rank);
        for (int blockSize = 1; blockSize < N; blockSize <<= 1) {
            countingSort(rank, blockSize);

            for (int i = 0; i < N; i++) {

            }
            int[] tempRank = rank.clone();
            // go through and compare with the suffix adjacent (on the left)
            for (int i = 1; i < N; i++) {
                if (i + blockSize < N) {
                    // it turns out that the sorted order may not have been right
                    // However, it should be mostly be correct (since we used half
                    // the information). If it isn't based on the new doubled info,
                    // then we most increment its rank. Otherwise, keep it the same
                    // (The order we had was fine!)
                    int leftFirstHalf = rank[suffixArr[i - 1]];
                    int rightFirstHalf = rank[suffixArr[i]];
                    int leftSecondHalf = rank[suffixArr[i - 1] + blockSize];
                    int rightSecondHalf = rank[suffixArr[i] + blockSize];
                    if (!(leftFirstHalf == rightFirstHalf && leftSecondHalf == rightSecondHalf)) {
                        tempRank[suffixArr[i]]++;
                    }
                }
            }
        }
    } //*/

    /*static class Suffix implements Comparable<Suffix> {
        int id;
        int length;
        String str;

        public Suffix(int ii, int ll, String ss) {
            id = ii;
            length = ll;
            str = ss;
        }

        public int binarySearch(String s1, String s2) {
            int hi = Math.min(s1.length(), s2.length());
            int lo = 0;
            int ans = 0;
            while (lo < hi) {
                int mid = (hi - lo) / 2 + lo;
                if (s1.charAt(mid) == s2.charAt(mid)) {
                    // We know for certain that this works, but the next smaller
                    // index might not, so we can't rule it out yet.
                    ans = mid;
                    hi = mid;
                } else {
                    // We know for certain this and everything below will fail,
                    // so +1
                    lo = mid + 1;
                }
            }
            return ans;
        }

        public int compareTo(Suffix s2) {
            return 0;
        }*/

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
