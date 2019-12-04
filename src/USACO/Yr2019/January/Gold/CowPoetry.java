import java.io.*;
import java.util.*;
/*
 * USACO 2019 January Gold 1 CowPoetry
 * @derrick20
 */

public class CowPoetry {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
//        Scanner sc = new Scanner(new FileReader("poetry.in"));
//        PrintWriter out = new PrintWriter(new FileWriter("poetry.out"));
        N = sc.nextInt();
        int M = sc.nextInt();
        K = sc.nextInt();
        arr = new int[N];
        memo = new long[K + 1];

        HashMap<Integer, ArrayList<Integer>> classes = new HashMap<>();
        HashMap<String, Integer> freq = new HashMap();
        for (int i = 0; i < N; i++) {
            int w = sc.nextInt();
            int c = sc.nextInt();
            arr[i] = w;
            ArrayList<Integer> list;
            if (!classes.containsKey(c)) {
                list = new ArrayList<>();
            }
            else {
                list = classes.get(c);
            }
            list.add(w); // expand the list of words with that rhyme class
            classes.put(c, list);
        }
        // Now read in the lines of poetry desired
        for (int i = 0; i < M; i++) {
            String key = sc.next();
            if (!freq.containsKey(key)) {
                freq.put(key, 1);
            }
            else {
                int oldFreq = freq.get(key);
                freq.put(key, oldFreq + 1);
            }
        }
        memo[0] = 1L;
        // basically go at the most case where a weight is only 1
        for (int i = 0; i <= K; i++) {
            for (int j = 0; j < N; j++) {
                int wt = arr[j];
                // we need to make sure that this is still within bounds
                if (i + wt <= K) {
                    memo[i + wt] = (mod + memo[i + wt] + memo[i]) % mod;
                }
            }
        }
        // Look at each rhyme group, and figure out how many ways to fill that condition
        long total = 1L;
        for (String type : freq.keySet()){
            // Now try to apply each of the classes into that rhyme group
            long waysForClass = 0L;
            for (int group : classes.keySet()) {
                long waysToFit = 0L;
                // Try setting all possible endings of a rhyme scheme using each of the group members
                for (int wt : classes.get(group)) {
                    waysToFit = (mod + waysToFit + memo[K - wt]) % mod; // after using up the wt specified
                }
                waysForClass = (mod + waysForClass + fastExponentiate(waysToFit, freq.get(type))) % mod;
            }
            total = (mod + total * waysForClass) % mod;
        }
        out.println(total);
        out.close(); //*/
    }
    static long mod = (long) 1e9 + 7;
    // Raise x to the kth power using binary representation
    static long fastExponentiate(long x, int k) {
        if (k == 0) {
            return 1L;
        }
        else {
            long root = fastExponentiate(x, k / 2);
            if (k % 2 == 0) {
                return (root * root + mod) % mod;
            }
            else {
                return (root * root * x + mod) % mod;
            }
        } //*/
        /*long[] exp = new long[k + 1]; // stores x^i, i is the index
        exp[1] = x;
        for (int i = 2; (i << 1) <= k; i <<= 1) {
            exp[(1 << i)] = exp[(1 << (i-1))] * exp[(1 << (i-1))];
        }
        long ans = 1;
        for (int i = 1; (i << 1) <= k; i <<= 1) {
            if ((k & i) > 0) {
                // they share a bit
                ans *= exp[i];
                ans %= (long) 1e9 + 7;
            }
        }
        return ans; //*/
    }

    static int N, K;
    static int[] arr;
    static long[] memo;

    // returns the # of ways to form wt with all possible arrays
    /*static int solve(int wt) {
        if (wt == 0) {
            return 1;
        }
        if (memo[wt] != 0) {
            return memo[wt];
        }
        int val = 0;
        for (int i = 0; i < N && wt - arr[i] >= 0; i++) {
            val += solve(wt - arr[i]);
        }
        return memo[wt] = val;
    }
//*/
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

