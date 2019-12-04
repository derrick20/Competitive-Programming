/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class CowPoetryRedo {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean testing) throws Exception {
        String prefix = testing ? "/Users/derrick/IntelliJProjects/src/USACO/" : "";
        sc = new FastScanner(prefix + problem_name + ".in");
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner();
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
//        setupIO();
        setupIO("poetry", false);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        int[] words = new int[N];
        HashMap<Integer, ArrayList<Integer>> rhymeGroups = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int syllables = sc.nextInt();
            words[i] = syllables; // Just store the syllables rougly for easy access
            int rhymeClass = sc.nextInt();
            if (rhymeGroups.containsKey(rhymeClass)) {
                rhymeGroups.get(rhymeClass).add(syllables);
            }
            else {
                ArrayList<Integer> group = new ArrayList<>();
                group.add(syllables);
                rhymeGroups.put(rhymeClass, group);
            }
        }

        HashMap<String, Integer> poemSizes = new HashMap<>();
        for (int i = 0; i < M; i++) {
            String c = sc.next();
            poemSizes.merge(c, 1, Integer::sum);
        }

        long[] dp = new long[K + 1];
        // dp[k] stores the number of ways to reach syllable length of k
        // using ANY number of ANY of the words at our disposal
        dp[0] = 1;
        for (int curr = 1; curr <= K; curr++) {
            for (int word : words) {
                if (curr >= word) {
                    // If the current state is reachable from some previous state
                    // by adding this word, then update this state
                    dp[curr] += dp[curr - word];
                    dp[curr] %= mod;
                }
            }
        }

        HashMap<Integer, Long> lineWays = new HashMap<>();
        // Store for a given rhymeClass, how many ways we can make a line ending
        // with that rhymeClass. Rely on the dp which tells how many ways to
        // make the rest of the line (to get K - len(word) syllables, then
        // add the current word
        for (int rhymeGroup : rhymeGroups.keySet()) {
            long ways = 0;
            for (int wordLen : rhymeGroups.get(rhymeGroup)) {
                ways += dp[K - wordLen];
                ways %= mod;
            }
            lineWays.put(rhymeGroup, ways);
        }

        // Now, for all the groups that the poem has,
        // we sum the number of ways to make groupSize number of lines,
        // ending with a certain rhyme class for ALL rhyme classes available
        // sum of powers of rhyme group ways for each rhyme group.
        // class A: (end with 3)^2 + (end with 2)^2
        // class B: (end with 3)^1 + (end with 1)^1
        // Then, we multiply these together. Keep a running answer that is product
        // of everything so far
        long ans = 1;
        for (String group : poemSizes.keySet()) {
            int groupSize = poemSizes.get(group);
            long groupWays = 0;
            for (int rhymeGroup : lineWays.keySet()) {
                long ways = lineWays.get(rhymeGroup);
                groupWays += iterativeExpo(ways, groupSize);
                groupWays %= mod;
            }
            ans *= groupWays;
            ans %= mod;
        }
        out.println(ans);

        out.close();
    }

    static int mod = (int) 1e9 + 7;

    static long iterativeExpo(long x, int k) {
        long ans = 1;
        int j = 1;
        // Go through each digit in binary (using j as the pointer)
        while (j <= k) {
            if ((j & k) > 0) {
                ans *= x;
                ans %= mod;
            }
            x *= x;
            x %= mod;
            j <<= 1;
        }
        return ans;
    }

    static long fastExponentiate(long x, int k) {
        if (k == 0) {
            return 1;
        }
        else if (k == 1) {
            return x;
        }
        else {
            long root = fastExponentiate(x, k / 2);
            long ans = (root * root) % mod;
            if (k % 2 == 1) {
                ans *= x;
                ans %= mod;
            }
            return ans;
        }
    }

    static class FastScanner {
        public int BS = 1<<16;
        public char NC = (char)0;
        byte[] buf = new byte[BS];
        int bId = 0, size = 0;
        char c = NC;
        double cnt = 1;
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

        private char getChar(){
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
            cnt=1;
            boolean neg = false;
            if(c==NC)c=getChar();
            for(;(c<'0' || c>'9'); c = getChar()) {
                if(c=='-')neg=true;
            }
            long res = 0;
            for(; c>='0' && c <='9'; c=getChar()) {
                res = (res<<3)+(res<<1)+c-'0';
                cnt*=10;
            }
            return neg?-res:res;
        }

        public double nextDouble() {
            double cur = nextLong();
            return c!='.' ? cur:cur+nextLong()/cnt;
        }

        public String next() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=getChar();
            while(c>32) {
                res.append(c);
                c=getChar();
            }
            return res.toString();
        }

        public String nextLine() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=getChar();
            while(c!='\n') {
                res.append(c);
                c=getChar();
            }
            return res.toString();
        }

        public boolean hasNext() {
            if(c>32)return true;
            while(true) {
                c=getChar();
                if(c==NC)return false;
                else if(c>32)return true;
            }
        }
    }
}